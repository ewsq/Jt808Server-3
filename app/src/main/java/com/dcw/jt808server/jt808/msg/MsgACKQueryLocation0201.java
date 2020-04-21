package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 14:55
 * @desc 查询位置信息应答 【上行】
 */
public class MsgACKQueryLocation0201 extends DCWMessage {
    public static final int ID_ACK_QUERY_LOCATION = 0x0201;

    public short ackSeq;

    public MsgReportLocation0200 location;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_QUERY_LOCATION;
        packet.payload.putShort(ackSeq);
        if (null != location) {
            packet.payload.putInt(location.alarmSign);
            packet.payload.putInt(location.state);
            packet.payload.putInt(location.latitude);
            packet.payload.putInt(location.longitude);
            packet.payload.putShort(location.altitude);
            packet.payload.putShort(location.speed);
            packet.payload.putShort(location.direction);
            if (null != location.datetime) {
                for (int i = 0; i < location.datetime.length; i++) {
                    packet.payload.putByte(location.datetime[i]);
                }
            }
        }
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
