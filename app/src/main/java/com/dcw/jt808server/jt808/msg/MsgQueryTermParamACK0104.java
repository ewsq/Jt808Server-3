package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.entity.TermParam;

import java.util.List;

/**
 * @author lixiaobin
 * @date 2019-10-28 11:54
 * @desc 查询终端参数应答 【上行】
 */
public class MsgQueryTermParamACK0104 extends DCWMessage {
    public static final int ID_ACK_QUERY_TERM_PARAM = 0x0104;

    public long ackSeq;
    public int ackParamsCount;
    public List<TermParam> ackParamsList;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_QUERY_TERM_PARAM;
        packet.payload.putUnsignedShort((int) ackSeq & 0xFFFF);
        packet.payload.putByte((byte) ackParamsCount);
        if (ackParamsCount > 0 && null!=ackParamsList && ackParamsCount==ackParamsList.size()) {
            for (int i = 0; i < ackParamsCount; i++) {
                TermParam param = ackParamsList.get(i);
                packet.payload.putInt(param.paramId);
                packet.payload.putByte((byte) param.paramLen);
                if (param.paramLen > 0 && null!=param.paramValue && param.paramValue.length==param.paramLen) {
                    for (int j = 0; j < param.paramLen; j++) {
                        packet.payload.putByte(param.paramValue[j]);
                    }
                }
            }
        }
        packet.len = packet.payload.index;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
