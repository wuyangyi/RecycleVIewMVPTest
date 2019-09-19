package com.zz.recycleviewmvptest.mvp.recommend_day.recomend_list;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.RecommendBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.CornerTransform;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-18
 */
public class RecommendListFragment extends BaseFragment {
    public final static String RECOMMEND_BEAN = "recommend_bean";
    public final static String RECOMMEND_TITLE = "recommend_title";

    private List<RecommendBean> mListData = new ArrayList<>();
    private String mTitle;
    private RecyclerView mRvList;
    private CommonAdapter<RecommendBean> mAdapter;

    public static RecommendListFragment newInstance(List<RecommendBean> data, String title) {
        RecommendListFragment fragment = new RecommendListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RECOMMEND_TITLE, title);
        bundle.putParcelableArrayList(RECOMMEND_BEAN, (ArrayList<? extends Parcelable>) data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListData.addAll(getArguments().getParcelableArrayList(RECOMMEND_BEAN));
            mTitle = getArguments().getString(RECOMMEND_TITLE);
        }
    }

    @Override
    protected void initView(View rootView) {
        mRvList = rootView.findViewById(R.id.rv_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(getAdapter());

    }

    private RecyclerView.Adapter getAdapter(){
        mAdapter = new CommonAdapter<RecommendBean>(getContext(), R.layout.item_recommend_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, RecommendBean recommendBean, int position) {
                holder.setText(R.id.tvDesc, recommendBean.getDesc());
                holder.setText(R.id.tvName, recommendBean.getWho());
                holder.setText(R.id.tvTime, recommendBean.getCreatedAt());
                if (recommendBean.getUrl().endsWith(".jpg") || recommendBean.getUrl().endsWith(".png")) {
                    holder.getImageViwe(R.id.iv_head).setVisibility(View.VISIBLE);
                    CornerTransform transform = new CornerTransform(mContext, Utils.dpToPixel(mContext, 5F));
                    Glide.with(context)
                            .load(recommendBean.getUrl())
                            .bitmapTransform(transform)
                            .into(holder.getImageViwe(R.id.iv_head));
                } else {
                    holder.getImageViwe(R.id.iv_head).setVisibility(View.GONE);
                }
            }
        };
        return mAdapter;
    }


    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
