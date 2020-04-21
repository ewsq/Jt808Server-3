package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.nio.charset.Charset;

/**
 * @author lixiaobin
 * @date 2019-10-28 13:46
 * @desc 终端控制 【下行】
 */
public class MsgTermControl8105 extends DCWMessage {

    public static final int ID_TERM_CONTROL = 0x8105;

    public int cmdCode;
    public String cmdParam;

    public MsgTermControl8105(DCWPacket packet) {
        packet.msgId = ID_TERM_CONTROL;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_CONTROL;
        packet.payload.putByte((byte) cmdCode);
        packet.payload.putString(cmdParam,"gbk");
        packet.len = packet.payload.index;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        cmdCode = payload.getByte();
        byte[] bytes = new byte[payload.size() - payload.index];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = payload.getByte();
        }
        cmdParam = new String(bytes, Charset.forName("gbk"));
    }

    @Override
    public String toString() {
        return "MsgTermControl8105 终端控制{" +
                "cmdCode=" + cmdCode +
                ", cmdParam='" + cmdParam + '\'' +
                '}';
    }
}
