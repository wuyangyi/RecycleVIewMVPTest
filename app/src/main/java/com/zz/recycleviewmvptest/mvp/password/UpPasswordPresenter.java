package com.zz.recycleviewmvptest.mvp.password;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class UpPasswordPresenter extends BasePresenter<UpPasswordContract.View> implements UpPasswordContract.Presenter {

    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;

    public UpPasswordPresenter(UpPasswordContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
    }

    @Override
    public void setPwd(String phone, String pwd) {
        MyInfoBean myInfoBean = getUserInfo();
        myInfoBean.setPassword(pwd);
        setUserInfo(myInfoBean);
        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.selectByPhone(myInfoBean.getPhone());
        userInfoBean.setPassword(pwd);
        mUserInfoBeanDaoImpl.insertOrReplace(userInfoBean);
        mRootView.upPwdSuccess();
    }

    @Override
    public void upPwd(String phone, String pwd, String oldPwd) {
        MyInfoBean myInfoBean = getUserInfo();
        if (!pwd.equals(myInfoBean.getPassword())) {
            ToastUtils.showLongToast("原始密码错误");
            return;
        }
        myInfoBean.setPassword(pwd);
        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.selectByPhone(myInfoBean.getPhone());
        userInfoBean.setPassword(pwd);
        mUserInfoBeanDaoImpl.insertOrReplace(userInfoBean);
        mRootView.upPwdSuccess();
    }
}
