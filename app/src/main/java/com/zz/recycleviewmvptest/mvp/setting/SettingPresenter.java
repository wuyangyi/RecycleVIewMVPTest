package com.zz.recycleviewmvptest.mvp.setting;

import android.util.Log;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.AppVersionBean;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.network.RequestRepository;
import com.zz.recycleviewmvptest.network.RequestSubscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.Response;


/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;
    private RequestRepository mRequestRepository;

    public SettingPresenter(SettingContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
        mRequestRepository = new RequestRepository();
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

    //检查更新
    @Override
    public void checkAppVersion() {
        Observable<AppVersionBean> observable = mRequestRepository.getAppVersion();
        Observer<AppVersionBean> observer = new RequestSubscriber<AppVersionBean>() {
            @Override
            protected void onSuccess(AppVersionBean appVersionBean) {
                if (appVersionBean == null) {
                    return;
                }
                mRootView.checkAppVersionSuccess(appVersionBean);
            }

            @Override
            protected void onFailure(String msg) {
                mRootView.checkAppVersionFail(msg);
            }
        };
        observable.subscribe(observer);
    }
}
