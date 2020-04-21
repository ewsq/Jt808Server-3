package com.dcw.jt808server.jt808.msg;

import android.text.TextUtils;


import com.dcw.jt808server.XmlWrapper;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author lixiaobin
 * @date 2019-11-05 09:17
 * @desc 透传消息 【上行】
 */
public class MsgF904 extends DCWMessage {
    public static final int ID_F904 = 0xF904;
    public static final int FLAG_START = 0x2;
    public static final int FLAG_END = 0x3;

    public String xmlMsg;
    private XmlWrapper mXmlWrapper;
    private DCWPacket mPacket;
    private int serialId;
    public boolean hasRootTag = true;

    public MsgF904(DCWPacket packet) {
        packet.msgId = ID_F904;
        unpack(packet.payload);
    }

    public MsgF904() {
        mXmlWrapper = XmlWrapper.createEmptyMsg();
    }

    @Override
    public DCWPacket pack() {
        mPacket = new DCWPacket();
        mPacket.msgId = ID_F904;
        mPacket.payload.putByte((byte) FLAG_START); //开始标记
        serialId = mPacket.seq;
        mXmlWrapper.setEncodeWithRootTag(hasRootTag);
        xmlMsg = mXmlWrapper.build();
        if (!TextUtils.isEmpty(xmlMsg)) {
            byte[] bytes = xmlMsg.getBytes(Charset.forName("UTF-8"));
            for (int i = 0; i < bytes.length; i++) {
                mPacket.payload.putByte(bytes[i]);
            }
        }
        mPacket.payload.putByte((byte) FLAG_END); //结束
        mPacket.len = mPacket.payload.size();
        return mPacket;
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

    public void addParam(String key, String value) {
        mXmlWrapper.addParam(key, value);
    }

    @Override
    public String toString() {
        return "MsgF904 透传消息【上行】{" +
                "serialId='" + Parser.getHexMsgId(serialId) + '\'' +
                "xmlMsg='" + xmlMsg + '\'' +
                '}';
    }
}
