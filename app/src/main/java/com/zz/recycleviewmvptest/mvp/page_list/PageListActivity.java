package com.zz.recycleviewmvptest.mvp.page_list;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * 福利
 */
public class PageListActivity extends BaseActivity<PageListPresenter, PageListFragment> {

    @Override
    protected PageListFragment getFragment() {
        return new PageListFragment();
    }
}
