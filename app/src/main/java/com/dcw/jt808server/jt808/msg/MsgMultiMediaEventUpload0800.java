package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;
import com.dcw.jt808server.jt808.enums.EventCode;
import com.dcw.jt808server.jt808.enums.MultiMediaFormat;
import com.dcw.jt808server.jt808.enums.MultiMediaType;

/**
 * @author lixiaobin
 * @date 2020-03-19 10:16:01
 * @desc 描述信息
 */
public class MsgMultiMediaEventUpload0800 extends DCWMessage {
    public static final int ID_MULTIMEDIA_EVENT_UPLOAD = 0x0800;

    /**
     * 多媒体数据ID
     */
    public int id;

    public @MultiMediaType
    int mediaType;

    public @MultiMediaFormat
    int mediaFormat;

    public @EventCode
    int event;

    public int channel;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_MULTIMEDIA_EVENT_UPLOAD;
        packet.payload.putInt(id);
        packet.payload.putByte((byte) mediaType);
        packet.payload.putByte((byte) mediaFormat);
        packet.payload.putByte((byte) event);
        packet.payload.putByte((byte) channel);

        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgMultiMediaEventUpload0800 多媒体事件消息上传数据{" +
                "msgID="+ Parser.getHexMsgId(ID_MULTIMEDIA_EVENT_UPLOAD)+
                "id=" + id +
                ", mediaType=" + mediaType +
                ", mediaFormat=" + mediaFormat +
                ", event=" + event +
                ", channel=" + channel +
                '}';
    }
}
