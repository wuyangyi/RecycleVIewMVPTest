package com.zz.recycleviewmvptest.mvp.home;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.mvp.BaseActivity;

public class HomeActivity extends BaseActivity {
    @Override
    protected Fragment getFragment() {
        return new HomeFragment();
    }
}
