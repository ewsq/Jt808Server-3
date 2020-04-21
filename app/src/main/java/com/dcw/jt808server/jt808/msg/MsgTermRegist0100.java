package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

import java.util.Arrays;

/**
 * @author lixiaobin
 * @date 2019-10-18 11:44
 * @desc 终端注册 【上行】
 */
public class MsgTermRegist0100 extends DCWMessage {
    public static final int ID_TERM_REGIST = 0x0100;

    /**
     * 省域id
     */
    public int provinceId;

    /**
     * 市县域id
     */
    public int cityId;

    /**
     * 制造商id
     */
    public byte[] productorId = new byte[5];

    /**
     * 终端类型
     */
    public byte[] termType = new byte[20];

    /**
     * 终端id
     */
    public byte[] termId = new byte[7];

    /**
     * 车牌颜色
     */
    public int carBrandColor;

    /**
     * 车辆标志
     */
    public String carFlag;

    public MsgTermRegist0100() {
    }

    public MsgTermRegist0100(DCWPacket packet) {
        packet.msgId = ID_TERM_REGIST;
        unpack(packet.payload);
    }


    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_TERM_REGIST;
        packet.payload.putUnsignedShort(provinceId);
        packet.payload.putUnsignedShort(cityId);
        if (null != productorId && productorId.length > 0) {
            for (int i = 0; i < productorId.length; i++) {
                packet.payload.putByte(productorId[i]);
            }
        }

        if (null != termType) {
            for (int i = 0; i < termType.length; i++) {
                packet.payload.putByte(termType[i]);
            }
        }

        if (null != termId) {
            for (int i = 0; i < termId.length; i++) {
                packet.payload.putByte(termId[i]);
            }
        }

        packet.payload.putByte((byte) carBrandColor);
        if (null != carFlag) {
            byte[] bytes = carFlag.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                packet.payload.putByte(bytes[i]);
            }
        }
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        provinceId = payload.getUnsignedShort();
        cityId = payload.getUnsignedShort();
        byte[] tempProductorId = new byte[5];
        for (int i = 0; i < tempProductorId.length; i++) {
            tempProductorId[i] = payload.getByte();
        }
        productorId = tempProductorId;

        byte[] tempTermType = new byte[20];
        for (int i = 0; i < tempTermType.length; i++) {
            tempTermType[i] = payload.getByte();
        }
        termType = tempTermType;

        byte[] tempTermId = new byte[7];
        for (int i = 0; i < tempTermId.length; i++) {
            tempTermId[i] = payload.getByte();
        }
        termId = tempTermId;

        carBrandColor = payload.getByte();

        byte[] tempCarFlag = new byte[payload.size() - payload.index];
        for (int i = 0; i < tempCarFlag.length; i++) {
            tempCarFlag[i] = payload.getByte();
        }

        carFlag = new String(tempCarFlag);

    }

    @Override
    public String toString() {
        return "MsgTermRegist0100-终端注册消息{" +
                "msgId="+ Parser.getHexMsgId(ID_TERM_REGIST)+
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", productorId=" + Arrays.toString(productorId) +
                ", termType=" + Arrays.toString(termType) +
                ", termId=" + Arrays.toString(termId) +
                ", carBrandColor=" + carBrandColor +
                ", carFlag='" + carFlag + '\'' +
                '}';
    }
}
