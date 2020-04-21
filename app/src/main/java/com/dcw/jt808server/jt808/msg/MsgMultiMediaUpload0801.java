package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;
import com.dcw.jt808server.jt808.enums.EventCode;
import com.dcw.jt808server.jt808.enums.MultiMediaFormat;
import com.dcw.jt808server.jt808.enums.MultiMediaType;

import java.util.Arrays;

/**
 * @author lixiaobin
 * @date 2020-03-19 09:36:08
 * @desc 描述信息
 */
public class MsgMultiMediaUpload0801 extends DCWMessage {

    public static final int ID_MULTIMEDIA_UPLOAD = 0x0801;

    /**
     * 4 bytes
     * 多媒体id 大于0
     */
    public int id;

    /**
     * 1 byte
     * 多媒体类型
     */
    public @MultiMediaType
    int mediaType;

    public boolean isFirstPacket;


    @Override
    public String toString() {
        return "MsgMultiMediaUpload0801多媒体上传{" +
                "msgId="+ Parser.getHexMsgId(ID_MULTIMEDIA_UPLOAD)+
                "id=" + id +
                ", mediaType=" + mediaType +
                ", isFirstPacket=" + isFirstPacket +
                ", mediaFormat=" + mediaFormat +
                ", event=" + event +
                ", channel=" + channel +
                ", location=" + location +
                ", data=" + Arrays.toString(data) +
                ", isMulitPackt=" + isMulitPackt +
                ", nPackets=" + nPackets +
                ", seqPackts=" + seqPackts +
                '}';
    }

    /**
     * 1byte
     * 多媒体格式编码
     */
    public @MultiMediaFormat
    int mediaFormat;

    /**
     * 1byte
     * 事件项编码
     */
    public @EventCode
    int event;

    /**
     * 1byte
     * 通道ID
     */
    public int channel;

    public MsgReportLocation0200 location;

    public byte[] data;

    public boolean isMulitPackt;

    public int nPackets;

    public int seqPackts;


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_MULTIMEDIA_UPLOAD;
        packet.isMultiPacket = isMulitPackt;
        packet.nPackets = nPackets;
        packet.seqPacket = seqPackts;
        if (isFirstPacket) {
            packet.payload.putInt(id);
            packet.payload.putByte((byte) mediaType);
            packet.payload.putByte((byte) mediaFormat);
            packet.payload.putByte((byte) event);
            packet.payload.putByte((byte) channel);
            for (int i = 0; i < 28; i++) {
                if (null != location) {
                    packet.payload.putByte(location.pack().payload.payload.get(i));
                }else {
                    packet.payload.putByte((byte) 0);
                }
            }
        }

        if (null != data) {
            packet.payload.putBytes(data);
        }

        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
