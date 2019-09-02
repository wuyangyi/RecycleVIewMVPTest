package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.content.Intent;
import android.support.annotation.Nullable;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContanierFragment.onActivityResult(requestCode, resultCode, data);
    }
}
