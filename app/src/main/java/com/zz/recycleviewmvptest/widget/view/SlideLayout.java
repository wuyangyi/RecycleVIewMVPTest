package com.zz.recycleviewmvptest.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideLayout extends FrameLayout {
    private View mMenuView;
    private int mMenuWidth;
    private int mMenuHeight;
    private int mContentWidth;
    private Scroller mScroller;
    private float startX;

    private float downX;
    private float downY;

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContentWidth = getMeasuredWidth();
        mMenuWidth = mMenuView.getMeasuredWidth();
        mMenuHeight = mMenuView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //将menu布局到右侧不可见
        mMenuView.layout(mContentWidth, 0, mContentWidth + mMenuWidth, mMenuHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                final float dx = (int) (x - startX);
                int disX = (int) (getScrollX() - dx);
                if (disX <= 0) {
                    disX = 0;
                }
                scrollTo(Math.min(disX, mMenuWidth), getScrollY());
                final float moveX = Math.abs(x - downX);
                final float moveY = Math.abs(y - downY);
                if (moveX > moveY && moveX > 10f) {
                    //父布局不要拦截子view的touch事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                startX = x;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < mMenuWidth / 2) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
        }
        return true;
    }

    //拦截事件不传递给子view
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                if (mOnSlideChangeListener != null) {
                    mOnSlideChangeListener.onClick(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float moveX = Math.abs(x - downX);
                if (moveX > 10f) {                    //对touch事件进行拦截
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //当动画执行完成以后，执行新的动画
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private OnSlideChangeListener mOnSlideChangeListener;

    public interface OnSlideChangeListener {
        void onMenuOpen(SlideLayout slideLayout);

        void onMenuClose(SlideLayout slideLayout);

        void onClick(SlideLayout slideLayout);
    }

    public void setOnSlideChangeListener(OnSlideChangeListener onSlideChangeListener1) {
        this.mOnSlideChangeListener = onSlideChangeListener1;
    }

    public final void openMenu() {
        mScroller.startScroll(getScrollX(), getScrollY(), mMenuWidth - getScrollX(), 0);
        invalidate();
        if (mOnSlideChangeListener != null) {
            mOnSlideChangeListener.onMenuOpen(this);
        }
    }

    public final void closeMenu() {
        mScroller.startScroll(getScrollX(), getScrollY(), 0 - getScrollX(), 0);
        invalidate();
        if (mOnSlideChangeListener != null) {
            mOnSlideChangeListener.onMenuClose(this);
        }
    }
}