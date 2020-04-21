package com.dcw.jt808server.jt808.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lixiaobin
 * @date 2020-03-19 10:01:11
 * @desc 描述信息
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({EventCode.PLATFORM, EventCode.TIMER, EventCode.ROBBERY, EventCode.CRASH,EventCode.DOOR_OPEN_CAPTURE,EventCode.DOOR_CLOSE_CAPTURE,EventCode.DOOR_OPEN_TO_CLOSE,EventCode.FIXDISTANCE_CAPTURE})
public @interface EventCode {
    /**
     * 平台下发指令
     */
    int PLATFORM = 0;

    /**
     * 定时器触发
     */
    int TIMER = 1;

    /**
     * 抢劫报警触发
     */
    int ROBBERY = 2;

    /**
     * 碰撞报警触发
     */
    int CRASH = 3;

    /**
     * 门开拍照
     */
    int DOOR_OPEN_CAPTURE = 4;

    /**
     * 门关拍照
     */
    int DOOR_CLOSE_CAPTURE = 5;

    /**
     * 车门由开变关，时速从<20公里到超过20公里
     */
    int DOOR_OPEN_TO_CLOSE = 6;

    /**
     * 定距拍照
     */
    int FIXDISTANCE_CAPTURE = 7;
}
