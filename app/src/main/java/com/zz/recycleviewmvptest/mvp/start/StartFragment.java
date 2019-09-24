package com.zz.recycleviewmvptest.mvp.start;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.mvp.login.LoginActivity;

/**
 * author: wuyangyi
 * date: 2019-09-24
 * 启动页
 */
public class StartFragment extends BaseFragment<StartContract.Presenter> implements StartContract.View {
    private TextView tvJump;
    private boolean isLogin = false;

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
    protected boolean setStatusbarGrey() {
        return true;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_start;
    }

    @Override
    protected void initView(View rootView) {
        tvJump = rootView.findViewById(R.id.tvJump);
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToHome();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new StartPresenter(this);
        isLogin = ((StartPresenter) mPresenter).isLogin();
        mPresenter.startTime();
    }


    @Override
    public void setJumpText(String text) {
        tvJump.setText(text);
    }

    @Override
    public void startToHome() {
        if (isLogin) {
            goHome(true);
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stopTime();
    }
}
