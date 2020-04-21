package com.dcw.jt808server.jt808.msg;


import com.dcw.jt808server.Tools;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

/**
 * @author lixiaobin
 * @date 2019-11-28 15:07
 * @desc 查询资源列表
 */
public class MsgQueryResourceList9205 extends DCWMessage {

    public static final int ID_QUERY_RESOURCE_LIST = 0x9205;

    /**
     * 逻辑通道号 byte
     */
    public int channel;

    /**
     * 开始时间
     */
    public String startAt;

    /**
     * 结束时间
     */
    public String endAt;

    /**
     * 报警标志
     */
    public long  alarmSign;

    /**
     *  资源类型 0：音视频 1:音频 2:视频 3:视频或音视频
     */
    public int sourceType;

    /**
     * 码流类型
     */
    public int streamType;

    /**
     * 存储器类型
     */
    public int storageType;

    public int serialId;

    public MsgQueryResourceList9205(DCWPacket packet) {
        packet.msgId = ID_QUERY_RESOURCE_LIST;
        serialId = packet.seq;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        return null;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        channel = payload.getByte();
        byte[] startBytes = new byte[6];
        for (int i = 0; i < startBytes.length; i++) {
            startBytes[i] = payload.getByte();
        }
        startAt = Tools.bcd2Str(startBytes);

        byte[] endBytes = new byte[6];
        for (int i = 0; i < endBytes.length; i++) {
            endBytes[i] = payload.getByte();
        }
        endAt = Tools.bcd2Str(endBytes);

        alarmSign = payload.getUnsignedLong();

        sourceType = payload.getByte();
        streamType = payload.getByte();
        storageType = payload.getByte();
    }

    @Override
    public String toString() {
        return "MsgQueryResourceList9205 查询资源列表【下行】{" +
                "channel=" + channel +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", alarmSign=" + alarmSign +
                ", sourceType=" + sourceType +
                ", streamType=" + streamType +
                ", storageType=" + storageType +
                ", serialId=" + serialId +
                '}';
    }
}
