package com.zz.recycleviewmvptest.mvp.password;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.widget.editview.EditTextDelOrSee;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class UpPasswordFragment extends BaseFragment<UpPasswordContract.Presenter> implements UpPasswordContract.View {
    private EditTextDelOrSee edsOldPwd;
    private EditTextDelOrSee edsNewPwd;
    private EditTextDelOrSee edsSureNewPwd;
    private Button btSure;
    private View llOld;

    private String oldPwd;
    private String newPwd;
    private String sureNewPwd;

    private boolean isUpPwd; //是否修改密码

    private MyInfoBean myInfoBean;
    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_up_password;
    }

    @Override
    protected String setCenterTitle() {
        return "密码管理";
    }

    @Override
    protected void initView(View rootView) {
        edsOldPwd = rootView.findViewById(R.id.edsOldPwd);
        edsNewPwd = rootView.findViewById(R.id.edsNewPwd);
        edsSureNewPwd = rootView.findViewById(R.id.edsSureNewPwd);
        btSure = rootView.findViewById(R.id.btSure);
        llOld = rootView.findViewById(R.id.llOld);
        initListener();
    }

    private void initListener() {
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upPassword();
            }
        });
    }

    private void upPassword() {
        oldPwd = edsOldPwd.getEdtCenter().getText().toString();
        newPwd = edsNewPwd.getEdtCenter().getText().toString();
        sureNewPwd = edsSureNewPwd.getEdtCenter().getText().toString();
        if (isUpPwd && oldPwd.isEmpty()) {
            ToastUtils.showLongToast("原始密码不能为空");
            return;
        }
        if (newPwd.isEmpty()) {
            ToastUtils.showLongToast("新密码不能为空");
            return;
        }
        if (sureNewPwd.isEmpty()) {
            ToastUtils.showLongToast("确认不能为空");
            return;
        }
        if (!sureNewPwd.equals(newPwd)) {
            ToastUtils.showLongToast("确认密码与新密码不相同");
            return;
        }
        if (newPwd.length() < 6) {
            ToastUtils.showLongToast("请输入6-20位的密码");
            return;
        }
        if (isUpPwd) {
            Log.d("手机号", myInfoBean.getPhone() == null ? "null" : myInfoBean.getPhone());
//            mPresenter.upPwd(myInfoBean.getPhone(), newPwd, oldPwd);
        } else {
            mPresenter.setPwd(myInfoBean.getPhone(), newPwd);
        }
    }

    @Override
    protected void initData() {
        mPresenter = new UpPasswordPresenter(this);
        myInfoBean = ((UpPasswordPresenter) mPresenter).getUserInfo();
        Log.d("我的暑假", myInfoBean.toString());
        if (myInfoBean.getPassword() == null || myInfoBean.getPassword().isEmpty()) {
            isUpPwd = false;
            llOld.setVisibility(View.GONE);
        } else {
            isUpPwd = true;
        }
    }

    @Override
    public void upPwdSuccess() {
        getActivity().finish();
    }
}
