package com.zz.recycleviewmvptest.widget.toast;

import android.view.Gravity;

import com.hjq.toast.IToastStyle;

/**
 * author: wuyangyi
 * date: 2019-09-19
 * 自定义Toast样式
 */
public class ToastSystemStyle implements IToastStyle {
    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public int getCornerRadius() {
        return 15;
    }

    @Override
    public int getBackgroundColor() {
        return 0XFF333333;
    }

    @Override
    public int getTextColor() {
        return 0XFFE3E3E3;
    }

    @Override
    public float getTextSize() {
        return 12;
    }

    @Override
    public int getMaxLines() {
        return 3;
    }

    @Override
    public int getPaddingLeft() {
        return 10;
    }

    @Override
    public int getPaddingTop() {
        return 7;
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }

    @Override
    public int getPaddingBottom() {
        return getPaddingTop();
    }
}
