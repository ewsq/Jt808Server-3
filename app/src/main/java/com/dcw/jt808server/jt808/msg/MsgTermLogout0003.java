package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 13:52
 * @desc 终端注销 【上行】
 */
public class MsgTermLogout0003 extends DCWMessage {

    public static final int ID_TERM_LOGOUT = 0x0003;


    public MsgTermLogout0003() {
    }

    public MsgTermLogout0003(DCWPacket packet) {
        packet.msgId = ID_TERM_LOGOUT;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_LOGOUT;
        packet.len = 0;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgTermLogout0003-终端注销{" +
                "msgId=" + Parser.getHexMsgId(ID_TERM_LOGOUT)+
                "}";
    }
}
