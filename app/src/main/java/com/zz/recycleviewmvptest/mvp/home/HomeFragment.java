package com.zz.recycleviewmvptest.mvp.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.mvp.friend.FriendActivity;
import com.zz.recycleviewmvptest.mvp.page_list.PageListFragment;
import com.zz.recycleviewmvptest.mvp.mine.MineFragment;
import com.zz.recycleviewmvptest.mvp.web_page_list.WebPageListFragment;
import com.zz.recycleviewmvptest.widget.viewpage.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    public final static int PAGE_HOME = 0;
    public final static int PAGE_MINE = 1;
    private NoScrollViewPager mVpHome;
    private LinearLayout mLlHome;
    private ImageView mIvHome;
    private TextView mTvHome;
    private LinearLayout mLlMine;
    private ImageView mIvMine;
    private TextView mTvMine;
    private List<Fragment> listFragment;
    @Override
    protected void initView(View rootView) {
        mVpHome = rootView.findViewById(R.id.vp_home);
        mLlHome = rootView.findViewById(R.id.ll_home);
        mIvHome = rootView.findViewById(R.id.iv_home);
        mTvHome = rootView.findViewById(R.id.tv_home);
        mLlMine = rootView.findViewById(R.id.ll_mine);
        mIvMine = rootView.findViewById(R.id.iv_mine);
        mTvMine = rootView.findViewById(R.id.tv_mine);
        initViewPage();
        selectItem(PAGE_HOME);
        initListener();
    }

    private void initListener() {
        mLlHome.setOnClickListener(this);
        mLlMine.setOnClickListener(this);
    }

    /**
     * 初始化viewpage
     */
    private void initViewPage() {
        listFragment = new ArrayList<>();
        listFragment.add(new WebPageListFragment());
        listFragment.add(new MineFragment());
        mVpHome.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return listFragment.get(i);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setRightImage() {
        return R.mipmap.ico_message;
    }

    @Override
    protected void setRightImageClick() {
        context.startActivity(new Intent(context, FriendActivity.class));
    }

    @Override
    protected int setLeftImage() {
        return 0;
    }

    @Override
    protected String setCenterTitle() {
        return "My App";
    }

    @Override
    protected int setTitleBg() {
        return R.color.home_bottom;
    }

    @Override
    protected int setCenterTitleColor() {
        return R.color.white;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_home;
    }



    @Override
    public void setPresenter(Object presenter) {

    }

    /**
     * 按钮选中状态和viewpage显示的页面
     */
    public void selectItem(int page) {
        mIvHome.setImageDrawable(getResources().getDrawable(page == PAGE_HOME ? R.mipmap.ic_home_select : R.mipmap.ic_home));
        mTvHome.setTextColor(getResources().getColor(page == PAGE_HOME ? R.color.home_bottom : R.color.home_bottom_text_normal));
        mIvMine.setImageDrawable(getResources().getDrawable(page == PAGE_MINE ? R.mipmap.ic_mine_select : R.mipmap.ic_mine));
        mTvMine.setTextColor(getResources().getColor(page == PAGE_MINE ? R.color.home_bottom : R.color.home_bottom_text_normal));
        mVpHome.setCurrentItem(page, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                selectItem(PAGE_HOME);
                break;
            case R.id.ll_mine:
                selectItem(PAGE_MINE);
                break;
        }
    }
}
