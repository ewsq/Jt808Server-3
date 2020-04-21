package com.dcw.jt808server.jt808.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lixiaobin
 * @date 2020-03-19 10:00:30
 * @desc 描述信息
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({MultiMediaFormat.JPEG, MultiMediaFormat.TIF, MultiMediaFormat.MP3, MultiMediaFormat.WAV, MultiMediaFormat.WMV})
public @interface MultiMediaFormat {
    int JPEG = 0;
    int TIF = 1;
    int MP3 = 2;
    int WAV = 3;
    int WMV = 4;
}
