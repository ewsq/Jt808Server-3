package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 14:36
 * @desc 终端升级结果通知
 * 终端在升级完成并重新连接后使用该命令通知监控中心 【上行】
 */
public class MsgACKTermUpgradeResult0108 extends DCWMessage {
    public static final int ID_ACK_TERM_UPGRADE_RESULT = 0x0108;

    /**
     * 升级类型
     * 0：终端，12：道路运输证 IC 卡读卡器，52：北斗
     * 卫星定位模块
     */
    public int upgradeType;

    /**
     * 升级结果 0：成功，1：失败，2：取消
     */
    public int upgradeResult;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_TERM_UPGRADE_RESULT;
        packet.payload.putByte((byte) upgradeType);
        packet.payload.putByte((byte) upgradeResult);
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
