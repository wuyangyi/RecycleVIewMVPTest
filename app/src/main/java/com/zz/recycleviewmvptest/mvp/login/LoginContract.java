package com.zz.recycleviewmvptest.mvp.login;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;

/**
 * author: wuyangyi
 * date: 2019-09-23
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        /**
         * 登录的回调
         * @param isSuccess 是否登录成功
         * @param isNeedRegister 是否新用户注册
         */
        void loginState(boolean isSuccess, boolean isNeedRegister, String message);

        /**
         * 设置获取验证码按钮文本内容
         * @param text
         */
        void setCodeButText(String text);

        void setCodeButEnable(boolean enable);

        //获得正确的图片验证码
        String getRealImageCode();

    }

    interface Presenter extends IBasePresenter {
        /**
         * 密码登录
         * @param phone
         * @param pwd
         */
        void loginByPwd(String phone, String pwd);

        /**
         * 验证码登录
         * @param phone
         * @param code
         * @param imageCode
         */
        void loginByCode(String phone, String code, String imageCode);

        /**
         * 获得验证码
         * @param phone
         */
        void getCodeByPhone(String phone);

        void onDestroy();

    }
}
