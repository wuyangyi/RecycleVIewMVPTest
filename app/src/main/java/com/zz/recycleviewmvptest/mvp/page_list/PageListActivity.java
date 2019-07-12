package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.base.BaseActivity;

public class PageListActivity extends BaseActivity<PageListPresenter, PageListFragment> {

    @Override
    protected PageListFragment getFragment() {
        return new PageListFragment();
    }
}
