package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2020-03-19 14:18:04
 * @desc 摄像头立即拍摄命令应答
 */
public class MsgACKCapture0805 extends DCWMessage {
    public static final int ID_ACK_CAPTURE = 0x0805;

    /**
     * 应答流水号
     */
    public int ackSrialId;

    /**
     * 0 ：成功 1:失败 2:通道不支持
     * 结果
     */
    public int result;

    public int successCount;

    public int[] ids;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_CAPTURE;
        packet.payload.putInt(ackSrialId);
        packet.payload.putByte((byte) result);
        if (result==0) {
            packet.payload.putInt(successCount);
            if (null!=ids) {
                for (int i = 0; i < ids.length; i++) {
                    packet.payload.putInt(ids[i]);
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
