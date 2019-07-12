package com.zz.recycleviewmvptest.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {
    public static String converTimeFormat(String time) {
        String parts[] = time.split("-");
        if (parts.length == 3) {
            return parts[0] + "." + parts[1];
        }
        return time;
    }

    public static float dpToPixel(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
    }

    /**
     * 获得屏幕密度
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}
