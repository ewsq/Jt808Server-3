package com.dcw.jt808server.jt808.entity;

/**
 * @author lixiaobin
 * @date 2019-11-28 15:24
 * @desc 描述信息
 */
public class MediaResource {
    /**
     * 逻辑通道号
     */
    public int channel;

    /**
     * 开始时间 BCD[6]
     */
    public String startAt;

    /**
     * 结束时间 BCD[6]
     */
    public String endAt;

    /**
     * 报警标志
     */
    public long alarmSign;

    /**
     * 音视频资源类型
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

    /**
     *
     */
    public int fileSize;

    @Override
    public String toString() {
        return "MediaResource 媒体资源{" +
                "channel=" + channel +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", alarmSign=" + alarmSign +
                ", sourceType=" + sourceType +
                ", streamType=" + streamType +
                ", storageType=" + storageType +
                ", fileSize=" + fileSize +
                '}';
    }
}
