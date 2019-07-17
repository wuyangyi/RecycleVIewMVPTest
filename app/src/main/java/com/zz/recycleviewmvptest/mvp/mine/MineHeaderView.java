package com.zz.recycleviewmvptest.mvp.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.friend.FriendActivity;
import com.zz.recycleviewmvptest.mvp.mine.add_user.AddUserActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;

public class MineHeaderView {
    private Context context;
    private View mMineHeaderView;
    private MinePresenter mPresenter;
    private ImageView mIvUserHead;
    private TextView mTvName;
    private LinearLayout mLlAdd;

    public MineHeaderView(final Context context) {
        this.context = context;
        mMineHeaderView = LayoutInflater.from(context).inflate(R.layout.view_head_mine, null);
        mMineHeaderView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        mIvUserHead = mMineHeaderView.findViewById(R.id.iv_user_head);
        mTvName = mMineHeaderView.findViewById(R.id.tv_name);
        mLlAdd = mMineHeaderView.findViewById(R.id.ll_add);
        mLlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return;
                }
                context.startActivity(new Intent(context, AddUserActivity.class));
            }
        });
        mIvUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return;
                }
                context.startActivity(new Intent(context, FriendActivity.class));
            }
        });
    }

    public View getMineHeaderView() {
        return mMineHeaderView;
    }

    public void setData(UserInfoBean userInfoBean) {
        mTvName.setText(userInfoBean.getNickname());
        if (userInfoBean.getHead().isEmpty()) return;
        Glide.with(context)
                .load(userInfoBean.getHead())
                .into(mIvUserHead);
    }

    public void setPresenter(MinePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }
}
