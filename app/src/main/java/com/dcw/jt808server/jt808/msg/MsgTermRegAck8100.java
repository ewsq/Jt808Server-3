package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-18 13:43
 * @desc 终端注册应答 【下行】
 */
public class MsgTermRegAck8100 extends DCWMessage {
    public static final int ID_TERM_REG = 0x8100;
    public static final int RESULT_REGIST_SUCCESS = 0; //注册成功
    public static final int RESULT_CAR_ALREADY_REGISTED = 1; //车辆已经注册
    public static final int RESULT_CAR_NOTIN_DB = 2; //数据库中无该车辆
    public static final int RESULT_TERM_ALREADY_REGISTED = 3; //终端已被注册
    public static final int RESULT_TERM_NOTIN_DB = 4; //数据库中无该终端

    public int ackSeq;

    //0：成功；1：车辆已被注册；2：数据库中无该车辆；
    //3：终端已被注册；4：数据库中无该终端
    public int result;

    //成功返回鉴权码
    public String authcode;

    public MsgTermRegAck8100() {
    }

    public MsgTermRegAck8100(DCWPacket packet) {
        packet.msgId = ID_TERM_REG;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_REG;
        packet.payload.putUnsignedShort(ackSeq);
        packet.payload.putByte((byte) result);
        if (null != authcode) {
            byte[] bytes = authcode.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                packet.payload.putByte(bytes[i]);
            }
        }
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        ackSeq = payload.getUnsignedShort();
        result = payload.getByte();
        byte[] tempAuthcode = new byte[payload.size() - payload.index];
        for (int i = 0; i < tempAuthcode.length; i++) {
            tempAuthcode[i] = payload.getByte();
        }
        authcode = new String(tempAuthcode);
    }


    @Override
    public String toString() {
        return "MsgTermRegAck8100 终端注册应答{" +
                "msgId="+ Parser.getHexMsgId(ID_TERM_REG)+
                ", ackSeq=" + ackSeq +
                ", result=" + result +
                ", authcode='" + authcode + '\'' +
                '}';
    }
}
