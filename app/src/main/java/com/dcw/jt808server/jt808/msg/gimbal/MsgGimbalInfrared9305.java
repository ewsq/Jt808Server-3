package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 16:00
 * @desc 云台红外
 */
public class MsgGimbalInfrared9305 extends DCWMessage {

    public static final int ID_GIMBAL_INFRARED = 0x9305;

    public int channel;

    public int flag;

    public MsgGimbalInfrared9305(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_INFRARED;
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
        return "MsgGimbalInfrared9305 云台红外控制{" +
                "channel=" + channel +
                ", flag=" + flag +
                '}';
    }
}
