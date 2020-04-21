package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 11:38
 * @desc 平台通用应答 【下行】
 */
public class MsgPlatACK8001 extends DCWMessage {
    public static final int ID_PLAT_ACK = 0x8001;

    public static final int RESULT_SUCCESS = 0;//成功/确认
    public static final int RESULT_FAILED = 1;//失败
    public static final int RESULT_MSG_ERROR = 2;//消息有误
    public static final int RESULT_UNSUPPORT = 3;//不支持
    public static final int RESULT_WARNNING_ENSURE = 4;//报警处理确认

    /**
     * 应答流水号
     */
    public int ackSeq;

    /**
     * 应答id
     */
    public int ackId;

    /**
     * 结果
     */
    public int result;

    public MsgPlatACK8001() {

    }

    public MsgPlatACK8001(DCWPacket packet) {
        packet.msgId = ID_PLAT_ACK;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_PLAT_ACK;
        packet.payload.putUnsignedShort(ackSeq);
        packet.payload.putUnsignedShort(ackId);
        packet.payload.putByte((byte) result);
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        ackSeq = payload.getUnsignedShort();
        ackId = payload.getUnsignedShort();
        result = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgPlatACK8001-平台通用应答{" +
                "msgId=" + Parser.getHexMsgId(ID_PLAT_ACK)+
                ", ackSeq=" + Parser.getHexMsgId(ackSeq) +
                ", ackId=" + Parser.getHexMsgId(ackId) +
                ", result=" + result +
                ", serialId=" + serialid +
                '}';
    }
}
