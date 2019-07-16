package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/***
 * 添加用户
 */
public class AddUserActivity extends BaseActivity {
    @Override
    protected Fragment getFragment() {
        return new AddUserFragment();
    }
}
