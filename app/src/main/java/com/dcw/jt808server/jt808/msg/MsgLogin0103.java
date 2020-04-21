package com.dcw.jt808server.jt808.msg;

import android.text.TextUtils;

import com.dcw.jt808server.Tools;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;


/**
 * @author lixiaobin
 * @date 2019-10-21 10:57
 * @desc DCW登录消息 【上行】
 */
public class MsgLogin0103 extends DCWMessage {
    public static final int ID_LOGIN = 0x0103;

    /**
     * 登录模式 0x80
     */
    public int loginMode = 0x80;

    /**
     * 用户名
     */
    public String name;

    /**
     * 密码
     */
    public String pwd;

    /**
     * 设备ID
     */
    public String deviceId;

    /**
     *
     * @param packet
     */
    public MsgLogin0103(DCWPacket packet) {
        packet.msgId = ID_LOGIN;
        unpack(packet.payload);
    }

    public MsgLogin0103() {

    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_LOGIN;

        //随机生成的
        packet.termSn = generateTempTermSn();


        if (loginMode==0x80) {
            //登录模式
            packet.payload.putByte((byte) loginMode);
            //用户名长度
            packet.payload.putByte((byte) getNameBytes().length);

            byte[] nameBytes = getNameBytes();
            for (int i = 0; i < nameBytes.length; i++) {
                packet.payload.putByte(nameBytes[i]);
            }

            packet.payload.putByte((byte) getPwdBytes().length);

            byte[] pwdBytes = getPwdBytes();
            for (int i = 0; i < pwdBytes.length; i++) {
                packet.payload.putByte(pwdBytes[i]);
            }
        }else {
            if (!TextUtils.isEmpty(deviceId)) {
                String hexStr = Tools.str2HexStr(deviceId);
                byte[] bytes = Tools.parseHexStr2Byte(hexStr);
                if (null != bytes && bytes.length > 0) {
                    for (int i = 0; i < bytes.length; i++) {
                        packet.payload.putByte(bytes[i]);
                    }
                }
            }
        }


        packet.len = packet.payload.size();
        return packet;
    }

    private String generateTempTermSn() {
        return "00"+(int)((Math.random()*9+1)*1000000000);
    }

    private byte[] getPwdBytes() {
        if (!TextUtils.isEmpty(pwd)) {
            return pwd.getBytes();
        }
        return new byte[0];
    }

    private byte[] getNameBytes() {
        if (!TextUtils.isEmpty(name)) {
            return name.getBytes();
        }
        return new byte[0];
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        loginMode = payload.getByte();

    }

    @Override
    public String toString() {
        return "MsgLogin0103-登录消息{" +
                "msgId="+ Parser.getHexMsgId(ID_LOGIN)+
                ", loginMode=" + loginMode +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
