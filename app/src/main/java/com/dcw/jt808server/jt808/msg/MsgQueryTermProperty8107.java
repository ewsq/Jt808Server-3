package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 13:55
 * @desc 查询终端属性 【下行】
 */
public class MsgQueryTermProperty8107 extends DCWMessage {

    public static final int ID_QUERY_TERM_PROPERTY = 0x8107;

    public MsgQueryTermProperty8107(DCWPacket packet) {
        packet.msgId = ID_QUERY_TERM_PROPERTY;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        
    }

    @Override
    public String toString() {
        return "MsgQueryTermProperty8107 查询终端属性{}";
    }
}
