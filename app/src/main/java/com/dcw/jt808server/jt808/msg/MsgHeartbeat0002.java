package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-21 18:26
 * @desc 终端心跳 【上行】
 */
public class MsgHeartbeat0002 extends DCWMessage {
    public static final int ID_HEARTBEAT = 0x0002;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_HEARTBEAT;
        packet.len = 0;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgHeartbeat0002-终端心跳{" +
                "msgId=" + Parser.getHexMsgId(ID_HEARTBEAT)+
                "}";
    }
}
