package com.zz.recycleviewmvptest.mvp.setting;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-24
 * 设置
 */
public class SettingActivity extends BaseActivity<SettingPresenter, SettingFragment> {
    @Override
    protected SettingFragment getFragment() {
        return new SettingFragment();
    }
}
