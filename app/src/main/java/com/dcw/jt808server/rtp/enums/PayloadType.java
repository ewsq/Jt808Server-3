package com.dcw.jt808server.rtp.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lixiaobin
 * @date 2020-03-06 09:51:51
 * @desc 描述信息
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({PayloadType.AUDIO_AAC,PayloadType.VIDEO_H264,PayloadType.VIDEO_H265,PayloadType.AUDIO_PCM_VOICE,PayloadType.AUDIO_PCM_AUDIO,PayloadType.AUDIO_HEAAC,PayloadType.AUDIO_AACLC})
public @interface PayloadType {
    int AUDIO_G726 = 8;
    int AUDIO_AAC = 19;
    int AUDIO_PCM_VOICE = 22;
    int AUDIO_PCM_AUDIO = 23;
    int VIDEO_H264 = 98;
    int VIDEO_H265 = 99;
    int AUDIO_HEAAC = 21;
    int AUDIO_AACLC = 24;
}
