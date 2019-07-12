package com.zz.recycleviewmvptest.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;

/**
 * 圆形（圆角）图片
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint; //画笔
    private int mRadius; //圆形图片的半径
    private float mScale;//图片放缩比例

    private int type; //图片类型

    private int radius; //圆角矩形的圆角

    public CircleImageView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            type = array.getInteger(R.styleable.CircleImageView_type, 0);
            radius = array.getInteger(R.styleable.CircleImageView_radius, 0);
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == 1) {
            //保证图片的宽高一致，去最小值
            int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = size / 2;
            setMeasuredDimension(size, size); //设置图片大小
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (type == 0) {
            super.onDraw(canvas);
            return;
        }

        if (getDrawable() == null) {
            return;
        }
        mPaint = new Paint();
        if (type == 1) {
            Bitmap bitmap = drawableToBitmap(getDrawable());

            //初始化BitmapShader，传入Bitmap对象
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            //计算放缩比例
            mScale = (mRadius * 2.0f) / Math.min(bitmap.getWidth(), bitmap.getHeight());

            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);

            mPaint.setShader(bitmapShader);

            //画圆形，指定好中心点坐标，半径，画笔
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else if (type == 2) {
            Bitmap b = getRoundBitmap(getDrawable(), radius);
            //初始化BitmapShader，传入Bitmap对象
            BitmapShader bitmapShader = new BitmapShader(b, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);
            mPaint.setShader(bitmapShader);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            mPaint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, mPaint);
        }
    }

    /**
     * drawable转BitMap --圆形图片
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取圆角矩形图片方法
     * @param drawable
     * @param roundPx,一般设置成14
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getRoundBitmap(Drawable drawable, int roundPx) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, roundPx, roundPx, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, mPaint);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        drawable.draw(canvas);
        return output;
    }
}
