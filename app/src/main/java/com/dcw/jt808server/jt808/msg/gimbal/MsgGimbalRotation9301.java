package com.dcw.jt808server.jt808.msg.gimbal;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-12-18 15:29
 * @desc 云台旋转
 */
public class MsgGimbalRotation9301 extends DCWMessage {

    public static final int ID_GIMBAL_CONTROL = 0x9301;

    public static final int DIRECTION_STOP = 0;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    public static final int DIRECTION_RIGHT = 4;

    /**
     * 通道号
     */
    public int channel;

    /**&
     * 方向
     */
    public int direction;

    /**
     * 速度
     */
    public int speed;


    public MsgGimbalRotation9301(DCWPacket packet) {
        packet.msgId = ID_GIMBAL_CONTROL;
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
        direction = payload.getByte();
        speed = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgGimbalRotation9301 云台控制{" +
                "channel=" + channel +
                ", direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}
