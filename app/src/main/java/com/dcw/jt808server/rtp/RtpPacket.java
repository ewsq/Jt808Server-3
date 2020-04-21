package com.dcw.jt808server.rtp;

import android.support.annotation.IntDef;
import android.text.TextUtils;


import com.dcw.jt808server.Tools;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.rtp.enums.PayloadType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lixiaobin
 * @date 2020-01-16 16:22
 * @desc 描述信息
 */
public class RtpPacket extends DataPacket {

    private static final String TAG = "RtpPacket";

    /**
     * 数据类型
     * 视频i帧
     * 视频p帧
     * 视频b帧
     * 音频数据
     * 透传数据
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DataType.VIDEO_I_FRAME, DataType.VIDEO_P_FRAME, DataType.VIDEO_B_FRAME, DataType.AUDIO_FRAME, DataType.NORMAL_DATA})
    public @interface DataType {
        int VIDEO_I_FRAME = 0;
        int VIDEO_P_FRAME = 1;
        int VIDEO_B_FRAME = 2;
        int AUDIO_FRAME = 3;
        int NORMAL_DATA = 4;
    }

    /**
     * 分包标记
     * 原子包
     * 第一包
     * 最后一包
     * 中间包
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PacketFlag.PACKET_ATOM, PacketFlag.PACKET_FIRST, PacketFlag.PACKET_LAST, PacketFlag.PACKET_MIDDLE})
    public @interface PacketFlag {
        int PACKET_ATOM = 0;
        int PACKET_FIRST = 1;
        int PACKET_LAST = 2;
        int PACKET_MIDDLE = 3;
    }

    /**
     * 4个字节
     */
    public byte[] headFlag = {0x30, 0x31, 0x63, 0x64};

    /**
     * 2bits
     * 固定为2
     */
    public int V = 2;

    /**
     * 1bit
     * 固定为0
     */
    public int P = 0;

    /**
     * 1bit
     * RTP头是否需要扩展位，固定为0
     */
    public int X = 0;

    /**
     * 4bits，固定为1
     */
    public int CC = 1;

    /**
     * 1bit,是否完整数据帧的边界
     */
    public boolean isFrameEndFlag;

    /**
     * 7bits
     * 负载类型
     */
    public @PayloadType
    int payloadType;

    /**
     * 包序号
     * 2bytes
     */
    public int serialNumber;

    /**
     * BCD[6]
     * SIM卡号
     */
    public String simNumber;

    /**
     * 1 byte
     * 逻辑通道号
     */
    public int channel;


    /**
     * 4bits
     * 数据类型
     */
    public @DataType
    int dataType;

    /**
     * 4bits
     * 分包处理标记
     */
    public @PacketFlag
    int packetFlag;

    /**
     * 8 bytes
     * 时间戳(ms)
     * 非视频不含此字段
     */
    public long timestamp;

    /**
     * 2 bytes
     * 与上一关键帧的时间间隔(ms)
     * 非视频不含此字段
     */
    public int intervalLastIFrame;

    /**
     * 2bytes
     * 与上一帧的时间间隔(ms)
     * 非视频不含此字段
     */
    public int intervalLastFrame;

    /**
     * 数据体长度
     */
    public int payloadLength;

    /**
     * 数据体
     */
    public byte[] dataBody;


    public static final int SIZE_PAYLOAD = 900;



    /**
     * 根据每一帧数据，封装打包
     * @param src
     * @param timestamp
     * @param iInterval
     * @param frameInterval
     * @param payloadType
     * @param frameDataType
     * @return
     */
    public static RtpPacket[] generatePackets(byte[] src,
                                              long timestamp,
                                              int iInterval,
                                              int frameInterval,
                                              @PayloadType int payloadType,
                                              @DataType int frameDataType) {
        if (null != src) {
            int count = src.length / SIZE_PAYLOAD;
            if (src.length % SIZE_PAYLOAD > 0) {
                count += 1;//不能被整除数量加一
            }
            RtpPacket[] packets = new RtpPacket[count];
            if (count == 1) {
                //只有一包
                RtpPacket rtpPacket = new RtpPacket();
                rtpPacket.dataType = frameDataType;
                rtpPacket.isFrameEndFlag = true;
                rtpPacket.dataBody = src;
                rtpPacket.payloadLength = src.length;
                rtpPacket.packetFlag = PacketFlag.PACKET_ATOM;
                rtpPacket.timestamp =  timestamp;
                rtpPacket.intervalLastIFrame = iInterval;
                rtpPacket.intervalLastFrame = frameInterval;
                packets[0] = rtpPacket;
            } else {
                //需要分包
                for (int i = 0; i < count; i++) {
                    RtpPacket packet = new RtpPacket();
                    int bodyStart = i * SIZE_PAYLOAD;
                    int bodyEnd = bodyStart + SIZE_PAYLOAD <= src.length ? bodyStart + SIZE_PAYLOAD : src.length;
                    byte[] body = new byte[bodyEnd - bodyStart];
                    System.arraycopy(src, bodyStart, body, 0, body.length);
                    packet.dataBody = body;
                    packet.payloadType = payloadType;
                    packet.timestamp =  timestamp;
                    packet.intervalLastFrame = frameInterval;
                    packet.intervalLastIFrame = iInterval;
                    packet.dataType = frameDataType;
                    if (i == 0) {
                        //第一包
                        packet.isFrameEndFlag = false;
                        packet.packetFlag = PacketFlag.PACKET_FIRST;
                    } else if (i == count - 1) {
                        packet.isFrameEndFlag = true;
                        packet.packetFlag = PacketFlag.PACKET_LAST;
                    } else {
                        packet.isFrameEndFlag = false;
                        packet.packetFlag = PacketFlag.PACKET_MIDDLE;
                    }
                    packets[i] = packet;
                }
            }

            return packets;
        }
        return null;
    }

    public static RtpPacket[] generateAudioPackets(byte[] src,long timestamp, @PayloadType int payloadType) {
        return generatePackets(src, timestamp, 0, 0, payloadType, DataType.AUDIO_FRAME);
    }

    public static RtpPacket[] generateAudioAACPackets(byte[] src,long timestamp) {
        return generateAudioPackets(src, timestamp, PayloadType.AUDIO_AAC);
    }


    public static RtpPacket[] generateVideoH264Packets(byte[] src, long timestamp, int iInterval, int frameInterval, @DataType int frameDataType) {
        return generatePackets(src, timestamp, iInterval, frameInterval, PayloadType.VIDEO_H264, frameDataType);
    }

    public static RtpPacket[] generateVideoH265Packets(byte[] src,long timestamp,int iInterval,int frameInterval,@DataType int frameDataType) {
        return generatePackets(src, timestamp, iInterval, frameInterval, PayloadType.VIDEO_H265, frameDataType);
    }


    public byte[] pack() {
        putBytes(headFlag);
        putByte((byte) ((V << 6) | (P << 5) | (X << 4) | CC));
        putByte((byte) (((isFrameEndFlag ? 1 : 0) << 7) | payloadType));
        putShort((short) serialNumber);
        putBytes(getSimCardBCD());
        putByte((byte) channel);
        putByte((byte) ((dataType << 4) | (packetFlag)));
        if (dataType != DataType.NORMAL_DATA) {
            //透传数据没有时间戳
            putLong(timestamp);
        }

        if (dataType != DataType.NORMAL_DATA && dataType != DataType.AUDIO_FRAME) {
            //非视频帧没有这两项
            putShort((short) intervalLastIFrame);
            putShort((short) intervalLastFrame);
        }

        putShort((short) (dataBody != null ? dataBody.length : 0));
        putBytes(dataBody);
        byte[] bytes = new byte[size()];
        payload.flip();
        payload.get(bytes);
        return bytes;
    }

    private byte[] getSimCardBCD() {
        byte[] bytes = new byte[6];
        if (!TextUtils.isEmpty(simNumber)) {
            return Tools.str2Bcd(simNumber);
        }
        return bytes;
    }

    @Override
    public String toString() {
        return "RtpPacket{" +
                "isFrameEndFlag=" + isFrameEndFlag +
                ", payloadType=" + payloadType +
                ", simNumber='" + simNumber + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", channel=" + channel +
                ", dataType=" + dataType +
                ", packetFlag=" + packetFlag +
                ", timestamp=" + timestamp +
                ", intervalLastIFrame=" + intervalLastIFrame +
                ", intervalLastFrame=" + intervalLastFrame +
                ", payloadLength=" + (dataBody!=null?dataBody.length:0) +
                '}';
    }
}
