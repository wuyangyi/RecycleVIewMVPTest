package com.zz.recycleviewmvptest.mvp.chess;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-05
 */
public class ChessActivity extends BaseActivity {
    @Override
    protected Fragment getFragment() {
        return new ChessFragment();
    }
}
