package com.zz.recycleviewmvptest.mvp.mine;

import android.view.View;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;

public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {
    @Override
    protected void initView(View rootView) {
        mPresenter = new MinePresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_mine;
    }
}
