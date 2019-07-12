package com.zz.recycleviewmvptest.widget;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.zz.recycleviewmvptest.R;

/**
 * 防抖动点击
 *
 */
public class AntiShakeUtils {

    private final static long INTERNAL_TIME = 1000; //时间1s

    public static boolean isInvalidClick(@NonNull View target) {
        return isInvalidClick(target, INTERNAL_TIME);
    }

    /**
     * Whether this click event is invalid.
     *
     * @param target       被点击的控件
     * @param internalTime 防抖时间
     * @return true, 在防抖时间内点击了
     */
    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long internalTime) {
        long curTimeStamp = System.currentTimeMillis(); //获得系统时间，即当前点击时的时间
        long lastClickTimeStamp = 0; //存放上次点击的时间
        //给view设置名为last_click_time的tag标签并存储上一次点击的时间，在一定时间内只取一次点击事件
        Object o = target.getTag(R.id.last_click_time);
        if (o == null){
            target.setTag(R.id.last_click_time, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        //这次点击时间 - 上次点击时间 < 防抖时间 则表示该点击事件在防抖时间内点击的
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < internalTime;
        if (!isInvalid)
            target.setTag(R.id.last_click_time, curTimeStamp);
        return isInvalid;
    }
}
