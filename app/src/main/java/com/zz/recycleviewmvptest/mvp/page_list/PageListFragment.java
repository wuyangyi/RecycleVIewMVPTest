package com.zz.recycleviewmvptest.mvp.page_list;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.FlBean;
import com.zz.recycleviewmvptest.mvp.BaseFragment;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.CornerTransform;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

public class PageListFragment extends BaseFragment<PageListContract.Presenter> implements PageListContract.View {
    private SwipeMenuRecyclerView mRvList;
    private List<FlBean.ResultsBean> mListData;
    private CommonAdapter<FlBean.ResultsBean> adapter;
    @Override
    protected void initView(View rootView) {
        mRvList = mRootView.findViewById(R.id.rv_list);
    }

    @Override
    protected void initData() {
        mListData = new ArrayList<>();
        mRvList.setAdapter(getAdapter());
        if (mPresenter == null) {
            mPresenter = new PageListPresenter(this);
        }
        mPresenter.loadData();
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_page_list;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    public void sendDataSuccess(List<FlBean.ResultsBean> data) {
        mListData.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failDeal() {
        Toast.makeText(context, "网络加载异常", Toast.LENGTH_SHORT).show();
    }

    private RecyclerView.Adapter getAdapter() {
        adapter = new CommonAdapter<FlBean.ResultsBean>(context, R.layout.item_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, FlBean.ResultsBean data, int position) {
                holder.setText(R.id.tv_content, data.get_id());
                CornerTransform transform = new CornerTransform(mContext, Utils.dpToPixel(mContext, 5F));
                Glide.with(context)
                        .load(data.getUrl())
                        .bitmapTransform(transform)
                        .into(holder.getImageViwe(R.id.iv_head));

            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(linearLayoutManager);
        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.addItemDecoration(new DefaultItemDecoration(R.color.driver_color)); //添加item分割线--------设置分割线颜色为灰色
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
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "删除" + adapterPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(context, "点击"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }
}
