package com.dcw.jt808server.jt808;


import android.text.TextUtils;


import com.dcw.jt808server.jt808.msg.MsgCapture8801;
import com.dcw.jt808server.jt808.msg.MsgF903;
import com.dcw.jt808server.jt808.msg.MsgIssueTermUpgradePackage8108;
import com.dcw.jt808server.jt808.msg.MsgIssueTxt8300;
import com.dcw.jt808server.jt808.msg.MsgLoginACK8101;
import com.dcw.jt808server.jt808.msg.MsgNotice8109;
import com.dcw.jt808server.jt808.msg.MsgPlatACK8001;
import com.dcw.jt808server.jt808.msg.MsgQueryLocation8201;
import com.dcw.jt808server.jt808.msg.MsgQueryResourceList9205;
import com.dcw.jt808server.jt808.msg.MsgQueryTermMediaParams9003;
import com.dcw.jt808server.jt808.msg.MsgQueryTermParam8106;
import com.dcw.jt808server.jt808.msg.MsgQueryTermParams8104;
import com.dcw.jt808server.jt808.msg.MsgQueryTermProperty8107;
import com.dcw.jt808server.jt808.msg.MsgRTC9101;
import com.dcw.jt808server.jt808.msg.MsgRTC9102;
import com.dcw.jt808server.jt808.msg.MsgRequestFileUpload9206;
import com.dcw.jt808server.jt808.msg.MsgSettingTermParams8103;
import com.dcw.jt808server.jt808.msg.MsgTempLocationTrack8202;
import com.dcw.jt808server.jt808.msg.MsgTermControl8105;
import com.dcw.jt808server.jt808.msg.MsgTermRegAck8100;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalAperture9303;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalFocal9302;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalInfrared9305;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalRotation9301;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalWiper9304;
import com.dcw.jt808server.jt808.msg.gimbal.MsgGimbalZoom9306;

import java.nio.ByteBuffer;

/**
 * @author lixiaobin
 * @date 2019-10-17 15:33
 * @desc 808协议数据包
 */
public class DCWPacket {
    private static int seqmain;
    public static final int MAX_SERIALID = Short.MAX_VALUE - Short.MIN_VALUE;

    /**
     * 消息体封装项
     */
    public final DataPacket payload;

    /**
     * 消息开始标志
     */
    public static final int TAG_START = 0x7E;

    /**
     * 消息结尾标志
     */
    public static final int TAG_END = 0x7E;

    /**
     * 消息id
     */
    public int msgId;

    /**
     * 是否分包
     */
    public boolean isMultiPacket;

    /**
     * 加密类型
     */
    public byte encryptionType;

    /**
     * 消息体长度
     */
    public int len;

    public void setTermSn(String termSn) {
        this.termSn = termSn;
    }

    /**
     * 终端号
     */
    public String termSn;

    /**
     * 终端消息流水
     */
    public int seq;

    /**
     * 分包总包数
     */
    public int nPackets;

    /**
     * 分包包序列
     */
    public int seqPacket;

    /**
     * 校验码
     */
    public byte checkCode;

    public DCWPacket() {
        seq = getSerialId();
        payload = new DataPacket();

    }

    public boolean payloadIsFilled() {
        if (payload.size() >= DataPacket.MAX_PAYLOAD_SIZE - 1) {
            return true;
        }
        return (payload.size() == len);
    }

    //获得发送序列号
    public synchronized static int getSerialId() {
        if (seqmain > MAX_SERIALID) {
            seqmain = 0;
        }
        seqmain++;
        return seqmain;
    }

    /**
     * 封装包数据
     *
     * @return
     */
    public byte[] encodePacket() {
        int lenHead = isMultiPacket ? 16 : 12; //分包的话多4个字节
        int lenTotal = 1 + lenHead + len + 2;
        byte[] buffer = new byte[lenTotal];

        int i = 0;

        //开始标记(1)
        buffer[i++] = (byte) TAG_START;


        /****************************************消息头 start************************************/
        //消息id(2)
        buffer[i++] = (byte) (msgId >> 8);
        buffer[i++] = (byte) msgId;

        //消息体属性(2)-是否分包
        short msgAttr = (short) ((isMultiPacket ? 1 : 0) << 13);
        //消息体属性(2)-数据加密方式
        msgAttr = (short) (msgAttr | (encryptionType << 10 & 0x1C00)); //0001 1100 0000 0000
        //消息体属性(2)-消息体长度
        msgAttr = (short) (msgAttr | (len & 0x3FF)); // 0000 0011 1111 1111
        buffer[i++] = (byte) ((msgAttr >> 8) & 0xFF);
        buffer[i++] = (byte) msgAttr;

        //终端手机号(6)
        if (TextUtils.isEmpty(termSn)) {
            termSn = "000000000000";
        }
        System.arraycopy(Parser.str2Bcd(termSn), 0, buffer, i, 6);
        i += 6;

        //消息流水号(2)
        System.arraycopy(Parser.sortToByte(Parser.int2Bytes(seq, 2)), 0, buffer, i, 2);
        i += 2;

        //消息包封装项(4)
        if (isMultiPacket) {
            //--消息包总数(2)
            buffer[i++] = (byte) ((nPackets >> 8) & 0xFF);
            buffer[i++] = (byte) (nPackets & 0xFF);
            //--包序号(2)
            buffer[i++] = (byte) ((seqPacket >> 8) & 0xFF);
            buffer[i++] = (byte) (seqPacket & 0xFF);
        }
        /****************************************消息头 end************************************/


        //消息体(len)
        for (int j = 0; j < payload.size(); j++) {
            buffer[i++] = payload.payload.get(j);
        }

        //校验码(1)
        checkCode = Parser.generateCheckCode(buffer, 1, i);
        buffer[i++] = checkCode;

        //结束标记(1)
        buffer[i++] = (byte) TAG_END;

        //转义
        ByteBuffer tempBuffer = ByteBuffer.allocate(buffer.length * 2);
        for (int j = 0; j < buffer.length ; j++) {
            if (buffer[j] == 0x7E && j != 0 && j != buffer.length - 1) {
                //除了第一个和最后一个，中间的都需要转义
                tempBuffer.put((byte) 0x7D);
                tempBuffer.put((byte) 0x02);
            } else if (buffer[j] == 0x7D) {
                tempBuffer.put((byte) 0x7D);
                tempBuffer.put((byte) 0x01);
            } else {
                tempBuffer.put(buffer[j]);
            }
        }
        tempBuffer.flip();
        byte[] ret = new byte[tempBuffer.limit()];
        tempBuffer.get(ret);

        return ret;
    }


    /**
     * 解析包数据
     *
     * @return
     */
    public DCWMessage unpack() {
        switch (msgId) {
            case MsgPlatACK8001.ID_PLAT_ACK: //平台通用应答
                return new MsgPlatACK8001(this);
            case MsgTermRegAck8100.ID_TERM_REG: //终端注册应答
                return new MsgTermRegAck8100(this);
            case MsgLoginACK8101.ID_LOGIN_ACK: // 终端登录应答
                return new MsgLoginACK8101(this);
            case MsgF903.ID_F903: //透传下行
                return new MsgF903(this);
            case MsgIssueTxt8300.ID_ISSUE_TXT: //消息下发
                return new MsgIssueTxt8300(this);
            case MsgIssueTermUpgradePackage8108.ID_ISSUE_TERM_UPGRAD_PACKAGE: //下发终端升级包
                return new MsgIssueTermUpgradePackage8108(this);
            case MsgQueryLocation8201.ID_QUERY_LOCATION: //查询终端位置信息
                return new MsgQueryLocation8201(this);
            case MsgQueryTermParam8106.ID_QUERY_TERM_PARM://查询指定终端参数
                return new MsgQueryTermParam8106(this);
            case MsgQueryTermParams8104.ID_QUERY_TERM_PARAMS: //查询终端参数
                return new MsgQueryTermParams8104(this);
            case MsgQueryTermProperty8107.ID_QUERY_TERM_PROPERTY://查询终端属性:
                return new MsgQueryTermProperty8107(this);
            case MsgSettingTermParams8103.ID_SETTING_TERM_PARAMS://设置终端参数:
                return new MsgSettingTermParams8103(this);
            case MsgTempLocationTrack8202.ID_TEMP_LOCATION_TRACK://临时位置跟踪控制:
                return new MsgTempLocationTrack8202(this);
            case MsgTermControl8105.ID_TERM_CONTROL://终端控制:
                return new MsgTermControl8105(this);
            case MsgRTC9101.ID_RTC_TRANSFER: //音视频传输控制
                return new MsgRTC9101(this);
            case MsgRTC9102.ID_RTC_CONTROL: //音视频控制
                return new MsgRTC9102(this);
            case MsgNotice8109.ID_NOTICE: //登录通知
                return new MsgNotice8109(this);
            case MsgQueryResourceList9205.ID_QUERY_RESOURCE_LIST: //查询资源列表
                return new MsgQueryResourceList9205(this);
            case MsgGimbalRotation9301.ID_GIMBAL_CONTROL://云台旋转
                return new MsgGimbalRotation9301(this);
            case MsgGimbalFocal9302.ID_GIMBAL_FOCAL: //云台焦距
                return new MsgGimbalFocal9302(this);
            case MsgGimbalAperture9303.ID_GIMBAL_APERTURE: //云台光圈
                return new MsgGimbalAperture9303(this);
            case MsgGimbalWiper9304.ID_GIMBAL_WIPER: //云台雨刷
                return new MsgGimbalWiper9304(this);
            case MsgGimbalInfrared9305.ID_GIMBAL_INFRARED: //云台红外
                return new MsgGimbalInfrared9305(this);
            case MsgGimbalZoom9306.ID_GIMBAL_ZOOM: //云台变倍
                return new MsgGimbalZoom9306(this);
            case MsgCapture8801.ID_CAPTURE: //摄像头立即拍摄
                return new MsgCapture8801(this);
            case MsgQueryTermMediaParams9003.ID_QUERY_TERM_MEDIA_PARAMS://查询终端音视频属性
                return new MsgQueryTermMediaParams9003(this);
            case MsgRequestFileUpload9206.ID_FILE_UPLOAD://文件上传指令
                return new MsgRequestFileUpload9206(this);
        }

        return null;
    }
}
