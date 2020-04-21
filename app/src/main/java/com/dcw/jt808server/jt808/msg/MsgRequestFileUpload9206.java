package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.Tools;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author lixiaobin
 * @date 2020-03-31 14:23:52
 * @desc 文件上传指令
 */
public class MsgRequestFileUpload9206 extends DCWMessage {
    public static final int ID_FILE_UPLOAD = 0x9206;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyMMddHHmmss");

    /**
     * BYTE
     * 服务器地址长度
     */
    public int serverAddrLength;

    /**
     * string
     * 服务器地址
     */
    public String serverAddr;

    /*
     *WORD
     * 服务器端口
     */
    public int serverPort;

    /**
     * BYTE
     * 用户名长度
     */
    public int unameLength;

    /**
     * string
     * 用户名
     */
    public String uname;

    /**
     * 密码长度
     */
    public int pwdLength;

    /**
     * string
     * 密码
     */
    public String pwd;


    /**
     * byte
     * 文件上传路径长度
     */
    public int filePathLength;

    /**
     * 文件上传路径
     */
    public String filePath;

    /**
     * 逻辑通道号
     */
    public int channel;

    /**
     * BCD[6]
     * 开始时间
     */
    public long startTime;

    /**
     * BCD[6]
     * 结束时间
     */
    public long endTime;

    /**
     * 64BITS
     * 报警标志
     */
    public long alarmSign;


    /**
     * byte
     * 音视频资源类型
     * 0:音视频 1:音频 2:视频 3:视频或音频
     */
    public int resourceType;


    /**
     * byte
     * 码流类型
     * 0：主码流或子码流 1:主码流 2:子码流
     */
    public int streamType;

    /**
     * byte
     * 存储位置
     * 0:主存储器或灾备存储器 1:主存储器 2:灾备储存器
     */
    public int storageLocation;

    /**
     * byte
     * 任务执行条件
     * bit0：WIFI,为1时表示WI-FI下可下载
     * bit1：LAN,为1时表示LAN连接时可下载
     * bit2:3G/4G，为1时表示3G/4G连接时可下载
     */
    public int taskCondition;

    public MsgRequestFileUpload9206(DCWPacket packet) {
        packet.msgId = ID_FILE_UPLOAD;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        serverAddrLength = payload.getByte();
        if (serverAddrLength>0) {
            byte[] serverAddBytes = new byte[serverAddrLength];
            for (int i = 0; i < serverAddrLength; i++) {
                serverAddBytes[i] = payload.getByte();
            }
            serverAddr = new String(serverAddBytes, Charset.forName("utf-8"));
        }

        serverPort = payload.getShort();

        unameLength = payload.getByte();
        if (unameLength > 0) {
            byte[] unameBytes = new byte[unameLength];
            for (int i = 0; i < unameLength; i++) {
                unameBytes[i] = payload.getByte();
            }
            uname = new String(unameBytes, Charset.forName("utf-8"));
        }

        pwdLength = payload.getByte();
        if (pwdLength > 0) {
            byte[] pwdBytes = new byte[pwdLength];
            for (int i = 0; i < pwdLength; i++) {
                pwdBytes[i] = payload.getByte();
            }
            pwd = new String(pwdBytes, Charset.forName("utf-8"));
        }

        filePathLength = payload.getByte();
        if (filePathLength > 0) {
            byte[] filePathBytes = new byte[filePathLength];
            for (int i = 0; i < filePathLength; i++) {
                filePathBytes[i] = payload.getByte();
            }
            filePath = new String(filePathBytes,Charset.forName("utf-8"));
        }

        channel = payload.getByte();

        byte[] startBytes = new byte[6];
        for (int i = 0; i < startBytes.length; i++) {
            startBytes[i] = payload.getByte();
        }
        String startStr = Tools.bcd2Str(startBytes);
        try {
            startTime = mDateFormat.parse(startStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        byte[] endBytes = new byte[6];
        for (int i = 0; i < endBytes.length; i++) {
            endBytes[i] = payload.getByte();
        }
        String endStr = Tools.bcd2Str(endBytes);
        try {
            endTime = mDateFormat.parse(endStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        alarmSign = payload.getLong();

        resourceType = payload.getByte();
        streamType = payload.getByte();
        storageLocation = payload.getByte();
        taskCondition = payload.getByte();
    }
}
