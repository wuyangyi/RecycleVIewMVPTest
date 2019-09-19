package com.zz.recycleviewmvptest.widget.viewpage_indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import java.util.ArrayList;
import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-19
 * ViewPage指示器封装
 */
public class TabIndicator extends FrameLayout {

    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager; //传进来的ViewPage
    private View divider; //底部分割线
    private List<String> mStringList;// tab列表的文字
    private Context mContext;
    private CommonNavigator mCommonNavigator;
    private boolean mIsAdjustMode; //ture 即标题平分屏幕宽度的模式
    private int mLinePagerIndicator = LinePagerIndicator.MODE_WRAP_CONTENT; //指示器自适应文字宽度
    private int mLineColor; //指示器颜色
    private int mLineHeight; //指示器高度
    private int mSelectTextColor; //选中文字颜色
    private int mNoSelectTextColor; //未选中文字颜色
    private int mTextSize; //未选中文字大小
    private int mSelectTextSize; //选中文字大小
    private boolean isShowDriver; //是否显示底部分割线
    private int mTextPadding; //文字左右padding
    private int mIndicatorPadding; //指示器左右padding

    public TabIndicator(@NonNull Context context) {
        this(context, null);
    }

    public TabIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
        initView(context);
    }

    //获取自定义属性的值
    @SuppressLint("ResourceAsColor")
    private void initData(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabIndicator);
        try {
            mLineColor = array.getColor(R.styleable.TabIndicator_lineColor, getResources().getColor(R.color.home_bottom));
            mSelectTextColor = array.getColor(R.styleable.TabIndicator_selectTextColor, getResources().getColor(R.color.important_for_content));
            mNoSelectTextColor = array.getColor(R.styleable.TabIndicator_noSelectTextColor, getResources().getColor(R.color.normal_for_assist_text));
            mLineHeight = array.getDimensionPixelOffset(R.styleable.TabIndicator_lineHeight, getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.line_height));
            isShowDriver = array.getBoolean(R.styleable.TabIndicator_isShowDriver, true);
            mIsAdjustMode = array.getBoolean(R.styleable.TabIndicator_isAdjustMode, false);
            mTextSize = (int) array.getDimension(R.styleable.TabIndicator_textSize, Utils.sp2px(context, 15));
            mSelectTextSize = (int) array.getDimension(R.styleable.TabIndicator_selectTextSize, Utils.sp2px(context, 15));
            mTextPadding = (int) array.getDimension(R.styleable.TabIndicator_textLRPadding, 15);
            mIndicatorPadding = (int) array.getDimension(R.styleable.TabIndicator_indicatorPadding, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    /**
     * 初始化
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tab_select_view, this);
        mMagicIndicator = (MagicIndicator) findViewById(R.id.mg_indicator);
        divider = findViewById(R.id.divider);
        mContext = context;
        divider.setVisibility(isShowDriver ? VISIBLE : GONE);
    }

    public void initTabView(ViewPager viewPager, List<String> stringList) {
        this.mViewPager = viewPager;
        this.mStringList = stringList;
        if (mStringList == null) {
            mStringList = new ArrayList<>();
        }
        initMagicIndicator();
    }

    //需要自定义CommonNavigatorAdapter
    public void initTabView(ViewPager viewPager, List<String> stringList, CommonNavigatorAdapter adapter) {
        this.mViewPager = viewPager;
        this.mStringList = stringList;
        if (mStringList == null) {
            mStringList = new ArrayList<>();
        }
        initMagicIndicator(adapter);
    }

    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundColor(getResources().getColor(R.color.transparency));
        mCommonNavigator = new CommonNavigator(mContext);
        mCommonNavigator.setAdjustMode(mIsAdjustMode);

        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {


            @Override
            public int getCount() {
                return mStringList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(mNoSelectTextColor);
                simplePagerTitleView.setPadding(mTextPadding, 0, mTextPadding, 0);
                simplePagerTitleView.setSelectedColor(mSelectTextColor);
                simplePagerTitleView.setText(mStringList.get(index));
                if (mViewPager.getCurrentItem() == index) {
                    simplePagerTitleView.setTextSize(Utils.px2sp(mContext, mSelectTextSize));
                } else {
                    simplePagerTitleView.setTextSize(Utils.px2sp(mContext, mTextSize));
                }

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(mLinePagerIndicator);// 适应文字长度
                try {
                    linePagerIndicator.setXOffset(mIndicatorPadding);// 每个item边缘到指示器的边缘距离
                    linePagerIndicator.setLineHeight(mLineHeight);
                } catch (Exception ignored) {
                }
                linePagerIndicator.setColors(mLineColor);
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private void initMagicIndicator(CommonNavigatorAdapter adapter) {
        mMagicIndicator.setBackgroundColor(getResources().getColor(R.color.transparency));
        mCommonNavigator = new CommonNavigator(mContext);
        mCommonNavigator.setAdjustMode(mIsAdjustMode);
        mCommonNavigator.setAdapter(adapter);
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }
}
