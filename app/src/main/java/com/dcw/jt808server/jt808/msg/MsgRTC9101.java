package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.nio.charset.Charset;

/**
 * @author lixiaobin
 * @date 2019-11-22 13:57
 * @desc 音视频传输控制请求
 */
public class MsgRTC9101 extends DCWMessage {

    public static final int ID_RTC_TRANSFER = 0x9101;

    /**
     * 服务器IP地址长度
     */
    public int lenServerIp;

    /**
     * 实时视频服务器IP
     */
    public String serverIp;

    /**
     * 实时视频服务器TCP端口号
     */
    public int serverTCPPort;

    /**
     * 实时视频服务器UDP端口号
     */
    public int serverUDPPort;

    /**
     * 逻辑通道号
     */
    public int channel;

    /**
     * 数据类型 0：音视频 1：视频 2:双向对讲 3：监听 4:中心广播 5：透传
     */
    public int dataType;

    /**
     * 码流类型 0：主码流 1：子码流
     */
    public int streamType;

    public int serialid;
    @Override
    public DCWPacket pack() {
        return null;
    }

    public MsgRTC9101(DCWPacket packet) {
        packet.msgId = ID_RTC_TRANSFER;
        serialid = packet.seq;
        unpack(packet.payload);
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        lenServerIp = payload.getByte();
        byte[] ipBytes = new byte[lenServerIp];
        for (int i = 0; i < ipBytes.length; i++) {
            ipBytes[i] = payload.getByte();
        }
        serverIp = new String(ipBytes, Charset.forName("utf-8"));
        serverTCPPort = payload.getUnsignedShort();
        serverUDPPort = payload.getUnsignedShort();
        channel = payload.getByte();
        dataType = payload.getByte();
        streamType = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgRTC9101 实时音视频传输请求{" +
                "lenServerIp=" + lenServerIp +
                ", serverIp='" + serverIp + '\'' +
                ", serverTCPPort=" + serverTCPPort +
                ", serverUDPPort=" + serverUDPPort +
                ", channel=" + channel +
                ", dataType=" + dataType +
                ", streamType=" + streamType +
                ", serialid=" + serialid +
                '}';
    }
}
