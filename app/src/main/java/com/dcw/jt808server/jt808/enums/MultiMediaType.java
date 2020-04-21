package com.dcw.jt808server.jt808.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lixiaobin
 * @date 2020-03-19 09:59:17
 * @desc 描述信息
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({MultiMediaType.IMG, MultiMediaType.AUDIO, MultiMediaType.VIDEO})
public @interface MultiMediaType {
    int IMG = 0;
    int AUDIO = 1;
    int VIDEO = 2;
}
