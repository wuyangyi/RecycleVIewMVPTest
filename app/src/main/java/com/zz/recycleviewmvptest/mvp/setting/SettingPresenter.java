package com.zz.recycleviewmvptest.mvp.setting;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.MyInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;

    public SettingPresenter(SettingContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
    }

    @Override
    public void outLogin() {
        MyInfoBean myInfoBean = getUserInfo();
        myInfoBean.setIsLogin(false);
        setUserInfo(myInfoBean);
        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.selectByPhone(myInfoBean.getPhone());
        userInfoBean.setLogin(false);
        mUserInfoBeanDaoImpl.insertOrReplace(userInfoBean);
        mRootView.outLoginSuccess();
    }
}
