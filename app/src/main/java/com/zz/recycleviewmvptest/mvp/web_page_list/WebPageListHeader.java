package com.zz.recycleviewmvptest.mvp.web_page_list;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.mvp.page_list.PageListActivity;
import com.zz.recycleviewmvptest.widget.CornerTransform;
import com.zz.recycleviewmvptest.widget.banner.MZBannerView;
import com.zz.recycleviewmvptest.widget.banner.MZHolderCreator;
import com.zz.recycleviewmvptest.widget.banner.MZViewHolder;

import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-03
 */
public class WebPageListHeader {
    private View mWebPageListHeader;
    private Context mContext;
    private MZBannerView mMZBanner;
    public WebPageListHeader(Context context) {
        this.mContext = context;
        mWebPageListHeader = LayoutInflater.from(context).inflate(R.layout.view_webpage_head, null);
        mWebPageListHeader.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        mMZBanner = mWebPageListHeader.findViewById(R.id.mMZBanner);
        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setDataSize(2);
        mMZBanner.setCanLoop(true);
    }

    public View getmWebPageListHeader() {
        return mWebPageListHeader;
    }

    public MZBannerView getmMZBanner() {
        return mMZBanner;
    }

    public void setBannerData(final List<String> data) {
        if (data == null || data.size() == 0) {
            mMZBanner.setVisibility(View.GONE);
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mMZBanner.setPages(data, new MZHolderCreator() {
                    @Override
                    public MZViewHolder createViewHolder() {
                        return new BannerHolder();
                    }
                });
                mMZBanner.start();
                mMZBanner.setCanLoop(true);
            }
        });

    }

    public class BannerHolder implements MZViewHolder<String> {
        private ImageView mIvBanner;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_image, null);
            mIvBanner = view.findViewById(R.id.ivBanner);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            CornerTransform transform = new CornerTransform(context, 10F);
            Glide.with(context)
                    .load(data)
                    .bitmapTransform(transform)
                    .into(mIvBanner);
            mIvBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, PageListActivity.class));
                }
            });
        }
    }
}
