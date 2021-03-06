package com.zz.recycleviewmvptest.mvp.web_page_list;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.PageListListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.mvp.friend.FriendActivity;
import com.zz.recycleviewmvptest.mvp.login.LoginActivity;
import com.zz.recycleviewmvptest.mvp.webview.WebViewPageActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.ZXingUtils;
import com.zz.recycleviewmvptest.widget.popwindow.TopMenuPopWindow;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯
 */
public class WebPageListFragment extends BaseListFragment<WebPageListContract.Presenter, PageListListBean.ResultsListBean> implements WebPageListContract.View {

    private CommonAdapter<PageListListBean.ResultsListBean> adapter;
    private WebPageListHeader mWebPageListHeader;
    private Dialog dialog;
    private TopMenuPopWindow topMenuPopWindow;

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
        startRefresh();
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
        if (topMenuPopWindow != null && topMenuPopWindow.isShowing()) {
            topMenuPopWindow.dismiss();
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
                    return;
                }
//                context.startActivity(new Intent(context, PageListActivity.class));
                WebViewPageActivity.startToWebViewPageActivity(context, mListData.get(position - mHeaderAndFooterWrapper.getHeadersCount()).getUrl());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                position = position - mHeaderAndFooterWrapper.getHeadersCount();
                showCodeImageDialog(mListData.get(position).getUrl());
                return true;
            }
        });
        return adapter;
    }

    private void showCodeImageDialog(String url) {
        dialog = new Dialog(getContext(), R.style.TheDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_code_image_view, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.ivClear).setOnClickListener(v -> dialog.dismiss());
        ((ImageView)view.findViewById(R.id.ivCode)).setImageBitmap(ZXingUtils.createQRImage(url, Utils.dp2px(getContext(), 200), Utils.dp2px(getContext(), 200)));
        dialog.addContentView(view, layoutParams);
        dialog.show();
    }

    @Override
    protected String setCenterTitle() {
        return "资讯";
    }

    @Override
    protected int setRightImage() {
        return R.mipmap.ico_more;
    }

    @Override
    protected void setRightImageClick() {
        showTopMenu();
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
    protected boolean setUseSatusbar() {
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

    private void showTopMenu() {
        if (topMenuPopWindow == null) {
            MenuAdapter adapter = new MenuAdapter(getContext(), R.layout.view_menu_item, DataUtils.getMenuData());
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (position == 0) {
                        ToastUtils.showToast("正在建设中~");
                    } else if (position == 1) {
                        if (((WebPageListPresenter)mPresenter).isLogin()) {
                            startActivity(new Intent(getActivity(), FriendActivity.class));
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    }
                    topMenuPopWindow.hide();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            topMenuPopWindow = TopMenuPopWindow.builder()
                    .isOutsideTouch(true)
                    .isFocus(true)
                    .with(getActivity())
                    .adater(adapter)
                    .build();
        }
        topMenuPopWindow.show();
    }


}
