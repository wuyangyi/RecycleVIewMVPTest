package com.zz.recycleviewmvptest.widget.seekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.zz.recycleviewmvptest.R;

/**
 * author: wuyangyi
 * date: 2019-09-04
 * 自定义进度条
 */
public class RectangleRadioSeekBar extends View {

    private Paint mPaint = null;
    private int mProWidth; //进度条宽度
    private int mProHeight; //高度
    private int mProColor;  //颜色
    private int mProBgColor = getResources().getColor(R.color.seek_bar_color_floor); //进度条底色
    private int mProgressNow; //进度条当前进度比例
    private int mProgressMax; //最大进度值
    private boolean mNeedCircle; //是否需要圆
    private String mCenterText; //中间文字
    private String mEndText; //末尾文字
    private float mTextSize; //文字大小
    private float mTextToSeekbarHeight; //文字距离toolbar距离
    private int mTextColor = getResources().getColor(R.color.white);//文字颜色，默认白色

    private int mTextPadding = 10; //文字的padding

    public RectangleRadioSeekBar(Context context) {
        super(context);
        initData(context, null, 0);
    }

    public RectangleRadioSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
    }

    public RectangleRadioSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
    }

    public int getProColor() {
        return mProColor;
    }

    public void setProColor(int mProColor) {
        this.mProColor = mProColor;
    }

    public int getProWidth() {
        return mProWidth;
    }

    public void setProWidth(int mProWidth) {
        this.mProWidth = mProWidth;
    }

    public int getProHeight() {
        return mProHeight;
    }

    public void setProHeight(int mProHeight) {
        this.mProHeight = mProHeight;
    }

    public int getProgressMax() {
        return mProgressMax;
    }

    public void setProgressMax(int mProgressMax) {
        this.mProgressMax = mProgressMax;
    }

    public int getProgressNow() {
        return mProgressNow;
    }

    public void setProgressNow(int mProgressNow) {
        this.mProgressNow = mProgressNow;
    }

    //获取自定义属性的值
    @SuppressLint("ResourceAsColor")
    private void initData(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RectangleRadioSeekBar, defStyleAttr, 0);
        try {
            int count = array.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.RectangleRadioSeekBar_pro_color:
                        mProColor = array.getColor(attr, getResources().getColor(R.color.seek_bar_color));
                        break;
                    case R.styleable.RectangleRadioSeekBar_pro_bg_color:
                        mProBgColor = array.getColor(attr, getResources().getColor(R.color.seek_bar_color_floor));
                        break;
                    case R.styleable.RectangleRadioSeekBar_pro_width:
                        mProWidth = array.getDimensionPixelOffset(R.styleable.RectangleRadioSeekBar_pro_width, getContext().getResources()
                                .getDimensionPixelOffset(R.dimen.seekBar_width));
                        break;
                    case R.styleable.RectangleRadioSeekBar_pro_height:
                        mProHeight = array.getDimensionPixelOffset(R.styleable.RectangleRadioSeekBar_pro_height, getContext().getResources()
                                .getDimensionPixelOffset(R.dimen.seekBar_height));
                        break;
                    case R.styleable.RectangleRadioSeekBar_schedule:
                        mProgressNow = array.getInteger(attr, 0);
                        break;
                    case R.styleable.RectangleRadioSeekBar_schedule_max:
                        mProgressMax = array.getInteger(attr, 100);
                        break;
                    case R.styleable.RectangleRadioSeekBar_need_text:
                        mNeedCircle = array.getBoolean(attr, false);
                        break;
                    case R.styleable.RectangleRadioSeekBar_center_text:
                        mCenterText = array.getString(attr);
                        break;
                    case R.styleable.RectangleRadioSeekBar_end_text:
                        mEndText = array.getString(attr);
                        break;
                    case R.styleable.RectangleRadioSeekBar_text_size:
                        mTextSize = array.getDimension(attr, 30);
                        break;
                    case R.styleable.RectangleRadioSeekBar_text_to_seekbar_height:
                        mTextToSeekbarHeight = array.getDimension(attr, 0);
                        break;
                    case R.styleable.RectangleRadioSeekBar_text_color:
                        mTextColor = array.getColor(attr, getResources().getColor(R.color.white));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
        init();
    }

    /**
     * 测量当前view的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mProWidth;
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mProHeight;
            if (widthMode == MeasureSpec.AT_MOST) {
                height = Math.max(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        Paint paint = new Paint();
        paint.setColor(mTextColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(mTextSize);
        int mEndTextWidth = 0; //末尾文字矩形宽度的一半
        int mCenterTextWidth = 0; //中间文字矩形宽度的一半
        int mTextHeight = 0; //文字高度
        if (mNeedCircle) {
            mEndTextWidth = (getTextWidth(paint, mEndText) + mTextPadding) / 2;
            mCenterTextWidth = (getTextWidth(paint, mCenterText) + mTextPadding) / 2;
            mTextHeight = getFontHeight(paint, mTextSize);
        }

        //绘制灰色底部进度条的轨迹
        mPaint.setColor(mProBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, mProHeight / 2, width - mEndTextWidth, mProHeight + mProHeight / 2), 10, 10, mPaint);
        if (mNeedCircle) {
            //画圆
            canvas.drawCircle((width - mEndTextWidth) / 2, mProHeight, mProHeight, mPaint);
            canvas.drawCircle(width - mProHeight/2 - mEndTextWidth, mProHeight, mProHeight, mPaint);
            //画文字背景
            canvas.drawRoundRect(new RectF((width - mEndTextWidth) / 2 - mCenterTextWidth, mProHeight*2 + mTextToSeekbarHeight, (width - mEndTextWidth) / 2 + mCenterTextWidth, mProHeight*2 + mTextToSeekbarHeight + mTextHeight + mTextPadding), 15, 15, mPaint);
            canvas.drawRoundRect(new RectF(width - mEndTextWidth*2, mProHeight*2 + mTextToSeekbarHeight, width,mProHeight*2 + mTextToSeekbarHeight + mTextHeight + mTextPadding), 15, 15, mPaint);
            //画text
            canvas.drawText(mCenterText, (width - mEndTextWidth) / 2 - getTextWidth(paint, mCenterText) / 2, mProHeight*2 + mTextToSeekbarHeight + mTextHeight - mTextPadding / 2, paint);
            canvas.drawText(mEndText, width - getTextWidth(paint, mEndText) - mTextPadding / 2, mProHeight*2 + mTextToSeekbarHeight + mTextHeight - mTextPadding / 2, paint);
        }

        //绘制进度条的进度
        mPaint.setColor(mProColor);
        canvas.drawRoundRect(new RectF(0, mProHeight / 2, (int)(mProgressNow * (width - mEndTextWidth) / mProgressMax), mProHeight + mProHeight / 2), 10, 10, mPaint);
        if (mNeedCircle) {
            if ((float) mProgressNow / (float) mProgressMax >= 0.5) {
                canvas.drawCircle((width - mEndTextWidth) / 2, mProHeight, mProHeight, mPaint);
                canvas.drawRoundRect(new RectF((width - mEndTextWidth) / 2 - mCenterTextWidth, mProHeight*2 + mTextToSeekbarHeight, (width - mEndTextWidth) / 2 + mCenterTextWidth, mProHeight*2 + mTextToSeekbarHeight + mTextHeight + mTextPadding), 15, 15, mPaint);
                canvas.drawText(mCenterText, (width - mEndTextWidth) / 2 - getTextWidth(paint, mCenterText) / 2, mProHeight*2 + mTextToSeekbarHeight + mTextHeight - mTextPadding / 2, paint);

            }
            if ((float) mProgressNow / (float) mProgressMax >= 1) {
                canvas.drawCircle(width - mProHeight/2 - mEndTextWidth, mProHeight, mProHeight, mPaint);
                canvas.drawRoundRect(new RectF(width - mEndTextWidth*2, mProHeight*2 + mTextToSeekbarHeight, width,mProHeight*2 + mTextToSeekbarHeight + mTextHeight + mTextPadding), 15, 15, mPaint);
                canvas.drawText(mEndText, width - getTextWidth(paint, mEndText) - mTextPadding / 2, mProHeight*2 + mTextToSeekbarHeight + mTextHeight - mTextPadding / 2, paint);
            }
        }
    }

    //获取宽度
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    //获取文字高度
    public static int getFontHeight(Paint paint, float fontSize) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
