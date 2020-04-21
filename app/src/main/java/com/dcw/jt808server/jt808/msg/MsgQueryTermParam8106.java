package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.util.Arrays;

/**
 * @author lixiaobin
 * @date 2019-10-28 11:48
 * @desc 查询指定终端参数 【下行】
 */
public class MsgQueryTermParam8106 extends DCWMessage {

    public static final int ID_QUERY_TERM_PARM = 0x8106;

    /**
     * 参数总数
     */
    public int paramCount;

    /**
     * 参数id列表
     */
    public int[] paramIds;

    public MsgQueryTermParam8106(DCWPacket packet) {
        packet.msgId = ID_QUERY_TERM_PARM;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_QUERY_TERM_PARM;
        int len = 1;
        if (null != paramIds) {
            len = len + 4 * paramIds.length;
        }
        packet.len = len;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        paramCount = payload.getByte();
        if (paramCount > 0) {
            paramIds = new int[paramCount];
            for (int i = 0; i < paramCount; i++) {
                paramIds[i] = payload.getInt();
            }
        }
    }

    @Override
    public String toString() {
        return "MsgQueryTermParam8106 查询指定终端参数{" +
                "paramCount=" + paramCount +
                ", paramIds=" + Arrays.toString(paramIds) +
                '}';
    }
}
