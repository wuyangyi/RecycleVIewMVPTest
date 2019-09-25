package com.zz.recycleviewmvptest.widget.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.Utils;

/**
 * author: wuyangyi
 * date: 2019-09-25
 */
public class MenuGroupView extends LinearLayout {
    private Paint mPaint = null;
    private int triangleHeight; //三角形高度
    private int triangleRightDistance; //三角形右边距离
    private int triangleWidth;

    public MenuGroupView(Context context) {
        this(context, null);
    }

    public MenuGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuGroupView);
        try {
            triangleHeight = array.getDimensionPixelOffset(R.styleable.MenuGroupView_triangle_height, Utils.dp2px(context, 8));
            triangleRightDistance = array.getDimensionPixelOffset(R.styleable.MenuGroupView_triangle_right_distance, Utils.dp2px(context, 15));
            triangleWidth = array.getDimensionPixelOffset(R.styleable.MenuGroupView_triangle_width, Utils.dp2px(context, 12));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
            init();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.menu_color));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, triangleHeight, getWidth(), getHeight()), 6, 6, mPaint);
        Path path = new Path();
        path.moveTo(getWidth() - triangleRightDistance - triangleWidth, triangleHeight);
        path.lineTo(getWidth() - triangleRightDistance, triangleHeight);
        path.lineTo(getWidth() - triangleRightDistance - triangleWidth / 2, 0);
        path.close();
        canvas.drawPath(path, mPaint);
        super.dispatchDraw(canvas);
    }

}
