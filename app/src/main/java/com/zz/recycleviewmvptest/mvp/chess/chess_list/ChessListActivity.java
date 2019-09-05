package com.zz.recycleviewmvptest.mvp.chess.chess_list;

import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-05
 */
public class ChessListActivity extends BaseActivity {
    @Override
    protected Fragment getFragment() {
        return new ChessListFragment();
    }
}
