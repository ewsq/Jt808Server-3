package com.dcw.jt808server;


import com.dcw.jt808server.rtp.RtpPacket;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter{

    //接受client发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到数据 ");
    }

    //通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        ctx.flush();
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
        ctx.writeAndFlush("hello client");
        if (null != ctx.channel()) {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            log += socketAddress.toString();
        }
        System.out.println(log);

        sendData(ctx);
    }

    private void sendData(ChannelHandlerContext ctx) {
        String path = "D:\\temp\\audio\\2020-04-16-10_channel_18";
//        decodeFileToSend(path,ctx);
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                if (bis.available() < buffer.length) {
                    bis.close();
                    File file;
                    bis = new BufferedInputStream(new FileInputStream(path));
                }
                byte[] bytes = new byte[len];
                System.arraycopy(buffer, 0, bytes, 0, len);
                Thread.sleep(20L);
                ctx.writeAndFlush(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void decodeFileToSend(String filePath,ChannelHandlerContext ctx) {
        if (filePath == null || filePath.trim().length() == 0 || filePath.length() == 0) {
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
                byte[] buffer = new byte[2048];
                int len = 0;
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                while ((len=bis.read(buffer))!=-1){
                    if (byteBuffer.remaining() > len) {
                        byteBuffer.put(buffer, 0, len);
                    }else {
                        //扩容byteBuffer
                        ByteBuffer newBuffer = byteBuffer.allocate(byteBuffer.limit() + len);
                        byteBuffer.flip();
                        newBuffer.put(byteBuffer);
                        newBuffer.put(buffer, 0, len);
                        byteBuffer = newBuffer;
                    }

                    byteBuffer.flip();

                    byte tempByte = 0;
                    while (byteBuffer.hasRemaining()) {
                        tempByte = byteBuffer.get();
                        byteBuffer.mark();
                        if (tempByte==0x30) {
                            if (byteBuffer.get() == 0x31 && byteBuffer.get() == 0x63 && byteBuffer.get() == 0x64) {
                                byteBuffer.get();//固定81
                                //是rtp包
                                RtpPacket rtpPacket = new RtpPacket();
                                byte b5 = byteBuffer.get();
                                rtpPacket.isFrameEndFlag = ((b5 >> 7) & 0x1) == 1;
                                rtpPacket.payloadType = b5 & 0x7F;
                                rtpPacket.serialNumber = byteBuffer.getShort();
                                byte[] simBcd = new byte[6];
                                for (int i = 0; i < simBcd.length; i++) {
                                    simBcd[i] = byteBuffer.get();
                                }
                                rtpPacket.simNumber = Tools.bcd2Str(simBcd);
                                rtpPacket.channel = byteBuffer.get();
                                byte b15 = byteBuffer.get();
                                rtpPacket.dataType = (b15 >> 4) & 0xF;
                                rtpPacket.packetFlag = b15 & 0xF;
                                if (rtpPacket.dataType != 0x4) {
                                    rtpPacket.timestamp = byteBuffer.getLong();
                                }

                                if (rtpPacket.dataType <= 2) {
                                    //视频帧
                                    rtpPacket.intervalLastIFrame = byteBuffer.getShort();
                                    rtpPacket.intervalLastFrame = byteBuffer.getShort();
                                }

                                rtpPacket.payloadLength = byteBuffer.getShort();
                                byte[] body = new byte[rtpPacket.payloadLength];
                                byteBuffer.get(body);
                                rtpPacket.dataBody = body;
                                System.out.println("解析到rtpPacket:"+rtpPacket.toString());
                                ctx.writeAndFlush(body);
                                Thread.sleep(20L);
                                ByteBuffer slice = byteBuffer.slice();
                                if (slice.remaining() > 0) {
                                    byteBuffer.clear();
                                    byteBuffer.put(slice);
                                    break;
                                }
                            }else {
                                System.out.println("未找到rtp包头 reset buffer");
                                byteBuffer.reset();
                            }
                        }

                        if (byteBuffer.remaining() < 1024) {
                            break;
                        }
                    }
                }
                bis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        decodeFileToSend(filePath,ctx);
    }
}