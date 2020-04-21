package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2019-10-21 11:27
 * @desc 登录回复消息 【下行】
 */
public class MsgLoginACK8101 extends DCWMessage {

    public static final int ID_LOGIN_ACK = 0x8101;

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_NAME_NOT_EXIST = 1;
    public static final int STATUS_PWD_ERROR = 2;
    public static final int STATUS_TERMSN_NOT_EXIST = 3;


    /**
     * 回复的id
     */
    public int ackId;

    /**
     * 状态
     */
    public int status;

    /**
     *
     */
    public String termSn;


    public MsgLoginACK8101() {
    }

    public MsgLoginACK8101(DCWPacket packet) {
        packet.msgId = ID_LOGIN_ACK;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        ackId = payload.getUnsignedShort();
        status = payload.getByte();

        if (status==STATUS_SUCCESS) { //说明登录获取TermSn成功
            byte[] tempTermSnAsciis = new byte[12];
            for (int i = 0; i < tempTermSnAsciis.length; i++) {
                tempTermSnAsciis[i] = payload.getByte();
            }
            termSn = getAscillStr(tempTermSnAsciis);
        }


    }

    private String getAscillStr(byte[] tempTermSnAsciis) {
        String ret = "";
        if (null != tempTermSnAsciis) {
            for (int i = 0; i < tempTermSnAsciis.length; i++) {
                ret += Character.toString((char)tempTermSnAsciis[i]);
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "MsgLoginACK8101 登录回复消息{" +
                "msgId="+ Parser.getHexMsgId(ID_LOGIN_ACK)+
                ", ackId=" + Parser.getHexMsgId(ackId) +
                ", status=" + status +
                ", termSn='" + termSn +
                '}';
    }
}
