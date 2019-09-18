package com.zz.recycleviewmvptest.mvp.web_page_list;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.mvp.page_list.PageListActivity;
import com.zz.recycleviewmvptest.mvp.webview.WebViewPageActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;

import java.util.ArrayList;
import java.util.List;

public class WebPageListFragment extends BaseListFragment<WebPageListContract.Presenter, PageListListBean.ResultsListBean> implements WebPageListContract.View {

    private CommonAdapter<PageListListBean.ResultsListBean> adapter;
    private WebPageListHeader mWebPageListHeader;

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initHeader();
    }

    private void initHeader() {
        mWebPageListHeader = new WebPageListHeader(getContext());
        mHeaderAndFooterWrapper.addHeaderView(mWebPageListHeader.getmWebPageListHeader());
    }

    @Override
    protected void initData() {
        super.initData();
        if (mPresenter == null) {
            mPresenter = new WebPageListPresenter(this);
        }
        startRefrsh();
        List<String> list = new ArrayList<>();
        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=2293177440,3125900197&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=3967183915,4078698000&fm=27&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=3184221534,2238244948&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1794621527,1964098559&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1243617734,335916716&fm=27&gp=0.jpg");
        mWebPageListHeader.setBannerData(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebPageListHeader != null) {
            mWebPageListHeader.getmMZBanner().start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebPageListHeader != null) {
            mWebPageListHeader.getmMZBanner().pause();
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new CommonAdapter<PageListListBean.ResultsListBean>(context, R.layout.item_web_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, PageListListBean.ResultsListBean resultsListBean, int position) {
                holder.setText(R.id.tv_content, resultsListBean.getDesc());
//                holder.getView(R.id.ll_root).setOnClickListener();
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (AntiShakeUtils.isInvalidClick(view)) {
                    Toast.makeText(context, "请勿重复点击", Toast.LENGTH_SHORT).show();
                    return;
                }
//                context.startActivity(new Intent(context, PageListActivity.class));
                WebViewPageActivity.startToWebViewPageActivity(context, mListData.get(position - mHeaderAndFooterWrapper.getHeadersCount()).getUrl());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean isNeedListDriver() {
        return true;
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
    public void onNetSuccess(List<PageListListBean.ResultsListBean> data, boolean isLoadMore) {
        super.onNetSuccess(data, isLoadMore);
        closeLoadingView();
    }
}
