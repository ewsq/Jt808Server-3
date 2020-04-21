package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 15:55
 * @desc 云台雨刷
 */
public class MsgGimbalWiper9304 extends DCWMessage {

    public static final int ID_GIMBAL_WIPER = 0x9304;

    public int channel;

    public int flag;

    public MsgGimbalWiper9304(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_WIPER;
        serialid = packet.seq;
        unpack(packet.payload);
    }
    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        channel = payload.getByte();
        flag = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgGimbalWiper9304 云台雨刷控制{" +
                "channel=" + channel +
                ", flag=" + flag +
                '}';
    }
}
