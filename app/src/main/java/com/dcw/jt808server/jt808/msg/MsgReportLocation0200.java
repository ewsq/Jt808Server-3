package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 14:40
 * @desc 位置信息汇报 【上行】
 */
public class MsgReportLocation0200 extends DCWMessage {

    public static final int ID_REPORT_LOCATION = 0x0200;

    /**
     * 报警标志
     * @see com.dcw.dcwnetlib.jt808.enums.AlarmSign
     */
    public int alarmSign;

    /**
     * 状态
     */
    public int state;

    /**
     * 纬度 以度为单位的纬度值乘以 10 的 6 次方，精确到百万
     * 分之一度
     */
    public int latitude;

    /**
     * 经度 以度为单位的经度值乘以 10 的 6 次方，精确到百万
     * 分之一度
     */
    public int longitude;

    /**
     * 高度 海拔高度，单位为米（m）
     */
    public short altitude;

    /**
     * 速度 1/10km/h
     */
    public short speed;

    /**
     * 方向 0-359，正北为 0，顺时针
     */
    public short direction;

    /**
     * 时间  BCD[6] YY-MM-DD-hh-mm-ss（GMT+8 时间，本标准中之后涉
     * 及的时间均采用此时区）
     */
    public byte[] datetime;


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_REPORT_LOCATION;
        packet.payload.putInt(alarmSign);
        packet.payload.putInt(state);
        packet.payload.putInt(latitude);
        packet.payload.putInt(longitude);
        packet.payload.putShort(altitude);
        packet.payload.putShort(speed);
        packet.payload.putShort(direction);
        if (null != datetime) {
            for (int i = 0; i < datetime.length; i++) {
                packet.payload.putByte(datetime[i]);
            }
        }
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
