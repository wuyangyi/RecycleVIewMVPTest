package com.zz.recycleviewmvptest.mvp.friend;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.CornerTransform;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.view.SlideLayout;

import java.util.List;

public class FriendAdapter extends CommonAdapter<FlListBean.ResultsListBean> {
    private SlideLayout mSlideLayout;
    public FriendAdapter(Context context, int layoutId, List<FlListBean.ResultsListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FlListBean.ResultsListBean resultsListBean, final int position) {
        holder.setText(R.id.tv_test2, resultsListBean.getWho());
        holder.setText(R.id.tv_test3, resultsListBean.getDesc());
        CornerTransform transform = new CornerTransform(mContext, Utils.dpToPixel(mContext, 5F));
        Glide.with(mContext)
                .load(resultsListBean.getUrl())
                .bitmapTransform(transform)
                .into(holder.getImageViwe(R.id.iv_avatar));

        mSlideLayout = holder.getView(R.id.sl_parent);
        mSlideLayout.setOnSlideChangeListener(new SlideLayout.OnSlideChangeListener() {
            @Override
            public void onMenuOpen(SlideLayout slideLayout) {
                mSlideLayout = slideLayout;
            }

            @Override
            public void onMenuClose(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout = null;
                }
            }

            @Override
            public void onClick(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout.closeMenu();
                }
            }
        });
        holder.getView(R.id.ll_content_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return;
                }
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
