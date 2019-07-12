package com.zz.recycleviewmvptest.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zz.recycleviewmvptest.R;

import org.simple.eventbus.EventBus;

public abstract class BaseActivity<P extends BasePresenter, F extends Fragment> extends AppCompatActivity {
    protected BaseApplication mApplication;
    protected P mPresenter;
    /**
     * 内容 fragment
     */
    protected F mContanierFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // 如果要使用 eventbus 请将此方法返回 true
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus())// 如果要使用 eventbus 请将此方法返回 true
        {
            EventBus.getDefault().unregister(this);
        }
    }

    public F getContanierFragment() {
        return mContanierFragment;
    }

    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    /**
     * 是否使用 eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * @return 当前页的Fragment
     */
    protected abstract F getFragment();

    /**
     * view 初始化
     *
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {
        // 添加fragment
        if (mContanierFragment == null) {
            mContanierFragment = getFragment();
            if (!mContanierFragment.isAdded()) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fl_fragment_container, mContanierFragment);
                transaction.commit();
            }
        }
    }

    /**
     * 数据初始化
     */
    protected void initData(){}
}
