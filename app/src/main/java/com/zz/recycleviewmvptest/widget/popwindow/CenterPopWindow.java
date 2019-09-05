package com.zz.recycleviewmvptest.widget.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;

/**
 * author: wuyangyi
 * date: 2019-09-05
 */
public class CenterPopWindow extends PopupWindow {
    public static final float POPUPWINDOW_ALPHA = .7f; //默认的透明度
    public static final int NO_ANIMATION = -1; //关闭弹出动画
    protected Activity mActivity;
    protected View mParentView;
    protected View mContentView;

    protected boolean mIsOutsideTouch; //点击外围隐藏
    protected boolean mIsFocus;
    protected float mAlpha;
    protected int mAnimationStyle; //动画
    private Drawable mBackgroundDrawable = new ColorDrawable(0x00000000);// 默认为透明;

    private String mHint; //中间提示
    private String mCancel; //取消按钮
    private String mSure; //确定

    protected CancelClickListener mCancelClickListener;
    protected SureClickListener mSureClickListener;
    protected CenterPopupWindowShowOrDismissListener mCenterPopupWindowShowOrDismissListener;

    public CenterPopWindow(Builder builder) {
        this.mActivity = builder.mActivity;
        this.mParentView = builder.parentView;
        this.mIsOutsideTouch = builder.mIsOutsideTouch;
        this.mIsFocus = builder.mIsFocus;
        this.mAlpha = builder.mAlpha;
        this.mHint = builder.mHint;
        this.mCancel = builder.mCancel;
        this.mSure = builder.mSure;
        this.mCancelClickListener = builder.mCancelClickListener;
        this.mSureClickListener = builder.mSureClickListener;
        this.mCenterPopupWindowShowOrDismissListener = builder.mCenterPopupWindowShowOrDismissListener;
        if (canInitView()) {
            initView();
        }
    }

    protected void initView() {
        initLayout();
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(mIsFocus);
        setOutsideTouchable(mIsOutsideTouch);
        setBackgroundDrawable(mBackgroundDrawable);
        setContentView(mContentView);
        if (mAnimationStyle == NO_ANIMATION) {
            return;
        }
        setAnimationStyle(mAnimationStyle);
    }

    protected int getLayoutId() {
        return R.layout.center_popwindow;
    }

    protected void initLayout() {
        mContentView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        TextView mTvHint = mContentView.findViewById(R.id.tv_hint);
        mTvHint.setText(mHint);
        TextView mTvCancel = mContentView.findViewById(R.id.tv_cancel);
        mTvCancel.setText(mCancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCancelClickListener.onItemClicked();
            }
        });
        TextView mTvSure = mContentView.findViewById(R.id.tv_sure);
        mTvSure.setText(mSure);
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSureClickListener.onItemClicked();
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });
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
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (mCenterPopupWindowShowOrDismissListener != null) {
            mCenterPopupWindowShowOrDismissListener.onShow();
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        if (mCenterPopupWindowShowOrDismissListener != null) {
            mCenterPopupWindowShowOrDismissListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        if (mAnimationStyle == NO_ANIMATION) {
            mContentView.clearAnimation();
        }
        super.dismiss();
        if (mCenterPopupWindowShowOrDismissListener != null) {
            mCenterPopupWindowShowOrDismissListener.onDismiss();
        }
    }

    /**
     * 隐藏popupwindow
     */
    public void hide() {
        dismiss();
    }


    public static ActivePopWindow.Builder builder() {
        return new ActivePopWindow.Builder();
    }

    /**
     * 默认显示到中部
     */
    public void show() {
        setWindowAlpha(mAlpha);
        if (mParentView == null) {
            showAtLocation(mContentView, Gravity.CENTER, 0, 0);
        } else {
            showAtLocation(mParentView, Gravity.CENTER, 0, 0);
        }
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * 子类重新调用initView
     *
     * @return
     */
    protected boolean canInitView() {
        return true;
    }

    public static class Builder {
        protected Activity mActivity;
        protected View parentView;

        protected float mAlpha = POPUPWINDOW_ALPHA;
        protected boolean mIsOutsideTouch = true;// 默认为true
        protected boolean mIsFocus = true;// 默认为true
        protected int mAnimationStyle; //动画

        private String mHint; //中间提示
        private String mCancel; //取消按钮
        private String mSure; //确定

        protected CancelClickListener mCancelClickListener;
        protected SureClickListener mSureClickListener;
        protected CenterPopupWindowShowOrDismissListener mCenterPopupWindowShowOrDismissListener;

        public Builder() {
        }

        public Builder(CenterPopWindow popWindow) {
            this.mActivity = popWindow.mActivity;
            this.parentView = popWindow.mParentView;
            this.mAlpha = popWindow.mAlpha;
            this.mIsFocus = popWindow.mIsFocus;
            this.mIsOutsideTouch = popWindow.mIsOutsideTouch;
            this.mAnimationStyle = popWindow.mAnimationStyle;
            this.mHint = popWindow.mHint;
            this.mCancel = popWindow.mCancel;
            this.mSure = popWindow.mSure;
            this.mCancelClickListener = popWindow.mCancelClickListener;
            this.mSureClickListener = popWindow.mSureClickListener;
            this.mCenterPopupWindowShowOrDismissListener = popWindow.mCenterPopupWindowShowOrDismissListener;
        }

        public CenterPopWindow.Builder with(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public CenterPopWindow.Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public CenterPopWindow.Builder isOutsideTouch(boolean isOutsideTouch) {
            this.mIsOutsideTouch = isOutsideTouch;
            return this;
        }

        public CenterPopWindow.Builder isFocus(boolean isFocus) {
            this.mIsFocus = isFocus;
            return this;
        }

        public CenterPopWindow.Builder backgroundAlpha(float alpha) {
            this.mAlpha = alpha;
            return this;
        }

        public CenterPopWindow.Builder dismissListener(CenterPopupWindowShowOrDismissListener centerPopupWindowShowOrDismissListener) {
            this.mCenterPopupWindowShowOrDismissListener = centerPopupWindowShowOrDismissListener;
            return this;
        }

        public CenterPopWindow build() {
            return new CenterPopWindow(this);
        }

        public CenterPopWindow.Builder hintString(String hint) {
            this.mHint = hint;
            return this;
        }
        public CenterPopWindow.Builder cancelString(String cancel) {
            this.mCancel = cancel;
            return this;
        }
        public CenterPopWindow.Builder sureString(String sure) {
            this.mSure = sure;
            return this;
        }

        public CenterPopWindow.Builder cancelClickListener(CancelClickListener cancelClickListener){
            this.mCancelClickListener = cancelClickListener;
            return this;
        }

        public CenterPopWindow.Builder sureClickListener(SureClickListener sureClickListener){
            this.mSureClickListener = sureClickListener;
            return this;
        }


    }

    public interface ItemClickListener {
        void onItemClicked();
    }

    public interface CancelClickListener extends ItemClickListener {}

    public interface SureClickListener extends ItemClickListener {}

    public interface CenterPopupWindowShowOrDismissListener {
        void onDismiss();

        void onShow();
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}
