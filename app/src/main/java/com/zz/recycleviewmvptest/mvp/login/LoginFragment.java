package com.zz.recycleviewmvptest.mvp.login;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.mvp.mine.add_user.AddUserActivity;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.editview.EditTextDelOrSee;
import com.zz.recycleviewmvptest.widget.imageview.CodeImageView;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

/**
 * author: wuyangyi
 * date: 2019-09-23
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {
    private RadioButton mBtnPwd;
    private RadioButton mBtnCode;
    private View mLlPwd; //密码布局
    private View mLlCode; //验证码布局
    private View mLlImageCode; //图形验证码布局
    private TextView mBtLogin; //登录按钮
    private EditTextDelOrSee mEdsPhone; //手机号输入框
    private EditTextDelOrSee mEdsPwd; //密码输入框
    private EditTextDelOrSee mEdsCode; //验证码输入框
    private EditTextDelOrSee mEdsImageCode; //图形验证码输入框
    private CodeImageView mCivCode; //图形验证码
    private TextView mGetCodeBtn; //获得验证码按钮

    private boolean mIsByPwd = true;

    private String mPhone; //手机号
    private String mPwd; //密码
    private String mCode; //验证码
    private String mImageCode; //图形验证码


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
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.tv_jump).setOnClickListener(this);
        mBtnPwd = rootView.findViewById(R.id.btnPwd);
        mBtnCode = rootView.findViewById(R.id.btnCode);
        mLlPwd = rootView.findViewById(R.id.llPwd);
        mLlCode = rootView.findViewById(R.id.llCode);
        mLlImageCode = rootView.findViewById(R.id.llImageCode);
        mBtLogin = rootView.findViewById(R.id.btLogin);
        mEdsPhone = rootView.findViewById(R.id.eds_phone);
        mEdsPwd = rootView.findViewById(R.id.eds_pwd);
        mEdsCode = rootView.findViewById(R.id.eds_code);
        mEdsImageCode = rootView.findViewById(R.id.eds_image_code);
        mCivCode = rootView.findViewById(R.id.civCode);
        mGetCodeBtn = rootView.findViewById(R.id.get_code_btn_id);
        initListener();
        setLoginWay(mIsByPwd);
    }

    private void initListener() {
        mBtnPwd.setOnClickListener(this);
        mBtnCode.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
        mGetCodeBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(this);

    }



    @Override
    public void loginState(boolean isSuccess, boolean isNeedRegister, String message) {
        closeLoadingView();
        if (isSuccess) {
            if (isNeedRegister) {
                AddUserActivity.startToAddUserActivity(getContext(), mPhone);
                getActivity().finish();
            } else {
                goHome(true);
            }
        } else {
            ToastUtils.showLongToast(message);
        }
    }

    @Override
    public void setCodeButText(String text) {
        mGetCodeBtn.setText(text);
    }

    @Override
    public void setCodeButEnable(boolean enable) {
        mGetCodeBtn.setEnabled(enable);
    }

    @Override
    public String getRealImageCode() {
        return mCivCode.getmCode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jump: //跳过
                goHome(true);
                getActivity().finish();
                break;
            case R.id.btnPwd: //密码登录选择
                setUseCenterLoading();
                setLoginWay(true);
                break;
            case R.id.btnCode: //验证码登录选择
                setLoginWay(false);
                break;
            case R.id.get_code_btn_id: //获得验证码
                getCode();
                break;
            case R.id.btLogin: //登录按钮
                doLogin();
                break;
        }
    }

    /**
     * 设置登录方式
     * @param isByPwd 是否是密码登录
     */
    private void setLoginWay(boolean isByPwd) {
        hideKeyBoard();
        mIsByPwd = isByPwd;
        mBtnPwd.setChecked(isByPwd);
        mBtnCode.setChecked(!isByPwd);
        if (isByPwd) {
            mLlPwd.setVisibility(View.VISIBLE);
            mLlCode.setVisibility(View.GONE);
            mLlImageCode.setVisibility(View.GONE);
            mEdsCode.getEdtCenter().setText("");
            mEdsImageCode.getEdtCenter().setText("");
        } else {
            mLlPwd.setVisibility(View.GONE);
            mLlCode.setVisibility(View.VISIBLE);
            mLlImageCode.setVisibility(View.VISIBLE);
            mEdsPwd.getEdtCenter().setText("");
            mCivCode.resume();
        }
    }

    private void hideKeyBoard() {
        Utils.hideSoftKeyboard(getContext(), mEdsPhone.getEdtCenter());
        Utils.hideSoftKeyboard(getContext(), mEdsPwd.getEdtCenter());
        Utils.hideSoftKeyboard(getContext(), mEdsImageCode.getEdtCenter());
        Utils.hideSoftKeyboard(getContext(), mEdsCode.getEdtCenter());
    }

    /**
     * 获得手机验证码
     */
    private void getCode() {
        if (!checkPhone()) {
            return;
        }
        mPresenter.getCodeByPhone(mPhone);
    }

    //手机号检验
    private boolean checkPhone() {
        mPhone = mEdsPhone.getEdtCenter().getText().toString();
        if (mPhone == null || mPhone.isEmpty()) {
            ToastUtils.showLongToast("手机号不能为空");
            return false;
        }
        if (!DataUtils.checkPhone(mPhone)) {
            ToastUtils.showLongToast("手机号格式错误");
            return false;
        }
        return true;
    }

    //登录
    private void doLogin() {
        hideKeyBoard();
        if (!checkPhone()) {
            return;
        }
        if (mIsByPwd) {
            mPwd = mEdsPwd.getEdtCenter().getText().toString();
            doLoginByPwd();
        } else {
            mCode = mEdsCode.getEdtCenter().getText().toString();
            mImageCode = mEdsImageCode.getEdtCenter().getText().toString();
            doLoginByCode();
        }
    }

    //密码登录
    private void doLoginByPwd() {
        if (mPwd == null || mPwd.length() < 6) {
            ToastUtils.showLongToast("请输入6-20位的密码");
            return;
        }
        mPresenter.loginByPwd(mPhone, mPwd);
    }

    //验证码登录
    private void doLoginByCode() {
        if (mCode == null || mCode.isEmpty()) {
            ToastUtils.showLongToast("请输入验证码");
            return;
        }
        if (mImageCode == null || mImageCode.isEmpty()) {
            ToastUtils.showLongToast("请输入图形验证码");
            return;
        }
        mPresenter.loginByCode(mPhone, mCode, mImageCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
