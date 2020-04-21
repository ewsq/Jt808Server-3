package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.XmlWrapper;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

import java.io.UnsupportedEncodingException;

/**
 * @author lixiaobin
 * @date 2019-10-21 16:39
 * @desc 透传消息 【下行】
 */
public class MsgF903 extends DCWMessage {
    public static final int ID_F903 = 0xF903;

    public String getXmlMsg() {
        return xmlMsg;
    }

    public String xmlMsg;

    public MsgF903(DCWPacket packet) {
        packet.msgId = ID_F903;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        byte[] xmlBytes = new byte[payload.size()-payload.index];
        for (int i = 0; i < xmlBytes.length; i++) {
            xmlBytes[i] = payload.getByte();
        }
        try {
            xmlMsg = new String(xmlBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MsgF903-透传消息【下行】{" +
                "msgId="+ Parser.getHexMsgId(ID_F903)+
                ",xmlMsg='" + xmlMsg + '\'' +
                '}';
    }

    public XmlWrapper convertXmlMessage() {
        return XmlWrapper.createMsg(xmlMsg);
    }


}
