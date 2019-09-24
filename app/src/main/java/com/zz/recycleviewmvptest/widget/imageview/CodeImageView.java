package com.zz.recycleviewmvptest.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author: wuyangyi
 * date: 2019-09-23
 * 图形验证码
 */
public class CodeImageView extends View {
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private int DEFAULT_CODE_LENGTH;//验证码的长度  这里是4位
    private int DEFAULT_FONT_SIZE;//字体大小
    private int DEFAULT_LINE_NUMBER;//多少条干扰线
    private int DEFAULT_COLOR;//默认背景颜色值

    private static final int BASE_PADDING_LEFT = 20; //左边距
    private static final int RANGE_PADDING_LEFT = 35;//左边距范围值
    private static final int BASE_PADDING_TOP = 42;//上边距
    private static final int RANGE_PADDING_TOP = 15;//上边距范围值
    private int DEFAULT_WIDTH = 200;//默认宽度.图片的总宽
    private int DEFAULT_HEIGHT = 100;//默认高度.图片的总高

    private int mPaddingLeft, mPaddingTop;

    private String mCode; //验证码值

    private StringBuilder mBuilder = new StringBuilder();
    private Random mRandom = new Random();

    private Paint paint;

    private List<LineStyleBean> lineStyleBeans; //干扰性样式
    private List<TextStyleBean> textStyleBeans; //文字样式

    public CodeImageView(Context context) {
        this(context, null);
    }

    public CodeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
        initView();
    }

    private void initData(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CodeImageView);
        DEFAULT_CODE_LENGTH = array.getInteger(R.styleable.CodeImageView_codeLength, 4);
        DEFAULT_FONT_SIZE = (int) array.getDimension(R.styleable.CodeImageView_mTextSize, Utils.sp2px(context, 18));
        DEFAULT_LINE_NUMBER = array.getInteger(R.styleable.CodeImageView_lineNumber, 3);
        DEFAULT_COLOR = array.getColor(R.styleable.CodeImageView_bgColor, getResources().getColor(R.color.image_code_bg_color));
        DEFAULT_WIDTH = (int) array.getDimension(R.styleable.CodeImageView_width, 200);
        DEFAULT_HEIGHT =  (int) array.getDimension(R.styleable.CodeImageView_height, 100);
        array.recycle();
    }

    private void initView() {
        init();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resume();
            }
        });
    }

    //重绘
    public void resume() {
        init();
        invalidate();
    }

    private void init() {
        if (textStyleBeans == null) {
            textStyleBeans = new ArrayList<>();
        }
        if (lineStyleBeans == null) {
            lineStyleBeans = new ArrayList<>();
        }
        textStyleBeans.clear();
        lineStyleBeans.clear();
        mPaddingLeft = 0;
        mPaddingTop = 0;
        //生成的验证码
        mCode = createCode();
        paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);
        for (int i = 0; i < mCode.length(); i++) {
            randomTextStyle();
        }
        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            LineStyleBean lineStyleBean = new LineStyleBean();
            lineStyleBean.setColor(randomColor());
            lineStyleBean.setStartX(mRandom.nextInt(DEFAULT_WIDTH));
            lineStyleBean.setStartY(mRandom.nextInt(DEFAULT_HEIGHT));
            lineStyleBean.setEndX(mRandom.nextInt(DEFAULT_WIDTH));
            lineStyleBean.setEndY(mRandom.nextInt(DEFAULT_HEIGHT));
            lineStyleBeans.add(lineStyleBean);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        for (int i = 0; i < mCode.length(); i++) {
            drawTextStyle(paint, textStyleBeans.get(i));
            canvas.drawText(mCode.charAt(i) + "" , textStyleBeans.get(i).getPaddingLeft(), textStyleBeans.get(i).getPaddingTop(), paint);
        }
        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            drawLine(canvas, paint, lineStyleBeans.get(i));
        }

        canvas.save();//保存
        canvas.restore();
    }

    //生成验证码
    public String createCode() {
        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容

        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
            mBuilder.append(CHARS[mRandom.nextInt(CHARS.length)]);
        }
        return mBuilder.toString();
    }

    //生成干扰线
    private void drawLine(Canvas canvas, Paint paint, LineStyleBean lineStyleBean) {
        paint.setStrokeWidth(1);
        paint.setColor(lineStyleBean.getColor());
        canvas.drawLine(lineStyleBean.getStartX(), lineStyleBean.getStartY(), lineStyleBean.getEndX(), lineStyleBean.getEndY(), paint);
    }

    //随机颜色
    private int randomColor() {
        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容

        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(mRandom.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }

            mBuilder.append(haxString);
        }

        return Color.parseColor("#" + mBuilder.toString());
    }

    //随机文本样式
    private void drawTextStyle(Paint paint, TextStyleBean textStyleBean) {
        paint.setColor(textStyleBean.getColor());
        paint.setFakeBoldText(textStyleBean.isBold());  //true为粗体，false为非粗体
        paint.setTextSkewX(textStyleBean.getSkewX()); //float类型参数，负数表示右斜，整数左斜
//        paint.setUnderlineText(true); //true为下划线，false为非下划线
//        paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    //随机间距
    private void randomTextStyle() {
        TextStyleBean textStyleBean = new TextStyleBean();
        mPaddingLeft += BASE_PADDING_LEFT + mRandom.nextInt(RANGE_PADDING_LEFT);
        mPaddingTop = BASE_PADDING_TOP + mRandom.nextInt(RANGE_PADDING_TOP);
        textStyleBean.setPaddingLeft(mPaddingLeft);
        textStyleBean.setPaddingTop(mPaddingTop);
        textStyleBean.setColor(randomColor());
        textStyleBean.setBold(mRandom.nextBoolean());
        float skewX = mRandom.nextInt(11) / 10;
        skewX = mRandom.nextBoolean() ? skewX : -skewX;
        textStyleBean.setSkewX(skewX);
        textStyleBeans.add(textStyleBean);
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public class LineStyleBean {
        private int startX;
        private int startY;
        private int endX;
        private int endY;
        private int color;

        public int getStartX() {
            return startX;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public int getStartY() {
            return startY;
        }

        public void setStartY(int startY) {
            this.startY = startY;
        }

        public int getEndX() {
            return endX;
        }

        public void setEndX(int endX) {
            this.endX = endX;
        }

        public int getEndY() {
            return endY;
        }

        public void setEndY(int endY) {
            this.endY = endY;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    public class TextStyleBean {
        private int color; //颜色
        private boolean isBold; //是否加粗
        private float skewX; //斜体方向 float类型参数，负数表示右斜，整数左斜
        private int paddingLeft;
        private int paddingTop;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isBold() {
            return isBold;
        }

        public void setBold(boolean bold) {
            isBold = bold;
        }

        public float getSkewX() {
            return skewX;
        }

        public void setSkewX(float skewX) {
            this.skewX = skewX;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
        }

        public int getPaddingTop() {
            return paddingTop;
        }

        public void setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
        }
    }
}
