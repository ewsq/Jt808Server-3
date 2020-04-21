package com.dcw.jt808server;

import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.Parser;
import com.dcw.jt808server.jt808.msg.MsgLogin0103;
import com.dcw.jt808server.rtp.RtpPacket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lixiaobin
 * @date 2020-04-21 17:28:04
 * @desc 描述信息
 */
@ChannelHandler.Sharable
public class BusHandler extends ChannelInboundHandlerAdapter {

    private NettyChannelMap mChannelMap = new NettyChannelMap();
    //接受client发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof ByteBuf) {
                ByteBuf bf = (ByteBuf) msg;
                if (bf.isReadable() && bf.readableBytes() > 0) {
                    byte[] frameData = new byte[bf.readableBytes()];
                    bf.readBytes(frameData);
                    frameData = Tools.yxReversal(frameData);//转义
                    if (null == frameData) {
                        return;
                    }

                    byte[] bytes = new byte[frameData.length+2]; //+2 加上标记
                    bytes[0] = 0x7E;
                    bytes[bytes.length - 1] = 0x7E;
                    bf.readBytes(bytes, 1, bf.readableBytes());
                    System.arraycopy(frameData,0,bytes,1,frameData.length);

                    //16进制
                    String bytesStr = Tools.parseByte2HexStr(bytes);
                    printlog("收到客户端消息:"+bytesStr);

                    processCompletedData((SocketChannel) ctx.channel(),bytes);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printlog(String s) {
        System.out.println(s);
    }


    //读操作时捕获到异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        System.out.println("exceptionCaught "+cause.getMessage());
    }

    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String log = "channelActive ";
        if (null != ctx.channel()) {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            log += socketAddress.toString();
        }
        printlog(log);
    }

    /**
     * 处理一帧完整的数据
     * @param bytes
     */
    private void processCompletedData(SocketChannel channel,byte[] bytes) {
        if (null != bytes && bytes.length>=15) { //最短的消息15个字节
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            if (buffer.get() == 0x7E) {
                try {
                    DCWPacket packet = new DCWPacket();
                    packet.msgId = buffer.getShort() & 0xFFFF;
                    //消息体属性
                    int bodyAttr = buffer.getShort() & 0xFFFF;
                    packet.isMultiPacket = (int) ((bodyAttr >> 13) & 0x1) == 1; //0010 0000 0000 0000
                    packet.encryptionType = (byte) ((bodyAttr >> 10) & 0x7);
                    packet.len = bodyAttr & 0x3FF; //0000 0011 1111 1111

                    //终端手机号
                    byte[] tempTermSn = new byte[6];
                    for (int i = 0; i < tempTermSn.length; i++) {
                        tempTermSn[i] = buffer.get();
                    }
                    packet.termSn = new String(Tools.str2Bcd(new String(tempTermSn)));

                    //消息流水号
                    packet.seq = buffer.getShort() & 0xFFFF;

                    //消息包封装项
                    if (packet.isMultiPacket) {
                        packet.nPackets = buffer.getShort() & 0xFFFF;
                        packet.seqPacket = buffer.getShort() & 0xFFFF;
                    }

                    for (int i = 0; i < packet.len; i++) {
                        packet.payload.putByte(buffer.get());
                    }


                    //计算校验码
                    byte checkCode = Parser.generateCheckCode(bytes, 1, buffer.position());
                    if (checkCode == buffer.get()) {
//                                DCWLog.e("校验码通过 msgId=" + Integer.toHexString(packet.msgId));
                    } else {
                        printlog("校验码不通过 msgId=" + Integer.toHexString(packet.msgId));
                    }

//                    byte endFlag = buffer.get();

                    DCWMessage msg = packet.unpack();
                    if (null != msg) {
                        msg.mSrcBytes = bytes;
                        msg.serialid = packet.seq;
                        handleMsg(channel, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    printlog("解析消息失败，"+e.getMessage());
                }

            }
        }
    }

    private void handleMsg(SocketChannel channel, DCWMessage msg) {
        if (msg instanceof MsgLogin0103) {

        }
    }

}
