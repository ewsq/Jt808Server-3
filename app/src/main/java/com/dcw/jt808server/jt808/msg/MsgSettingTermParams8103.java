package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.entity.TermParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaobin
 * @date 2019-10-28 11:31
 * @desc 设置终端参数 【下行】
 */
public class MsgSettingTermParams8103 extends DCWMessage {

    public static final int ID_SETTING_TERM_PARAMS = 0x8103;

    /**
     * 参数总数
     */
    public int paramCount;

    public List<TermParam> params;

    public MsgSettingTermParams8103(DCWPacket packet) {
        packet.msgId = ID_SETTING_TERM_PARAMS;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_SETTING_TERM_PARAMS;

        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        paramCount = payload.getByte();
        if (paramCount > 0) {
            params = new ArrayList<>();
            for (int i = 0; i < paramCount; i++) {
                TermParam param = new TermParam();
                param.paramId = payload.getInt() & 0xFFFFFFFF;
                param.paramLen = payload.getByte();
                if (param.paramLen > 0) {
                    param.paramValue = new byte[param.paramLen];
                    for (int j = 0; j < param.paramLen; j++) {
                        param.paramValue[j] = payload.getByte();
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "MsgSettingTermParams8103 设置终端参数{" +
                "paramCount=" + paramCount +
                ", params=" + params +
                '}';
    }
}
