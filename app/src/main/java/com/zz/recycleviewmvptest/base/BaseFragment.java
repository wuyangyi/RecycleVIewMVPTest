package com.zz.recycleviewmvptest.base;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

/**
 * Fragment的基类
 * @param <P>
 */
public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements BaseView<P>  {
    protected View mRootView;
    protected Activity mActivity;
    protected P mPresenter;
    protected LayoutInflater mLayoutInflater;
    protected Context context;
    protected ImageButton mIbLeftImage; //返回按钮
    protected TextView mTvCenterTitle; //中间标题
    protected ImageButton mIbRightImage; //右边的图片按钮
    protected LinearLayout mLlTitle; //标题栏布局

    /**
     * 加载
     */
    protected View mCenterLoadingView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        mLayoutInflater = inflater;
        mRootView = getContentView();
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 如果要使用 eventbus 请将此方法返回 true
        if (useEventBus()){
            // 注册到事件主线
            EventBus.getDefault().register(this);
        }
        initData();
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 如果要使用 eventbus 请将此方法返回 true
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected View getContentView() {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 在需要显示toolbar时，进行添加
        if (showToolbar()) {
            View toolBarContainer = mLayoutInflater.inflate(getToolBarLayoutId(), null);
            initDefaultToolBar(toolBarContainer);
            linearLayout.addView(toolBarContainer);
        }
        final View bodyContainer = mLayoutInflater.inflate(getBodyLayoutId(), null);
        bodyContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 加载动画
        if (setUseCenterLoading()) {
            FrameLayout frameLayout = new FrameLayout(mActivity);
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.addView(bodyContainer);

            mCenterLoadingView = mLayoutInflater.inflate(R.layout.view_center_loading, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (!showToolbar()) {
                params.setMargins(0, getstatusbarAndToolbarHeight(), 0, 0);
            }
            mCenterLoadingView.setLayoutParams(params);
            if (setUseCenterLoadingAnimation()) {
                ((AnimationDrawable) ((ImageView) mCenterLoadingView.findViewById(R.id.iv_center_load)).getDrawable()).start();
            }
            frameLayout.addView(mCenterLoadingView);
            linearLayout.addView(frameLayout);

        } else {
            linearLayout.addView(bodyContainer);
        }
        return linearLayout;
    }

    /**
     * 获取状态栏和操作栏的高度
     *
     * @return
     */
    protected int getstatusbarAndToolbarHeight() {
        return 0;
    }

    /**
     * @return 是否需要中心加载动画，对应
     */
    protected boolean setUseCenterLoadingAnimation() {
        return true;
    }

    /**
     * 是否开启中心加载动画
     * @return
     */
    protected boolean setUseCenterLoading() {
        return false;
    }

    /**
     * 关闭中心放大缩小加载动画
     */
    protected void closeLoadingView() {
        if (mCenterLoadingView == null) {
            return;
        }
        if (mCenterLoadingView.getVisibility() == View.VISIBLE) {
            ((AnimationDrawable) ((ImageView) mCenterLoadingView.findViewById(R.id.iv_center_load)).getDrawable()).stop();
            mCenterLoadingView.animate().alpha(0.3f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (mCenterLoadingView != null) {
                        mCenterLoadingView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    /**
     * 开启中心放大缩小加载动画
     */
    protected void showLoadingView() {
        if (mCenterLoadingView == null) {
            return;
        }
        if (mCenterLoadingView.getVisibility() == View.GONE) {
            mCenterLoadingView.findViewById(R.id
                    .iv_center_load).setVisibility(View.VISIBLE);
            ((AnimationDrawable) ((ImageView) mCenterLoadingView.findViewById(R.id
                    .iv_center_load)).getDrawable()).start();
            mCenterLoadingView.setAlpha(1);
            mCenterLoadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化标题栏
     * @param toolBarContainer
     */
    protected void initDefaultToolBar(View toolBarContainer) {
        mIbLeftImage = toolBarContainer.findViewById(R.id.ib_left_image);
        mTvCenterTitle = toolBarContainer.findViewById(R.id.tv_center_title);
        mIbRightImage = toolBarContainer.findViewById(R.id.ib_right_image);
        mLlTitle = toolBarContainer.findViewById(R.id.ll_title);
        if (setLeftImage() == 0) {
            mIbLeftImage.setVisibility(View.INVISIBLE);
        } else {
            mIbLeftImage.setVisibility(View.VISIBLE);
            mIbLeftImage.setImageDrawable(getResources().getDrawable(setLeftImage()));
        }
        if (setRightImage() == 0) {
            mIbRightImage.setVisibility(View.INVISIBLE);
        } else {
            mIbRightImage.setVisibility(View.VISIBLE);
            mIbRightImage.setImageDrawable(getResources().getDrawable(setRightImage()));
        }
        //标题栏背景
        mLlTitle.setBackgroundColor(getResources().getColor(setTitleBg()));
        mTvCenterTitle.setText(setCenterTitle());
        mTvCenterTitle.setTextColor(getResources().getColor(setCenterTitleColor()));
        mIbLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLeftImageClickListener();
            }
        });
    }

    protected abstract void initView(View rootView);

    protected abstract void initData();

    /**
     * 是否使用 eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 获取toolbar下方的布局文件
     */
    protected abstract int getBodyLayoutId();

    /**
     * 是否显示toolbar,默认显示
     */
    protected boolean showToolbar() {
        return true;
    }

    /**
     * 设置中间标题
     * @return
     */
    protected String setCenterTitle() {
        return "";
    }

    protected void setCenterTitle(String title) {
        mTvCenterTitle.setText(title);
    }

    /**
     * 设置左边图片
     * @return
     */
    protected int setLeftImage() {
        return R.mipmap.ic_go_back_white;
    }

    /**
     * 标题栏的背景色
     * @return
     */
    protected int setTitleBg() {
        return R.color.home_bottom;
    }

    protected int setCenterTitleColor() {
        return R.color.white;
    }

    /**
     * 设置右边图片
     * @return
     */
    protected int setRightImage() {
        return 0;
    }

    /**
     * 获取toolbar的布局文件,如果需要返回自定义的toolbar布局，重写该方法；否则默认返回缺省的toolbar
     */
    protected int getToolBarLayoutId() {
        return R.layout.fragment_title;
    }

    /**
     * 左边图片的点击事件
     */
    protected void setLeftImageClickListener() {
        mActivity.finish();
    }

}
