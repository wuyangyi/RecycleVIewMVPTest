package com.zz.recycleviewmvptest.widget.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.widget.DeviceUtils;
import com.zz.recycleviewmvptest.widget.Utils;

/**
 * author: wuyangyi
 * date: 2019-09-25
 * 菜单
 */
public class TopMenuPopWindow extends PopupWindow {
    protected Activity mActivity;
    protected View mParentView;
    protected View mContentView;

    protected boolean mIsOutsideTouch; //点击外围隐藏
    protected boolean mIsFocus;
    protected float mAlpha;
    protected int mAnimationStyle; //动画
    protected CommonAdapter adapter;
    private Drawable mBackgroundDrawable = new ColorDrawable(0x00000000);// 默认为透明;
    public TopMenuPopWindow(Builder builder) {
        this.mActivity = builder.mActivity;
        this.mParentView = builder.parentView;
        this.mIsOutsideTouch = builder.mIsOutsideTouch;
        this.mIsFocus = builder.mIsFocus;
        this.mAlpha = builder.mAlpha;
        this.adapter = builder.adapter;
        initView();

    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static TopMenuPopWindow.Builder builder() {
        return new TopMenuPopWindow.Builder();
    }


    protected void initView() {
        initLayout();
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(mIsFocus);
        setOutsideTouchable(mIsOutsideTouch);
        setBackgroundDrawable(mBackgroundDrawable);
        setContentView(mContentView);
        if (mAnimationStyle == 1) {
            return;
        }
        setAnimationStyle(mAnimationStyle > 0 ? mAnimationStyle : R.style.style_faceAnimation);
    }

    protected void initLayout() {
        mContentView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        RecyclerView recyclerView = mContentView.findViewById(R.id.rvMenuList);
        if (adapter != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });
    }

    protected int getLayoutId() {
        return R.layout.popwindow_menu;
    }

    /**
     * 默认显示到顶部
     */
    public void show() {
        setWindowAlpha(mAlpha);
        if (mParentView == null) {
            showAtLocation(mContentView, Gravity.TOP, 0, (int) (mActivity.getResources().getDimension(R.dimen.title_height) + DeviceUtils.getStatuBarHeight(mActivity, mActivity)));
        } else {
            showAtLocation(mParentView, Gravity.TOP, 0, (int) (mActivity.getResources().getDimension(R.dimen.title_height) + DeviceUtils.getStatuBarHeight(mActivity, mActivity)));
        }
    }

    @Override
    public void dismiss() {
        if (mAnimationStyle == -1) {
            mContentView.clearAnimation();
        }
        super.dismiss();
    }

    /**
     * 隐藏popupwindow
     */
    public void hide() {
        dismiss();
    }

    /**
     * 设置屏幕的透明度
     *
     * @param alpha 需要设置透明度
     */
    protected void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        params.verticalMargin = 100;
        mActivity.getWindow().setAttributes(params);
    }

    public static class Builder {
        protected Activity mActivity;
        protected View parentView;

        protected float mAlpha = 1;
        protected boolean mIsOutsideTouch = true;// 默认为true
        protected boolean mIsFocus = true;// 默认为true
        protected int mAnimationStyle; //动画
        protected CommonAdapter adapter;

        protected Builder() {}

        protected Builder(TopMenuPopWindow popWindow) {
            this.mActivity = popWindow.mActivity;
            this.parentView = popWindow.mParentView;
            this.mAlpha = popWindow.mAlpha;
            this.mIsFocus = popWindow.mIsFocus;
            this.mIsOutsideTouch = popWindow.mIsOutsideTouch;
            this.mAnimationStyle = popWindow.mAnimationStyle;
            this.adapter = popWindow.adapter;
        }

        public TopMenuPopWindow.Builder with(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public TopMenuPopWindow.Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public TopMenuPopWindow.Builder animationStyle(int animationStyle) {
            this.mAnimationStyle = animationStyle;
            return this;
        }

        public TopMenuPopWindow.Builder isOutsideTouch(boolean isOutsideTouch) {
            this.mIsOutsideTouch = isOutsideTouch;
            return this;
        }

        public TopMenuPopWindow.Builder isFocus(boolean isFocus) {
            this.mIsFocus = isFocus;
            return this;
        }

        public TopMenuPopWindow.Builder backgroundAlpha(float alpha) {
            this.mAlpha = alpha;
            return this;
        }

        public TopMenuPopWindow.Builder adater(CommonAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public TopMenuPopWindow build() {
            return new TopMenuPopWindow(this);
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}
