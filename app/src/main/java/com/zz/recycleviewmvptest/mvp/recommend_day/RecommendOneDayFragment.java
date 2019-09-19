package com.zz.recycleviewmvptest.mvp.recommend_day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.RecommendBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewPagerAdapte;
import com.zz.recycleviewmvptest.mvp.recommend_day.recomend_list.RecommendListFragment;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;
import com.zz.recycleviewmvptest.widget.viewpage_indicator.TabIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.zz.recycleviewmvptest.mvp.recommend_day.RecommendOneDayActivity.IMAGE_HEAD_PATH;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 每日推荐
 */
public class RecommendOneDayFragment extends BaseFragment<RecommendOneDayContract.Presenter> implements RecommendOneDayContract.View, View.OnClickListener {
    private AppBarLayout appbar;
    private ImageView mIvBg;
    private TextView tvTitle;
    private TextView tvTitleName;
    private TabIndicator mTsvToolbar;
    private ViewPager vpFragment;
    private String mImagePath = "";

    private List<String> mTitle = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPagerAdapte viewPagerAdapter = null;

    public static RecommendOneDayFragment newInstance(Bundle bundle) {
        RecommendOneDayFragment fragment = new RecommendOneDayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImagePath = getArguments().getString(IMAGE_HEAD_PATH);
        }
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
    protected boolean showToolbar() {
        return false;
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
    protected void initView(View rootView) {
        appbar = rootView.findViewById(R.id.appbar);
        mIvBg = rootView.findViewById(R.id.iv_bg);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitleName = rootView.findViewById(R.id.tv_title_name);
        mTsvToolbar = rootView.findViewById(R.id.tsv_toolbar);
        vpFragment = rootView.findViewById(R.id.vp_fragment);
        initListener();
        rootView.findViewById(R.id.iv_back_toolbar).setOnClickListener(this);
        mTsvToolbar.setVisibility(View.GONE); //加载先隐藏
        vpFragment.setVisibility(View.GONE); //加载先隐藏
    }

    private void initViewPager() {
        mTsvToolbar.setVisibility(View.VISIBLE); //加载完显示
        vpFragment.setVisibility(View.VISIBLE); //加载完显示
        viewPagerAdapter = new ViewPagerAdapte(getChildFragmentManager());
        viewPagerAdapter.bindData(mFragmentList);
        vpFragment.setAdapter(viewPagerAdapter);
        mTsvToolbar.initTabView(vpFragment, mTitle);
        vpFragment.setOffscreenPageLimit(mFragmentList.size()); //缓存大小
    }

    private void addFragment(List<RecommendBean> data, String title) {
        mFragmentList.add(RecommendListFragment.newInstance(data, title));
    }

    private void initListener() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float point = (float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange();
                tvTitleName.setAlpha(point);
                tvTitle.setAlpha(1 - point);
            }
        });
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new RecommendOneDayPresenter(this);
        }
        mPresenter.loadData();
        Glide.with(getContext())
                .load(mImagePath)
                .into(mIvBg);
        tvTitle.setText("每日精选");
        tvTitleName.setAlpha(0);
        tvTitleName.setText("每日精选");
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_recommend_one_day;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_toolbar:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void loadDataResult(boolean isSuccess, JSONObject jsonObject) {
        if (!isSuccess) { //加载失败
            ToastUtils.showToast("加载失败");
        } else {    //加载成功
            Log.d("返回的数据", jsonObject.toString());
            try {
                JSONObject jsonObjectItemList = null;
                if (jsonObject.has("results")) {
                    jsonObjectItemList = jsonObject.getJSONObject("results");
                }
                if (jsonObject.has("category")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("category");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String value = jsonArray.getString(i);
                        mTitle.add(value);
                        if (jsonObjectItemList != null && jsonObjectItemList.has(value)) {
                            JSONArray jsonArrayItem = jsonObjectItemList.getJSONArray(value);
                            List<RecommendBean> recommendBeans = new ArrayList<>();
                            for (int j = 0; j < jsonArrayItem.length(); j++) {
                                recommendBeans.add(new RecommendBean(jsonArrayItem.getJSONObject(j)));
                            }
                            addFragment(recommendBeans, value);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        initViewPager();
        closeLoadingView();
    }
}
