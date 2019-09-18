package com.zz.recycleviewmvptest.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

import java.lang.reflect.Field;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 设备相关工具
 */
public class DeviceUtils {

    /**
     * 判断是否存在sd卡
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得状态栏高度
     * @param context
     * @return
     */
    public static int getStatuBarHeight(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        int h = getHeight(activity);
        return Math.max(h, sbar);
    }

    // 刘海高度
    public static int getHeight(Activity mAc) {
        View decorView = mAc.getWindow().getDecorView();
        if (decorView != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                WindowInsets windowInsets = decorView.getRootWindowInsets();
                if (windowInsets != null) {
//                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
//                    return displayCutout.getSafeInsetTop();
                }
            }
        }
        return 0;
    }
}
