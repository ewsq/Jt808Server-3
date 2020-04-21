package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 16:03
 * @desc 描述信息
 */
public class MsgGimbalZoom9306 extends DCWMessage {

    public static final int ID_GIMBAL_ZOOM = 0x9306;

    public int channel;

    public int flag;

    public MsgGimbalZoom9306(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_ZOOM;
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
        return "MsgGimbalZoom9306 云台变倍{" +
                "channel=" + channel +
                ", flag=" + flag +
                '}';
    }
}

