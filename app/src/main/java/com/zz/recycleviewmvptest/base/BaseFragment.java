package com.zz.recycleviewmvptest.base;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.DeviceUtils;
import com.zz.recycleviewmvptest.widget.StatusBarUtils;

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
     * 当侵入状态栏时， 状态栏的占位控件
     */
    protected View mStatusPlaceholderView;

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_RECORD_AUDIO = 103;

    private AlertDialog mAlertDialog;

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
        // 是否添加和状态栏等高的占位 View
        if (setUseSatusbar() && setUseStatusView()) {
            mStatusPlaceholderView = new View(getContext());
            mStatusPlaceholderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getStatuBarHeight(getContext(), getActivity())));
            if (StatusBarUtils.intgetType(getActivity().getWindow()) == 0 && ContextCompat.getColor(getContext(), setToolBarBackgroud()) == Color
                    .WHITE) {
                mStatusPlaceholderView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.home_bottom));
            } else {
                mStatusPlaceholderView.setBackgroundColor(ContextCompat.getColor(getContext(), setToolBarBackgroud()));
            }
            linearLayout.addView(mStatusPlaceholderView);
        }
        // 在需要显示toolbar时，进行添加
        if (showToolbar()) {
            View toolBarContainer = mLayoutInflater.inflate(getToolBarLayoutId(), null);
            initDefaultToolBar(toolBarContainer);
            linearLayout.addView(toolBarContainer);
        }
        if (setUseSatusbar()) {
            // 状态栏顶上去
            StatusBarUtils.transparencyBar(getActivity());
            linearLayout.setFitsSystemWindows(false);
        } else {
            // 状态栏不顶上去
            StatusBarUtils.setStatusBarColor(getActivity(), setToolBarBackgroud());
            linearLayout.setFitsSystemWindows(true);
        }
        // 是否设置状态栏文字图标灰色，对 小米、魅族、Android 6.0 及以上系统有效
        if (setStatusbarGrey()) {
            StatusBarUtils.statusBarLightMode(getActivity());
        } else {
            StatusBarUtils.statusBarLightModeWhile(getActivity());
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
            mIbRightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShakeUtils.isInvalidClick(v)) {
                        return;
                    }
                    setRightImageClick();
                }
            });
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
     * 设置右边图片点击事件
     */
    protected void setRightImageClick() {

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

    /**
     * 状态栏是否可用
     *
     * @return 默认不可用
     */
    protected boolean setUseSatusbar() {
        return false; //Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    /**
     * 设置是否需要添加和状态栏等高的占位 view
     *
     * @return
     */
    protected boolean setUseStatusView() {
        return true;
    }

    /**
     * @return 状态栏 背景
     */
    protected int setToolBarBackgroud() {
        return R.color.home_bottom;
    }

    /**
     * 状态栏字体默认为白色
     * 支持小米、魅族以及 6.0 以上机型
     *
     * @return
     */
    protected boolean setStatusbarGrey() {
        return false;
    }



    /**
     * 申请权限
     */
    protected void requestPermission(final String[] permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission[0])) {
            showAlertDialog("提示", rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    permission, requestCode);
                        }
                    }, "确定", null, "取消");
        } else {
            ActivityCompat.requestPermissions(getActivity(), permission, requestCode);
        }
    }

    /**
     * 再次确定
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }

    //使用动画跳转
    protected void useAnimationIntent() {
        if (getActivity() instanceof AnimationClick) {
            ((AnimationClick)getActivity()).animation();
        }
    }

}
