package com.zz.recycleviewmvptest.mvp.start;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-24
 * 启动页
 */
public class StartActivity extends BaseActivity<StartPresenter, StartFragment> {
    @Override
    protected StartFragment getFragment() {
        return new StartFragment();
    }
}
