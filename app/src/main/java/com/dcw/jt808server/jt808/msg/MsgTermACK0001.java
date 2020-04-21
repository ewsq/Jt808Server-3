package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 11:26
 * @desc 终端应答 【上行】
 */
public class MsgTermACK0001 extends DCWMessage {
    public static final int ID_TERM_ACK = 0x0001;

    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILED = 1;
    public static final int RESULT_MSG_ERROR = 2;
    public static final int RESULT_UNSUPPORT = 3;

    //对应的平台消息的流水号
    public int ackSeq;

    //对应的平台消息的 ID
    public int ackId;

    //0：成功/确认；1：失败；2：消息有误；3：不支持
    public int result;

    public MsgTermACK0001() {
    }

    public MsgTermACK0001(DCWPacket packet) {
        packet.msgId = ID_TERM_ACK;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_ACK;
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
        return "MsgTermACK0001-终端应答{" +
                "msgId="+ Parser.getHexMsgId(ID_TERM_ACK)+
                ", ackSeq=" + ackSeq +
                ", ackId=" + Parser.getHexMsgId(ackId) +
                ", result=" + result +
                '}';
    }
}
