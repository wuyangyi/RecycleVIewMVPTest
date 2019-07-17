package com.zz.recycleviewmvptest.mvp.friend;

import com.zz.recycleviewmvptest.base.BaseActivity;

public class FriendActivity extends BaseActivity<FriendPresenter, FriendFragment> {
    @Override
    protected FriendFragment getFragment() {
        return new FriendFragment();
    }
}
