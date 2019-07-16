package com.zz.recycleviewmvptest.widget.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;

public class CurrButtonFrameLayout extends FrameLayout {
    private ImageView mIvLeft;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvRight;
    private View mVButtomDriver;
    public CurrButtonFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CurrButtonFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CurrButtonFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_curr_button, this);
        mIvLeft = findViewById(R.id.iv_bt_left);
        mTvLeft = findViewById(R.id.tv_bt_left);
        mTvRight = findViewById(R.id.tv_bt_right);
        mIvRight = findViewById(R.id.iv_bt_right);
        mVButtomDriver = findViewById(R.id.v_buttom_driver);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CurrButtonFrameLayout);
        Drawable leftImage = array.getDrawable(R.styleable.CurrButtonFrameLayout_leftImage);
        Drawable rightImage = array.getDrawable(R.styleable.CurrButtonFrameLayout_rightImage);
        String leftText = array.getString(R.styleable.CurrButtonFrameLayout_leftText);
        String rightText = array.getString(R.styleable.CurrButtonFrameLayout_rightText);
        String rightHint = array.getString(R.styleable.CurrButtonFrameLayout_rightHintText);
        ColorStateList leftTextColor = array.getColorStateList(R.styleable.CurrButtonFrameLayout_leftTextColor);
        ColorStateList rightTextColor = array.getColorStateList(R.styleable.CurrButtonFrameLayout_rightTextColor);
        ColorStateList rightHintColor = array.getColorStateList(R.styleable.CurrButtonFrameLayout_rightHintColor);
        boolean showLine = array.getBoolean(R.styleable.CurrButtonFrameLayout_showLine, true);
        array.recycle();
        if (!TextUtils.isEmpty(leftText)) {
            mTvLeft.setText(leftText);
        }
        if (leftTextColor != null) {
            mTvLeft.setTextColor(leftTextColor);
        }
        if (rightTextColor != null) {
            mTvRight.setTextColor(rightTextColor);
        }
        if (rightHintColor != null){
            mTvRight.setHintTextColor(rightHintColor);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mTvRight.setText(rightText);
        }
        if (!TextUtils.isEmpty(rightHint)) {
            mTvRight.setHint(rightHint);
        }
        mVButtomDriver.setVisibility(showLine ? VISIBLE : GONE);
        if (leftImage == null) {
            mIvLeft.setVisibility(GONE);
        } else {
            mIvLeft.setVisibility(VISIBLE);
            mIvLeft.setImageDrawable(leftImage);
        }
        mIvRight.setImageDrawable(rightImage);
    }

    public ImageView getmIvLeft() {
        return mIvLeft;
    }

    public TextView getmTvLeft() {
        return mTvLeft;
    }

    public TextView getmTvRight() {
        return mTvRight;
    }

    public ImageView getmIvRight() {
        return mIvRight;
    }
}
