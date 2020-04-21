package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 15:39
 * @desc 云台焦距
 */
public class MsgGimbalFocal9302 extends DCWMessage {
    public static final int ID_GIMBAL_FOCAL = 0x9302;

    public int channel;

    public int flag;


    public MsgGimbalFocal9302(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_FOCAL;
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
        return "MsgGimbalFocal9302 云台焦距调整{" +
                "channel=" + channel +
                ", flag=" + flag +
                '}';
    }
}
