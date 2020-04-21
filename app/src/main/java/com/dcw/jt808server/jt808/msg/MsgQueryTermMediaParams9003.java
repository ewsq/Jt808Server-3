package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2020-03-31 13:58:57
 * @desc 查询终端音视频属性，消息体为空
 */
public class MsgQueryTermMediaParams9003 extends DCWMessage {
    public static final int ID_QUERY_TERM_MEDIA_PARAMS = 0x9003;

    public MsgQueryTermMediaParams9003(DCWPacket packet) {
        packet.msgId = ID_QUERY_TERM_MEDIA_PARAMS;
        unpack(packet.payload);
    }
    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
    }

    @Override
    public String toString() {
        return "MsgQueryTermMediaParams9003 查询终端音视频属性{}";
    }
}
