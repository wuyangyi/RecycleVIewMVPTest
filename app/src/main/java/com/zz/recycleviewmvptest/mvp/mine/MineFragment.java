package com.zz.recycleviewmvptest.mvp.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.mvp.chess.ChessActivity;
import com.zz.recycleviewmvptest.mvp.friend.FriendActivity;
import com.zz.recycleviewmvptest.mvp.login.LoginActivity;
import com.zz.recycleviewmvptest.mvp.setting.SettingActivity;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.seekbar.RectangleRadioSeekBar;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View, View.OnClickListener {

    private ImageView mIvUserHead;
    private TextView mTvName;
    private LinearLayout mLlChess;
    private LinearLayout mLlSetting;
    private RectangleRadioSeekBar mSbTop;
    private View llHead;
    private ImageView ivMessage;

    private MyInfoBean mMyInfoBean;

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected int setToolBarBackgroud() {
        return R.color.white;
    }


    @Override
    protected boolean setStatusbarGrey() {
        return true;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.view_head_mine;
    }

    @Override
    protected void initView(View rootView) {
        mIvUserHead = rootView.findViewById(R.id.iv_user_head);
        mTvName = rootView.findViewById(R.id.tv_name);
        mLlChess = rootView.findViewById(R.id.ll_chess);
        mSbTop = rootView.findViewById(R.id.sb_top);
        llHead = rootView.findViewById(R.id.llHead);
        ivMessage = rootView.findViewById(R.id.ivMessage);
        mLlSetting = rootView.findViewById(R.id.ll_setting);
        initListener();
    }

    private void initListener() {
        mLlChess.setOnClickListener(this);
        mSbTop.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
        llHead.setOnClickListener(this);
    }


    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected void initData() {
        mPresenter = new MinePresenter(this);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.sendUserInfo();
        }
    }


    @Override
    public void getUserInfo(MyInfoBean myInfoBean) {
        if (myInfoBean != null) {
            mMyInfoBean = myInfoBean;
            mIvUserHead.setImageBitmap(Utils.getBitmapForPath(myInfoBean.getHead()));
            mTvName.setText(myInfoBean.getNickname());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_chess:
                startActivity(new Intent(getActivity(), ChessActivity.class));
                break;
            case R.id.sb_top:
                ToastUtils.showLongToast("正在建设中~");
                break;
            case R.id.ivMessage:
                if (mMyInfoBean == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), FriendActivity.class));
                }
                break;
            case R.id.ll_setting: //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.llHead:
                if (mMyInfoBean == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }
}
