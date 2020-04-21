package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 13:55
 * @desc 终端鉴权【上行】
 */
public class MsgTermAuth0102 extends DCWMessage {

    public static final int ID_TERM_AUTH = 0x0102;

    /**
     * 鉴权码
     */
    public String authcode;

    public MsgTermAuth0102() {
    }

    public MsgTermAuth0102(DCWPacket packet) {
        packet.msgId = ID_TERM_AUTH;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_AUTH;
        if (null != authcode) {
            byte[] bytes = authcode.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                packet.payload.putByte(bytes[i]);
            }
        }
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        byte[] tempAuth = new byte[payload.size() - payload.index];
        for (int i = 0; i < tempAuth.length; i++) {
            tempAuth[i] = payload.getByte();
        }
        authcode = new String(tempAuth);
    }

    @Override
    public String toString() {
        return "MsgTermAuth0102-终端鉴权{" +
                "msgId="+ Parser.getHexMsgId(ID_TERM_AUTH)+
                ", authcode='" + authcode + '\'' +
                '}';
    }
}
