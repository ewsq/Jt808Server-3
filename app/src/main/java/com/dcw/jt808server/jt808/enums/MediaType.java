package com.dcw.jt808server.jt808.enums;

/**
 * @author lixiaobin
 * @date 2020-01-16 16:30
 * @desc 媒体编码类型
 */
public enum MediaType {
    AUDIO_G721(1),
    AUDIO_G722(2),
    AUDIO_G723(3),
    AUDIO_G728(4),
    AUDIO_G729(5),
    AUDIO_G711A(6),
    AUDIO_G711U(7),
    AUDIO_AAC(19),
    AUDIO_PCM_VOICE(22),
    AUDIO_PCM(23),
    AUDIO_AACLC(24),
    AUDIO_MP3(25),
    VIDEO_H264(98),
    VIDEO_H265(99),
    ;

    private final int value;

    MediaType(int i) {
        value = i;
    }
}

