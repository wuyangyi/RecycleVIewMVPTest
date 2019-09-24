package com.zz.recycleviewmvptest.mvp.password;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-24
 * 修改密码
 */
public class UpPasswordActivity extends BaseActivity<UpPasswordPresenter, UpPasswordFragment> {
    @Override
    protected UpPasswordFragment getFragment() {
        return new UpPasswordFragment();
    }
}
