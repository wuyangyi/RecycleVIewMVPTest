package com.zz.recycleviewmvptest.mvp.about_us;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-25
 */
public class AboutUsActivity extends BaseActivity {
    @Override
    protected Fragment getFragment() {
        return new AboutUsFragment();
    }
}
