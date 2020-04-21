package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 14:53
 * @desc 查询终端位置信息 【下行】
 */
public class MsgQueryLocation8201 extends DCWMessage {
    public static final int ID_QUERY_LOCATION = 0x8201;

    public MsgQueryLocation8201(DCWPacket packet) {
        packet.msgId = ID_QUERY_LOCATION;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_QUERY_LOCATION;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgQueryLocation8201 查询终端位置信息{}";
    }
}
