package com.zz.recycleviewmvptest.mvp.page_list;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.HeaderAndFooterWrapper;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.CornerTransform;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现的下拉加载
 */
/**
 * 福利
 */
public class PageListFragment extends BaseListFragment<PageListContract.Presenter, FlListBean.ResultsListBean> implements PageListContract.View {
    private SwipeMenuRecyclerView mRvList;
    private CommonAdapter<FlListBean.ResultsListBean> mAdapter;
    @Override
    protected void initView(View rootView) {
        mRvList = rootView.findViewById(R.id.rv_list);
        mSrlLayout = rootView.findViewById(R.id.srl_refresh_layout);
        mListData = new ArrayList<>();
        getAdapter();
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        if (isNeedLoadMore()) {
            mHeaderAndFooterWrapper.addFootView(getFooterView());
        }
        mRvList.setItemAnimator(new DefaultItemAnimator());
        //设置添加删除按钮
        mRvList.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setBackground(R.color.delColor)
                        .setTextColor(Color.WHITE)
                        .setText("删除")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
            }
        });
        mRvList.addItemDecoration(new DefaultItemDecoration(getDriverColor())); //添加item分割线--------设置分割线
        //设置滑动菜单item监听
        mRvList.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection();//左边还是右边菜单
                int adapterPosition = menuBridge.getAdapterPosition();//    recyclerView的Item的position。
                int position = menuBridge.getPosition();// 菜单在RecyclerView的Item中的Position。

                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    mListData.remove(adapterPosition);//删除item
                    mAdapter.notifyDataSetChanged();
                    ToastUtils.showToast("删除" + adapterPosition);
                }
            }
        });
        mRvList.setAdapter(mHeaderAndFooterWrapper);
        if (isNeedListDriver()) {
            mRvList.addItemDecoration(new DefaultItemDecoration(getDriverColor())); //添加item分割线--------设置分割线
        }
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);
        mSrlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { //实现下拉加载
//                mPage = 1;
//                mMaxId = 0;
                mPresenter.requestNetData(mMaxId, false, mPage);
            }
        });
        mSrlLayout.setEnabled(isNeedRefresh()); //下拉刷新的开启和关闭
    }

    @Override
    protected void initData() {
        super.initData();
        mListData = new ArrayList<>();
        mRvList.setAdapter(getAdapter());
        if (mPresenter == null) {
            mPresenter = new PageListPresenter(this);
        }
        mPresenter.requestNetData(0, false, 1);
    }

    @Override
    protected String setCenterTitle() {
        return "福利";
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_page_list;
    }

    @Override
    public void onNetSuccess(List<FlListBean.ResultsListBean> data, boolean isLoadMore) {
        List<FlListBean.ResultsListBean> mData = data;
        mData.addAll(mListData);
        mListData.clear();
        mListData.addAll(mData);
        mMaxId = getMaxId();
        mPage = getPage(data);
        mAdapter.notifyDataSetChanged();
        hideRefreshState(isLoadMore);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new CommonAdapter<FlListBean.ResultsListBean>(context, R.layout.item_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, FlListBean.ResultsListBean data, int position) {
                holder.setText(R.id.tv_content, data.get_id());
                CornerTransform transform = new CornerTransform(mContext, Utils.dpToPixel(mContext, 5F));
                Glide.with(context)
                        .load(data.getUrl())
                        .bitmapTransform(transform)
                        .into(holder.getImageViwe(R.id.iv_head));

            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Toast.makeText(context, "点击"+position, Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("点击" + position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return mAdapter;
    }
}
