package com.zz.recycleviewmvptest.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;

import org.simple.eventbus.EventBus;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=activity;
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
        linearLayout.addView(bodyContainer);
        return linearLayout;
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

    /**
     * 设置左边图片
     * @return
     */
    protected int setLeftImage() {
        return R.mipmap.ic_go_back;
    }

    /**
     * 标题栏的背景色
     * @return
     */
    protected int setTitleBg() {
        return R.color.white;
    }

    protected int setCenterTitleColor() {
        return R.color.title_color;
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

}
