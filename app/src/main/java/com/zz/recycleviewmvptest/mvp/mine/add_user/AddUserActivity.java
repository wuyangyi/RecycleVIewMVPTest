package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/***
 * 添加用户
 */
public class AddUserActivity extends BaseActivity {
    public static final String LOGIN_PHONE = "login_phone";
    @Override
    protected Fragment getFragment() {
        return AddUserFragment.getInstance(getIntent().getExtras());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContanierFragment.onActivityResult(requestCode, resultCode, data);
    }


    public static void startToAddUserActivity(Context context, String phone) {
        Intent intent = new Intent(context, AddUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_PHONE, phone);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
