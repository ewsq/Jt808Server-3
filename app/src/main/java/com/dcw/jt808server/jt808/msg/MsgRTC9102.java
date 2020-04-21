package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.io.Serializable;

/**
 * @author lixiaobin
 * @date 2019-11-22 14:08
 * @desc 实时音视频传输控制
 */
public class MsgRTC9102 extends DCWMessage implements Serializable {

    public static final long serialVersionUID = 201911281454L;

    public static final int ID_RTC_CONTROL = 0x9102;

    public static final int CMD_CLOSE_TRANSFER = 0;
    public static final int CMD_SWITCH_OR_STOP_OR_PAUSE = 1;
    public static final int CMD_PAUSE = 2;
    public static final int CMD_RESUME = 3;
    public static final int CMD_TWOWAY_INTERCOM = 4;

    /**
     * 控制指令
     * 0：关闭音视频传输指令
     * 1：切换码流 暂停 或 继续
     * 2：暂停该通道所有流的发送
     * 3：恢复暂停前流的发送，与暂停前流的类型一致
     * 4：关闭双向对讲
     */
    public int controlCmd;

    /**
     * 关闭音频类型
     * 0:关闭该通道有关的音视频数据
     * 1：只关闭该通道有关的音频，保留该通道有关的视频
     * 2：只关闭该通道有关的视频，保留该通道有关的音频
     */
    public int closeAudioType;


    /**
     * 切换码流类型
     * 将之前申请的码流切换为新申请的码流，音频与切换前保持一致。
     * 新申请的码流为:
     * 1:主码流
     * 2：子码流
     */
    public int switchStreamType;

    public MsgRTC9102(DCWPacket packet) {
        packet.msgId = ID_RTC_CONTROL;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        controlCmd = payload.getByte();
        closeAudioType = payload.getByte();
        switchStreamType = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgRTC9102 实时音视频传输控制{" +
                "controlCmd=" + controlCmd +
                ", closeAudioType=" + closeAudioType +
                ", switchStreamType=" + switchStreamType +
                '}';
    }
}
