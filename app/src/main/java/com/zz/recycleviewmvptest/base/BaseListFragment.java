package com.zz.recycleviewmvptest.base;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.BaseListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.HeaderAndFooterWrapper;
import com.zz.recycleviewmvptest.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * List加载数据的Fragment封装
 * @param <P>
 * @param <B>
 */
public abstract class BaseListFragment<P extends IBaseListPresenter, B extends BaseListBean> extends BaseFragment<P> implements BaseListView<P, B> {
    public static final int PAGE_LIST_NUMBER = 15; //加载数据的条数
    protected SwipeRefreshLayout mSrlLayout;
    protected RecyclerView mRvList;
    protected List<B> mListData;
    protected LinearLayoutManager mLayoutManager;
    protected RecyclerView.Adapter mAdapter;
    //头布局和尾布局adapter
    protected HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    protected AnimationDrawable mAnimationDrawable;//加载动画
    protected ImageView mIvLoad; //加载动画的图片
    protected TextView mTvText; //加载的文字

    protected boolean isHaveMoreData = true; //是否还有更多数据
    /**
     * 列表脚视图
     */
    protected View mFooterView;

    /**
     * 数据的条数
     */
    protected int mMaxId = 0;

    /**
     * 分页页数
     */
    protected int mPage = 1;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_base_list;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView(View rootView) {
        mRvList = rootView.findViewById(R.id.rv_list);
        mSrlLayout = rootView.findViewById(R.id.srl_refresh_layout);
        mListData = new ArrayList<>();
        mAdapter = getAdapter();
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        if (isNeedLoadMore()) {
            mHeaderAndFooterWrapper.addFootView(getFooterView());
        }
        mRvList.setAdapter(mHeaderAndFooterWrapper);
        if (isNeedListDriver()) {
            mRvList.addItemDecoration(new DefaultItemDecoration(getDriverColor())); //添加item分割线--------设置分割线
        }
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);
        mSrlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mMaxId = 0;
                mPresenter.requestNetData(mMaxId, false, mPage);
            }
        });
        mSrlLayout.setEnabled(isNeedRefresh()); //下拉刷新的开启和关闭
        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isNeedLoadMore() && isHaveMoreData && !mRvList.canScrollVertically(1)) {
                    mTvText.setText("正在加载...");
                    mIvLoad.setVisibility(View.VISIBLE);
                    handleAnimation(true);
                    mPresenter.requestNetData(mMaxId, true, mPage);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 获取没有更多的脚信息
     *
     * @return
     */
    protected View getFooterView() {
        // 添加加载更多没有了的提示
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.view_footer_no_more, null);
        mTvText = mFooterView.findViewById(R.id.tv_no_more_data_text);
        mIvLoad = mFooterView.findViewById(R.id.iv_load);
        mAnimationDrawable = (AnimationDrawable) mIvLoad.getDrawable();
        mFooterView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return mFooterView;
    }

    /**
     * 获取adapter
     * @return
     */
    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    public void onNetSuccess(List<B> data, boolean isLoadMore) {
        if (isLoadMore) { //加载更多
            mListData.addAll(data);

        } else { //刷新
            if (mListData != null && !mListData.isEmpty()) {
                mListData.clear();
            }
            mListData.addAll(data);
        }
        mMaxId = getMaxId();
        mPage = getPage(data);
        mAdapter.notifyDataSetChanged();
        hideRefreshState(isLoadMore);
        if (!isNeedLoadMore()) {
            return;
        }
        if (data.isEmpty() || data.size() < PAGE_LIST_NUMBER) {
            mFooterView.setVisibility(View.VISIBLE);
            mTvText.setText("没有更多数据了");
            mIvLoad.setVisibility(View.GONE);
            handleAnimation(false);
            isHaveMoreData = false;
        } else {
            mFooterView.setVisibility(View.VISIBLE);
            mTvText.setText("下拉加载更多");
            mIvLoad.setVisibility(View.GONE);
            handleAnimation(false);
            isHaveMoreData = true;
        }
    }

    /**
     * 是否需要上拉加载功能
     * @return
     */
    protected boolean isNeedLoadMore() {
        return true;
    }

    /**
     * 是否需要下拉刷新功能
     * @return
     */
    protected boolean isNeedRefresh() {
        return true;
    }

    /**
     * 关闭加载动画
     */
    protected void hideRefreshState(boolean isLoadMore) {
        if (isLoadMore) {

        } else {
            closeRefrsh();
        }
    }

    protected int getMaxId() {
        return mListData.size();
    }

    protected int getPage(List<B> data) {
        if (data.size() > 0) {
            mPage++;
        }
        return mPage;
    }

    /**
     * 手动刷新
     */
    @Override
    public void startRefrsh() {
        mSrlLayout.post(new Runnable() {
            @Override
            public void run() {
                mSrlLayout.setRefreshing(true);
                mPresenter.requestNetData(mMaxId, false, mPage);
            }
        });
    }

    /**
     * 关闭下拉刷新动画
     */
    protected void closeRefrsh() {
        mSrlLayout.post(new Runnable() {
            @Override
            public void run() {
                mSrlLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 是否需要分割线
     * @return
     */
    protected boolean isNeedListDriver() {
        return false;
    }

    /**
     * 分割线的颜色(默认为灰色)
     * @return
     */
    protected int getDriverColor() {
        return R.color.driver_color;
    }

    @Override
    public void onNetFailing() {
        ToastUtils.showToast("加载失败");
    }


    /**
     * 处理动画
     *
     * @param status true 开启动画，false 关闭动画
     */
    public void handleAnimation(boolean status) {
        if (mAnimationDrawable == null) {
            throw new IllegalArgumentException("load animation not be null");
        }
        if (status) {
            if (!mAnimationDrawable.isRunning()) {
                mIvLoad.setVisibility(View.VISIBLE);
                mAnimationDrawable.start();
            }
        } else {
            if (mAnimationDrawable.isRunning()) {
                mAnimationDrawable.stop();
                mIvLoad.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}
