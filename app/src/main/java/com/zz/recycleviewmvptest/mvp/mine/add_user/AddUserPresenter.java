package com.zz.recycleviewmvptest.mvp.mine.add_user;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.widget.DataUtils;

public class AddUserPresenter extends BasePresenter<AddUserContract.View> implements AddUserContract.Presenter {
    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;
    public AddUserPresenter(AddUserContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
    }

    @Override
    public void saveUser(UserInfoBean u) {
        mUserInfoBeanDaoImpl.insertOrReplace(u);
//        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.selectByPhone(u.getPhone());
        MyInfoBean myInfoBean = DataUtils.doChangeUserInfo(u);
        setUserInfo(myInfoBean);
        mRootView.saveSuccess();
    }
}
