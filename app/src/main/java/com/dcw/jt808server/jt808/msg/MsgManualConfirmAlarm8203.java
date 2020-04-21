package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 15:06
 * @desc 人工确认报警消息 【上行】
 */
public class MsgManualConfirmAlarm8203 extends DCWMessage {
    public static final int ID_MANUALCONFIRM_ALARM = 0x8203;

    /**
     * 报警消息流水号
     * 需人工确认的报警消息流水号，0 表示该报警类型所有
     * 消息
     */
    public short seq;

    /**
     * 人工确认报警类型
     */
    public int alarmType;
    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_MANUALCONFIRM_ALARM;
        packet.payload.putShort(seq);
        packet.payload.putInt(alarmType);
        packet.len = packet.payload.index;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
