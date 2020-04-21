package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 15:01
 * @desc 临时位置跟踪控制 【下行】
 */
public class MsgTempLocationTrack8202 extends DCWMessage {

    public static final int ID_TEMP_LOCATION_TRACK = 0x8202;

    /**
     * 时间间隔
     * 单位为秒（s），0 则停止跟踪。停止跟踪无需带后继
     * 字段
     */
    public short inteval;

    /**
     * 位置跟踪有效期
     * 单位为秒（s），终端在接收到位置跟踪控制消息后，
     * 在有效期截止时间之前，依据消息中的时间间隔发
     * 送位置汇报
     */
    public int validTime;

    public MsgTempLocationTrack8202(DCWPacket packet) {
        packet.msgId = ID_TEMP_LOCATION_TRACK;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {

        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        inteval = (short) (payload.getShort() & 0xFFFF);
        validTime = payload.getInt();
    }

    @Override
    public String toString() {
        return "MsgTempLocationTrack8202 临时位置跟踪控制{" +
                "inteval=" + inteval +
                ", validTime=" + validTime +
                '}';
    }
}
