package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-11-26 11:21
 * @desc 登录通知消息
 * 7E8109000310000000102837E0001B016E7E
 */
public class MsgNotice8109 extends DCWMessage {
    public static final int ID_NOTICE = 0x8109;

    public static final int STATE_LOGIN_OTHER = 1;
    public static final int STATE_SEVER_BREAK_DISCONNECT = 2;


    public int ackId;
    /**
     *  1被挤掉
     *  2服务器主动断掉
     */
    public int state;

    public MsgNotice8109(DCWPacket packet) {
        packet.msgId = ID_NOTICE;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        ackId = payload.getUnsignedShort();
        state = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgNotice8109 登录通知消息{" +
                "ackId=" + ackId +
                ", state=" + state +
                '}';
    }
}
