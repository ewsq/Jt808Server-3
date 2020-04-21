package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 15:43
 * @desc 云台光圈 aperture
 */
public class MsgGimbalAperture9303 extends DCWMessage {

    public static final int ID_GIMBAL_APERTURE = 0x9303;

    public  int channel;

    public int flag;

    public MsgGimbalAperture9303(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_APERTURE;
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
        return "MsgGimbalAperture9303 云台光圈控制{" +
                "channel=" + channel +
                ", flag=" + flag +
                '}';
    }
}
