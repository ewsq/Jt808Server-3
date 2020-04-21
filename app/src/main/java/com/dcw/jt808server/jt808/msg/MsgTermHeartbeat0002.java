package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 11:41
 * @desc 终端心跳 【上行】
 */
public class MsgTermHeartbeat0002 extends DCWMessage {

    public static final int ID_TERM_HEARTBEAT = 0x0002;


    public MsgTermHeartbeat0002() {
    }

    public MsgTermHeartbeat0002(DCWPacket packet) {
        packet.msgId = ID_TERM_HEARTBEAT;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.len = 0;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgTermHeartbeat0002-终端心跳{" +
                "msgId=" + Parser.getHexMsgId(ID_TERM_HEARTBEAT)+
                "}";
    }
}
