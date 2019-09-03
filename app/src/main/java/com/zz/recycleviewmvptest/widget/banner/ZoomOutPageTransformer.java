package com.zz.recycleviewmvptest.widget.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * author: wuyangyi
 * date: 2019-09-03
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View view, float position) {
        if (position < -1) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            view.setScaleX(scaleFactor);
            if (position > 0) {
                view.setTranslationX(-scaleFactor * 2);
            } else if (position < 0) {
                view.setTranslationX(scaleFactor * 2);
            }
            view.setScaleY(scaleFactor);
        } else {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }

    }
}
