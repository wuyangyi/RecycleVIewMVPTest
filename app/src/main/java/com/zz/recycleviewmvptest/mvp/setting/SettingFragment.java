package com.zz.recycleviewmvptest.mvp.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.mvp.about_us.AboutUsActivity;
import com.zz.recycleviewmvptest.mvp.login.LoginActivity;
import com.zz.recycleviewmvptest.mvp.password.UpPasswordActivity;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class SettingFragment extends BaseFragment<SettingContract.Presenter> implements SettingContract.View, View.OnClickListener {
    private Button tvOutLogin;
    private View llPwd;
    private View llAbout;
    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected String setCenterTitle() {
        return "设置";
    }

    @Override
    protected void initView(View rootView) {
        tvOutLogin = rootView.findViewById(R.id.tvOutLogin);
        llPwd = rootView.findViewById(R.id.llPwd);
        llAbout = rootView.findViewById(R.id.llAbout);
        initListener();
    }

    private void initListener() {
        tvOutLogin.setOnClickListener(this);
        llPwd.setOnClickListener(this);
        llAbout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new SettingPresenter(this);
        if (!((SettingPresenter) mPresenter).isLogin()) {
            tvOutLogin.setVisibility(View.GONE);
            llPwd.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOutLogin: //退出登录
                mPresenter.outLogin();
                break;
            case R.id.llPwd: //密码管理
                startActivity(new Intent(getActivity(), UpPasswordActivity.class));
                break;
            case R.id.llAbout:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }

    @Override
    public void outLoginSuccess() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
