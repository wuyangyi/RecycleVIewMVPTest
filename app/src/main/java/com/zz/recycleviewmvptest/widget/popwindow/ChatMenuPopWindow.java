package com.zz.recycleviewmvptest.widget.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.DeviceUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.viewgroup.MenuGroupView;

/**
 * author: wuyangyi
 * date: 2019-09-25
 * 聊天菜单
 */
public class ChatMenuPopWindow extends PopupWindow {
    protected Activity mActivity;
    protected View mParentView;
    protected View mContentView;

    protected boolean mIsOutsideTouch; //点击外围隐藏
    protected boolean mIsFocus;
    protected float mAlpha;
    protected int mAnimationStyle; //动画
    protected CommonAdapter adapter;
    protected int gravity = Gravity.CENTER; //对其方式
    protected int rightMargin = 0; //右边rightMargin
    protected int leftMargin = 0; //左边leftMargin
    protected int topMargin = 0; //上边topMargin
    protected int bottomMargin = 0; //下边bottomMargin
    protected RecyclerView.LayoutManager layoutManager;

    private MenuGroupView mGvBg;
    private Drawable mBackgroundDrawable = new ColorDrawable(0x00000000);// 默认为透明;
    public ChatMenuPopWindow(Builder builder) {
        this.mActivity = builder.mActivity;
        this.mParentView = builder.parentView;
        this.mIsOutsideTouch = builder.mIsOutsideTouch;
        this.mIsFocus = builder.mIsFocus;
        this.mAlpha = builder.mAlpha;
        this.adapter = builder.adapter;
        this.gravity = builder.gravity;
        this.rightMargin = builder.rightMargin;
        this.leftMargin = builder.leftMargin;
        this.layoutManager = builder.layoutManager;
        this.topMargin = builder.topMargin;
        this.bottomMargin = builder.bottomMargin;
        initView();

    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static ChatMenuPopWindow.Builder builder() {
        return new ChatMenuPopWindow.Builder();
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = gravity;
        mContentView.setLayoutParams(layoutParams);

        RecyclerView recyclerView = mContentView.findViewById(R.id.rvMenuList);
        if (adapter != null) {
            if (layoutManager == null) {
                layoutManager = new LinearLayoutManager(mActivity);
                ((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });
        mGvBg = mContentView.findViewById(R.id.mGvBg);
        mGvBg.setGravity(gravity);
        mGvBg.setPadding(0, topMargin, 0, bottomMargin);
    }

    protected int getLayoutId() {
        return gravity == Gravity.RIGHT ? R.layout.popwindow_menu : R.layout.popwindow_chat_menu;
    }

    /**
     * 显示根据gravity来显示
     * 三角形方向用y来判断，y小于屏幕高度的一半则三角形向上
     */
    public void show(int x, int y, boolean isBottom, boolean isMe, String type) {
        setWindowAlpha(mAlpha);
        boolean isShort;
        int width = Utils.getScreenWidth(mActivity);
        int menuWidth = (int) (mActivity.getResources().getDimension(R.dimen.chat_menu_width) * DataUtils.getChatMenuData(type).size());
        if (isMe) {
            isShort = (width - x) < menuWidth / 2;
        } else {
            isShort = x < menuWidth / 2;
        }
        mContentView.setPadding(isShort && !isMe ? 0 : leftMargin - menuWidth / 2 , 0, isShort && isMe ? 0 : rightMargin - menuWidth / 2, 0);
        int right = 0;
        if (isMe) {
            if (isShort) {
                right = width - x;
            } else {
                right = menuWidth / 2;
            }
        } else {
            if (isShort) {
                right = menuWidth - x;
            }else {
                right = menuWidth / 2;
            }
        }
        mGvBg.setTriangleRightDistance(right, !isBottom);
        if (mParentView == null) {
            showAtLocation(mContentView, isBottom ? Gravity.BOTTOM : Gravity.TOP, 0, y);
        } else {
            showAtLocation(mParentView, isBottom ? Gravity.BOTTOM : Gravity.TOP, 0, y);
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
        protected int gravity;
        protected int rightMargin = 0; //右边rightMargin
        protected int leftMargin = 0; //左边leftMargin
        protected int topMargin = 0; //上边topMargin
        protected int bottomMargin = 0; //下边bottomMargin
        protected RecyclerView.LayoutManager layoutManager;

        protected Builder() {}

        protected Builder(ChatMenuPopWindow popWindow) {
            this.mActivity = popWindow.mActivity;
            this.parentView = popWindow.mParentView;
            this.mAlpha = popWindow.mAlpha;
            this.mIsFocus = popWindow.mIsFocus;
            this.mIsOutsideTouch = popWindow.mIsOutsideTouch;
            this.mAnimationStyle = popWindow.mAnimationStyle;
            this.adapter = popWindow.adapter;
            this.gravity = popWindow.gravity;
            this.rightMargin = popWindow.rightMargin;
            this.leftMargin = popWindow.leftMargin;
            this.layoutManager = popWindow.layoutManager;
            this.topMargin = popWindow.topMargin;
            this.bottomMargin = popWindow.bottomMargin;
        }

        public ChatMenuPopWindow.Builder with(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public ChatMenuPopWindow.Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public ChatMenuPopWindow.Builder animationStyle(int animationStyle) {
            this.mAnimationStyle = animationStyle;
            return this;
        }

        public ChatMenuPopWindow.Builder isOutsideTouch(boolean isOutsideTouch) {
            this.mIsOutsideTouch = isOutsideTouch;
            return this;
        }

        public ChatMenuPopWindow.Builder isFocus(boolean isFocus) {
            this.mIsFocus = isFocus;
            return this;
        }

        public ChatMenuPopWindow.Builder backgroundAlpha(float alpha) {
            this.mAlpha = alpha;
            return this;
        }

        public ChatMenuPopWindow.Builder adater(CommonAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public ChatMenuPopWindow.Builder layoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public ChatMenuPopWindow.Builder gravity(int  gravity) {
            this.gravity = gravity;
            return this;
        }

        public ChatMenuPopWindow.Builder rightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

        public ChatMenuPopWindow.Builder leftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public ChatMenuPopWindow.Builder topMargin(int topMargin) {
            this.topMargin = topMargin;
            return this;
        }

        public ChatMenuPopWindow.Builder bottomMargin(int bottomMargin) {
            this.bottomMargin = bottomMargin;
            return this;
        }

        public ChatMenuPopWindow build() {
            return new ChatMenuPopWindow(this);
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}
