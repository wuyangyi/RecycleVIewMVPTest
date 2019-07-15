package com.zz.recycleviewmvptest.mvp.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zz.recycleviewmvptest.R;

public class MineHeaderView {
    private Context context;
    private View mMineHeaderView;

    public MineHeaderView(Context context) {
        this.context = context;
        mMineHeaderView = LayoutInflater.from(context).inflate(R.layout.view_head_mine, null);
        mMineHeaderView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
    }

    public View getMineHeaderView() {
        return mMineHeaderView;
    }
}
