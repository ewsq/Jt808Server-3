package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.nio.charset.Charset;

/**
 * @author lixiaobin
 * @date 2019-10-28 15:09
 * @desc 文本信息下发 【下行】
 */
public class MsgIssueTxt8300 extends DCWMessage {
    public static final int ID_ISSUE_TXT = 0x8300;

    /**
     * 标志
     */
    public int flag;

    /**
     * 文本内容
     */
    public String content;

    public MsgIssueTxt8300(DCWPacket packet) {
        packet.msgId = ID_ISSUE_TXT;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        flag = payload.getByte();
        byte[] bytes = new byte[payload.size() - payload.index];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = payload.getByte();
        }
        content = new String(bytes, Charset.forName("GBK"));
    }

    @Override
    public String toString() {
        return "MsgIssueTxt8300 文本信息下发{" +
                "flag=" + flag +
                ", content='" + content + '\'' +
                '}';
    }
}
