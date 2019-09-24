package com.zz.recycleviewmvptest.mvp.login;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-23
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginFragment> {
    @Override
    protected LoginFragment getFragment() {
        return new LoginFragment();
    }
}
