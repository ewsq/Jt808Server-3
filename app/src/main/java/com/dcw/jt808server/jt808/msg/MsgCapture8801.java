package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.Parser;

/**
 * @author lixiaobin
 * @date 2020-03-19 13:46:42
 * @desc 描述信息
 */
public class MsgCapture8801 extends DCWMessage {
    public static final int ID_CAPTURE = 0x8801;
    public static final int CMD_RECORD = 0xFFFF;

    /**
     * BYTE
     * 通道 大于0
     */
    public int channel;

    /**
     * WORD
     * 拍摄命令
     */
    public int cmd;

    /**
     * WORD
     * 拍照间隔/录像时间
     */
    public int interval;

    /**
     * 1、保存 0：实时上传
     * 保存标志
     */
    public int saveFlag;

    /**
     * 分辩率
     */
    public int resolution;

    /**
     * 图像/视频质量
     */
    public int quality;

    /**
     * 亮度
     */
    public int brightness;

    /**
     * 对比度
     */
    public int contrast;

    /**
     * 饱和度
     */
    public int saturate;

    /**
     * 色度
     */
    public int chroma;

    public MsgCapture8801(DCWPacket packet) {
        packet.msgId = ID_CAPTURE;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_CAPTURE;



        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        channel = payload.getByte();
        cmd = payload.getShort();
        interval = payload.getShort();
        saveFlag = payload.getByte();
        resolution = payload.getByte();
        quality = payload.getByte();
        brightness = payload.getByte();
        contrast = payload.getByte();
        saturate = payload.getByte();
        chroma = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgCapture8801 摄像头立即拍摄指令{" +
                "msgId="+ Parser.getHexMsgId(ID_CAPTURE) +
                "channel=" + channel +
                ", cmd=" + cmd +
                ", interval=" + interval +
                ", saveFlag=" + saveFlag +
                ", resolution=" + resolution +
                ", quality=" + quality +
                ", brightness=" + brightness +
                ", contrast=" + contrast +
                ", saturate=" + saturate +
                ", chroma=" + chroma +
                '}';
    }
}
