package com.zz.recycleviewmvptest.mvp.friend;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.FlListBean;

import java.util.List;

public class FriendFragment  extends BaseListFragment<FriendContract.Presenter, FlListBean.ResultsListBean> implements FriendContract.View, FriendAdapter.DelItemClick {
    private FriendAdapter adapter;
    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new FriendAdapter(context, R.layout.item_qq_list, mListData);
        adapter.setDelItemClick(this);
        return adapter;
    }

    @Override
    protected String setCenterTitle() {
        return "好友列表";
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new FriendPresenter(this);
        startRefresh();
    }

    @Override
    public void onNetSuccess(List<FlListBean.ResultsListBean> data, boolean isLoadMore) {
        super.onNetSuccess(data, isLoadMore);
        closeLoadingView();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
    }

    @Override
    protected boolean setUseCenterLoading() {
        return true;
    }

    @Override
    protected boolean setUseCenterLoadingAnimation() {
        return true;
    }

    @Override
    protected int getstatusbarAndToolbarHeight() {
        return 0;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean isNeedListDriver() {
        return true;
    }

    @Override
    public void delItemClickListener(int position) {
        mListData.remove(position);
        adapter.notifyDataSetChanged();
    }
}
