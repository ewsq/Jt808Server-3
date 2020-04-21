package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 11:46
 * @desc 查询终端参数 【下行】
 */
public class MsgQueryTermParams8104 extends DCWMessage {

    public static final int ID_QUERY_TERM_PARAMS = 0x8104;

    public MsgQueryTermParams8104(DCWPacket packet) {
        packet.msgId = ID_QUERY_TERM_PARAMS;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_QUERY_TERM_PARAMS;
        packet.len = 0;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
    }

    @Override
    public String toString() {
        return "MsgQueryTermParams8104 查询终端参数{}";
    }
}
