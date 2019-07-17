package com.zz.recycleviewmvptest.widget.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;

public class ActivePopWindow extends PopupWindow {
    public static final float POPUPWINDOW_ALPHA = .8f; //默认的透明度
    public static final int NO_ANIMATION = -1; //关闭弹出动画
    protected Activity mActivity;
    protected View mParentView;
    protected View mContentView;

    protected boolean mIsOutsideTouch; //点击外围隐藏
    protected boolean mIsFocus;
    protected float mAlpha;
    protected int mAnimationStyle; //动画
    private Drawable mBackgroundDrawable = new ColorDrawable(0x00000000);// 默认为透明;

    protected String mItem1Str;
    protected String mItem2Str;
    protected String mItem3Str;
    protected String mItem4Str;
    protected String mItem5Str;



    protected String mItem6Str;
    protected String mDesStr;
    protected String mBottomStr;


    protected int mItem1Color;
    protected int mItem2Color;
    protected int mItem3Color;
    protected int mItem4Color;
    protected int mItem5Color;
    protected int mItem6Color;
    protected int mItemDesColor;
    protected int mItemBottomColor;

    protected ActionPopupWindowItem1ClickListener mActionPopupWindowItem1ClickListener;
    protected ActionPopupWindowItem2ClickListener mActionPopupWindowItem2ClickListener;
    protected ActionPopupWindowItem3ClickListener mActionPopupWindowItem3ClickListener;

    protected ActionPopupWindowItem4ClickListener mActionPopupWindowItem4ClickListener;
    protected ActionPopupWindowDesClickListener mActionPopupWindowDesClickListener;
    protected ActionPopupWindowItem5ClickListener mActionPopupWindowItem5ClickListener;
    protected ActionPopupWindowItem6ClickListener mActionPopupWindowItem6ClickListener;
    protected ActionPopupWindowShowOrDismissListener mActionPopupWindowDismissListener;

    protected ActivePopWindow.ActionPopupWindowBottomClickListener mActionPopupWindowBottomClickListener;

    public ActivePopWindow(Builder builder) {
        this.mActivity = builder.mActivity;
        this.mParentView = builder.parentView;
        this.mIsOutsideTouch = builder.mIsOutsideTouch;
        this.mIsFocus = builder.mIsFocus;
        this.mAlpha = builder.mAlpha;

        this.mItem1Str = builder.mItem1Str;
        this.mItem2Str = builder.mItem2Str;
        this.mItem3Str = builder.mItem3Str;
        this.mItem4Str = builder.mItem4Str;
        this.mItem5Str = builder.mItem5Str;
        this.mItem6Str = builder.mItem6Str;
        this.mDesStr = builder.mDesStr;
        this.mBottomStr = builder.mBottomStr;
        this.mItem1Color = builder.mItem1Color;
        this.mItem2Color = builder.mItem2Color;
        this.mItem3Color = builder.mItem3Color;
        this.mItem4Color = builder.mItem4Color;
        this.mItem5Color = builder.mItem5Color;
        this.mItem6Color = builder.mItem6Color;
        this.mItemDesColor = builder.mItemDesColor;
        this.mItemBottomColor = builder.mItemBottomColor;
        this.mAnimationStyle = builder.mAnimationStyle;
        this.mActionPopupWindowItem1ClickListener = builder.mActionPopupWindowItem1ClickListener;
        this.mActionPopupWindowItem2ClickListener = builder.mActionPopupWindowItem2ClickListener;
        this.mActionPopupWindowItem3ClickListener = builder.mActionPopupWindowItem3ClickListener;
        this.mActionPopupWindowItem4ClickListener = builder.mActionPopupWindowItem4ClickListener;
        this.mActionPopupWindowItem5ClickListener = builder.mActionPopupWindowItem5ClickListener;
        this.mActionPopupWindowItem6ClickListener = builder.mActionPopupWindowItem6ClickListener;
        this.mActionPopupWindowDesClickListener = builder.mActionPopupWindowDesClickListener;
        this.mActionPopupWindowBottomClickListener = builder.mActionPopupWindowBottomClickListener;
        this.mActionPopupWindowDismissListener = builder.mActionPopupWindowDismissListener;
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
        setAnimationStyle(mAnimationStyle > 0 ? mAnimationStyle : R.style.style_activePopupAnimation);
    }

    protected int getLayoutId() {
        return R.layout.popwindow_active;
    }

    protected void initLayout() {
        mContentView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
        initItemView(R.id.tv_pop_item1, R.id.v_pop_item1, mItem1Color, mItem1Str, mActionPopupWindowItem1ClickListener);
        initItemView(R.id.tv_pop_item2, R.id.v_pop_item2, mItem2Color, mItem2Str, mActionPopupWindowItem2ClickListener);
        initItemView(R.id.tv_pop_item3, R.id.v_pop_item3, mItem3Color, mItem3Str, mActionPopupWindowItem3ClickListener);
        initItemView(R.id.tv_pop_item4, R.id.v_pop_item4, mItem4Color, mItem4Str, mActionPopupWindowItem4ClickListener);
        initItemView(R.id.tv_pop_des, R.id.v_pop_des, mItemDesColor, mDesStr, mActionPopupWindowDesClickListener);
        initItemView(R.id.tv_pop_item5, R.id.v_pop_item5, mItem5Color, mItem5Str, mActionPopupWindowItem5ClickListener);
        initItemView(R.id.tv_pop_item6, R.id.v_pop_item6, mItem6Color, mItem6Str, mActionPopupWindowItem6ClickListener);
        initItemView(R.id.tv_pop_bottom, R.id.v_pop_bottom, mItem5Color, mBottomStr, mActionPopupWindowBottomClickListener);
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


    protected void initItemView(@IdRes int viewId, @IdRes int viewIds, int viewColor, String text, final ItemClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            TextView itemView = mContentView.findViewById(viewId);
            View v = mContentView.findViewById(viewIds);
            itemView.setVisibility(View.VISIBLE);
            v.setVisibility(View.VISIBLE);
            itemView.setText(text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClicked();
                    }
                }
            });
            if (viewColor != 0) {
                itemView.setTextColor(viewColor);
            }
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (mActionPopupWindowDismissListener != null) {
            mActionPopupWindowDismissListener.onShow();
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        if (mActionPopupWindowDismissListener != null) {
            mActionPopupWindowDismissListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        if (mAnimationStyle == NO_ANIMATION) {
            mContentView.clearAnimation();
        }
        super.dismiss();
        if (mActionPopupWindowDismissListener != null) {
            mActionPopupWindowDismissListener.onDismiss();
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
     * 默认显示到底部
     */
    public void show() {
        setWindowAlpha(mAlpha);
        if (mParentView == null) {
            showAtLocation(mContentView, Gravity.BOTTOM, 0, 0);
        } else {
            showAtLocation(mParentView, Gravity.BOTTOM, 0, 0);
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

        protected String mItem1Str;
        protected String mItem2Str;
        protected String mItem3Str;
        protected String mItem4Str;
        protected String mItem5Str;
        protected String mItem6Str;
        protected String mDesStr;
        protected String mBottomStr;

        protected int mItem1Color;
        protected int mItem2Color;
        protected int mItem3Color;
        protected int mItem4Color;
        protected int mItem5Color;
        protected int mItem6Color;
        protected int mItemDesColor;
        protected int mItemBottomColor;

        protected ActionPopupWindowItem1ClickListener mActionPopupWindowItem1ClickListener;
        protected ActionPopupWindowItem2ClickListener mActionPopupWindowItem2ClickListener;
        protected ActionPopupWindowItem3ClickListener mActionPopupWindowItem3ClickListener;
        protected ActionPopupWindowItem4ClickListener mActionPopupWindowItem4ClickListener;
        protected ActionPopupWindowItem5ClickListener mActionPopupWindowItem5ClickListener;
        protected ActionPopupWindowItem6ClickListener mActionPopupWindowItem6ClickListener;
        protected ActionPopupWindowShowOrDismissListener mActionPopupWindowDismissListener;
        protected ActionPopupWindowDesClickListener mActionPopupWindowDesClickListener;
        protected ActivePopWindow.ActionPopupWindowBottomClickListener mActionPopupWindowBottomClickListener;

        protected Builder() {
        }

        private Builder(ActivePopWindow popWindow) {
            this.mActivity = popWindow.mActivity;
            this.parentView = popWindow.mParentView;
            this.mAlpha = popWindow.mAlpha;
            this.mIsFocus = popWindow.mIsFocus;
            this.mIsOutsideTouch = popWindow.mIsOutsideTouch;
            this.mAnimationStyle = popWindow.mAnimationStyle;
            this.mItem1Str = popWindow.mItem1Str;
            this.mItem2Str = popWindow.mItem2Str;
            this.mItem3Str = popWindow.mItem3Str;
            this.mItem4Str = popWindow.mItem4Str;
            this.mItem5Str = popWindow.mItem5Str;
            this.mItem6Str = popWindow.mItem6Str;
            this.mDesStr = popWindow.mDesStr;
            this.mBottomStr = popWindow.mBottomStr;
            this.mItem1Color = popWindow.mItem1Color;
            this.mItem2Color = popWindow.mItem2Color;
            this.mItem3Color = popWindow.mItem3Color;
            this.mItem4Color = popWindow.mItem4Color;
            this.mItem5Color = popWindow.mItem5Color;
            this.mItem6Color = popWindow.mItem6Color;
            this.mItemDesColor = popWindow.mItemDesColor;
            this.mItemBottomColor = popWindow.mItemBottomColor;

            this.mActionPopupWindowItem1ClickListener = popWindow.mActionPopupWindowItem1ClickListener;
            this.mActionPopupWindowItem2ClickListener = popWindow.mActionPopupWindowItem2ClickListener;
            this.mActionPopupWindowItem3ClickListener = popWindow.mActionPopupWindowItem3ClickListener;
            this.mActionPopupWindowItem4ClickListener = popWindow.mActionPopupWindowItem4ClickListener;
            this.mActionPopupWindowItem5ClickListener = popWindow.mActionPopupWindowItem5ClickListener;
            this.mActionPopupWindowItem6ClickListener = popWindow.mActionPopupWindowItem6ClickListener;
            this.mActionPopupWindowDesClickListener = popWindow.mActionPopupWindowDesClickListener;
            this.mActionPopupWindowBottomClickListener = popWindow.mActionPopupWindowBottomClickListener;
            this.mActionPopupWindowDismissListener = popWindow.mActionPopupWindowDismissListener;
        }
        public ActivePopWindow.Builder with(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public ActivePopWindow.Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public ActivePopWindow.Builder item1Str(String item1Str) {
            this.mItem1Str = item1Str;
            return this;
        }

        public ActivePopWindow.Builder item2Str(String item2Str) {
            this.mItem2Str = item2Str;
            return this;
        }

        public ActivePopWindow.Builder item3Str(String item3Str) {
            this.mItem3Str = item3Str;
            return this;
        }

        public ActivePopWindow.Builder item4Str(String item4Str) {
            this.mItem4Str = item4Str;
            return this;
        }

        public ActivePopWindow.Builder item5Str(String item5Str) {
            this.mItem5Str = item5Str;
            return this;
        }

        public ActivePopWindow.Builder item6Str(String item6Str) {
            this.mItem6Str = item6Str;
            return this;
        }

        public ActivePopWindow.Builder desStr(String desStr) {
            this.mDesStr = desStr;
            return this;
        }

        public ActivePopWindow.Builder bottomStr(String bottomStr) {
            this.mBottomStr = bottomStr;
            return this;
        }

        public ActivePopWindow.Builder item1Color(int color) {
            this.mItem1Color = color;
            return this;
        }

        public ActivePopWindow.Builder animationStyle(int animationStyle) {
            this.mAnimationStyle = animationStyle;
            return this;
        }

        public ActivePopWindow.Builder item2Color(int color) {
            this.mItem2Color = color;
            return this;
        }

        public ActivePopWindow.Builder item3Color(int color) {
            this.mItem3Color = color;
            return this;
        }

        public ActivePopWindow.Builder item4Color(int color) {
            this.mItem4Color = color;
            return this;
        }

        public ActivePopWindow.Builder item5Color(int color) {
            this.mItem5Color = color;
            return this;
        }

        public ActivePopWindow.Builder item6Color(int color) {
            this.mItem6Color = color;
            return this;
        }

        public ActivePopWindow.Builder itemDesColor(int color) {
            this.mItemDesColor = color;
            return this;
        }

        public ActivePopWindow.Builder item1ClickListener(ActionPopupWindowItem1ClickListener actionPopupWindowItem1ClickListener) {
            this.mActionPopupWindowItem1ClickListener = actionPopupWindowItem1ClickListener;
            return this;
        }

        public ActivePopWindow.Builder item2ClickListener(ActionPopupWindowItem2ClickListener actionPopupWindowItem2ClickListener) {
            this.mActionPopupWindowItem2ClickListener = actionPopupWindowItem2ClickListener;
            return this;
        }

        public ActivePopWindow.Builder item3ClickListener(ActionPopupWindowItem3ClickListener actionPopupWindowItem3ClickListener) {
            this.mActionPopupWindowItem3ClickListener = actionPopupWindowItem3ClickListener;
            return this;
        }

        public ActivePopWindow.Builder item4ClickListener(ActionPopupWindowItem4ClickListener actionPopupWindowItem4ClickListener) {
            this.mActionPopupWindowItem4ClickListener = actionPopupWindowItem4ClickListener;
            return this;
        }

        public ActivePopWindow.Builder item5ClickListener(ActionPopupWindowItem5ClickListener actionPopupWindowItem5ClickListener) {
            this.mActionPopupWindowItem5ClickListener = actionPopupWindowItem5ClickListener;
            return this;
        }

        public ActivePopWindow.Builder item6ClickListener(ActionPopupWindowItem6ClickListener actionPopupWindowItem6ClickListener) {
            this.mActionPopupWindowItem6ClickListener = actionPopupWindowItem6ClickListener;
            return this;
        }

        public ActivePopWindow.Builder dismissListener(ActionPopupWindowShowOrDismissListener actionPopupWindowDismissListener) {
            this.mActionPopupWindowDismissListener = actionPopupWindowDismissListener;
            return this;
        }

        public ActivePopWindow.Builder desClickListener(ActionPopupWindowDesClickListener actionPopupWindowDesClickListener) {
            this.mActionPopupWindowDesClickListener = actionPopupWindowDesClickListener;
            return this;
        }

        public ActivePopWindow.Builder bottomClickListener(ActivePopWindow.ActionPopupWindowBottomClickListener
                                                                     actionPopupWindowBottomClickListener) {
            this.mActionPopupWindowBottomClickListener = actionPopupWindowBottomClickListener;
            return this;
        }

        public ActivePopWindow.Builder isOutsideTouch(boolean isOutsideTouch) {
            this.mIsOutsideTouch = isOutsideTouch;
            return this;
        }

        public ActivePopWindow.Builder isFocus(boolean isFocus) {
            this.mIsFocus = isFocus;
            return this;
        }

        public ActivePopWindow.Builder backgroundAlpha(float alpha) {
            this.mAlpha = alpha;
            return this;
        }

        public ActivePopWindow build() {
            return new ActivePopWindow(this);
        }
    }

    public interface ItemClickListener {
        void onItemClicked();
    }

    public interface ActionPopupWindowItem1ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowItem2ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowItem3ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowItem4ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowItem5ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowItem6ClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowDesClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowBottomClickListener extends ItemClickListener {
    }

    public interface ActionPopupWindowShowOrDismissListener {
        void onDismiss();

        void onShow();
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}
