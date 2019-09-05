package com.zz.recycleviewmvptest.mvp.mine.add_user;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;

public class AddUserPresenter extends BasePresenter<AddUserContract.View> implements AddUserContract.Presenter {
    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;
    public AddUserPresenter(AddUserContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
    }

    @Override
    public void saveUser(UserInfoBean userInfoBean) {
        mUserInfoBeanDaoImpl.insertOrReplace(userInfoBean);
        mRootView.saveSuccess();
    }
}
