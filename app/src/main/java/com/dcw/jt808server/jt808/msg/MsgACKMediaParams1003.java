package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2020-03-31 14:04:32
 * @desc 终端上传音视频属性 {@link MsgQueryTermMediaParams9003}
 */
public class MsgACKMediaParams1003 extends DCWMessage {
    public static final int ID_ACK_MEDIA_PARAMS = 0x1003;

    /**
     * byte
     * 输入音频编码方式
     */
    public int audioCoder;

    /**
     * byte
     * 输入音频声道数
     */
    public int audioChannelCount;

    /**
     * byte
     * 输入音频采样率
     * 0:8KHz
     * 1:22.05kHz
     * 2:44.1kHz
     * 3:48kHz
     */
    public int audioSamplerate;

    /**
     * byte
     * 输入音频采样位数
     * 0:8位
     * 1:16位
     * 2:32位
     */
    public int audioFormat;

    /**
     * WORD
     * 音频帧长度
     */
    public int audioFrameLength;

    /**
     * byte  0：不支持 1:支持
     * 是否支持音频输出
     */
    public boolean supportAudioOutput;

    /**
     * byte
     * 视频编码方式
     */
    public int videoCoder;

    /**
     * byte
     * 音频最大支持通道数
     */
    public int audioSupportMaxChannelCount;

    /**
     * byte
     * 视频最大支持通道数
     */
    public int videoSupportMaxChannelCount;



    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ACK_MEDIA_PARAMS;
        packet.payload.putByte((byte) audioCoder);
        packet.payload.putByte((byte) audioChannelCount);
        packet.payload.putByte((byte) audioSamplerate);
        packet.payload.putByte((byte) audioFormat);
        packet.payload.putShort((byte) audioFrameLength);
        packet.payload.putByte((byte) (supportAudioOutput?1:0));
        packet.payload.putByte((byte) videoCoder);
        packet.payload.putByte((byte) audioSupportMaxChannelCount);
        packet.payload.putByte((byte) videoSupportMaxChannelCount);
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }
}
