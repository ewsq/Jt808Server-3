package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-10-28 13:58
 * @desc 查询终端属性应答 【上行】
 */
public class MsgACKQueryTermProperty0107 extends DCWMessage {
    public static final int ID_ACK_QUERY_TERM_PROPERTY = 0x0107;

    /**
     * 终端类型 WORD
     * bit0，0：不适用客运车辆，1：适用客运车辆；
     * bit1，0：不适用危险品车辆，1：适用危险品车辆；
     * bit2，0：不适用普通货运车辆，1：适用普通货运车辆；
     * bit3，0：不适用出租车辆，1：适用出租车辆；
     * bit6，0：不支持硬盘录像，1：支持硬盘录像；
     * bit7，0：一体机，1：分体机。
     */
    public int termType;

    /**
     * 制造商id  5 个字节，终端制造商编码。
     */
    public byte[] productId = new byte[5];

    /**
     * 终端型号 20 个字节，此终端型号由制造商自行定义，位数不足时，
     * 后补“0X00”
     */
    public byte[] termModel = new byte[20];

    /**
     * 终端id 7 个字节，由大写字母和数字组成，此终端 ID 由制造商
     * 自行定义，位数不足时，后补“0X00”。
     */
    public byte[] termId = new byte[7];

    /**
     * 终端 SIM 卡 ICCID 号 BCD[10]码
     */
    public byte[] simId = new byte[10];

    /**
     *硬件 版本号长度 byte
     */
    public byte hardwareVersionLen;

    /**
     * 硬件版本号
     */
    public String  hardwareVersion;


    /**
     * 固件版本号长度
     */
    public byte firmwareVersionLen;

    /**
     * 固件版本号
     */
    public String firmwareVersion;

    /**
     * GNSS 模块属性
     * bit0，0：不支持 GPS 定位， 1：支持 GPS 定位；
     * bit1，0：不支持北斗定位， 1：支持北斗定位；
     * bit2，0：不支持 GLONASS 定位， 1：支持 GLONASS 定位；
     * bit3，0：不支持 Galileo 定位， 1：支持 Galileo 定位。
     */
    public byte gnss;

    /**
     * 通信模块属性
     * bit0，0：不支持GPRS通信， 1：支持GPRS通信；
     * bit1，0：不支持CDMA通信， 1：支持CDMA通信；
     * bit2，0：不支持TD-SCDMA通信， 1：支持TD-SCDMA通信；
     * bit3，0：不支持WCDMA通信， 1：支持WCDMA通信；
     * bit4，0：不支持CDMA2000通信， 1：支持CDMA2000通信。
     * bit5，0：不支持TD-LTE通信， 1：支持TD-LTE通信；
     * bit7，0：不支持其他通信方式， 1：支持其他通信方式。
     */
    public byte comm;

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_QUERY_TERM_PROPERTY;
        packet.payload.putShort((short) termType);
        if (null != productId) {
            for (int i = 0; i < productId.length; i++) {
                packet.payload.putByte(productId[i]);
            }
        }

        if (null != termModel) {
            for (int i = 0; i < termModel.length; i++) {
                packet.payload.putByte(termModel[i]);
            }
        }

        if (null != termId) {
            for (int i = 0; i < termId.length; i++) {
                packet.payload.putByte(termId[i]);
            }
        }

        if (null != simId) {
            for (int i = 0; i < simId.length; i++) {
                packet.payload.putByte(simId[i]);
            }
        }

        packet.payload.putByte(hardwareVersionLen);

        if (null != hardwareVersion && hardwareVersion.length() == hardwareVersionLen) {
            packet.payload.putString(hardwareVersion,"utf-8");
        }

        packet.payload.putByte(firmwareVersionLen);

        if (null != firmwareVersion && firmwareVersion.length() == firmwareVersionLen) {
            packet.payload.putString(firmwareVersion,"utf-8");
        }

        packet.payload.putByte(gnss);

        packet.payload.putByte(comm);

        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
