package com.zz.recycleviewmvptest.mvp.login;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.local.UserInfoBeanDaoImpl;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * author: wuyangyi
 * date: 2019-09-23
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    public static final int S_TO_MS_SPACING = 1000; // s 和 ms 的比例
    public static final int SNS_TIME = 60 * S_TO_MS_SPACING; // 发送短信间隔时间，单位 ms
    private int mTimeOut = SNS_TIME;
    private UserInfoBeanDaoImpl mUserInfoBeanDaoImpl;
    private String phone;
    private String imageCode;

    CountDownTimer timer = new CountDownTimer(mTimeOut, S_TO_MS_SPACING) {
        @Override
        public void onTick(long millisUntilFinished) {
            mRootView.setCodeButText(millisUntilFinished / S_TO_MS_SPACING + "s");//显示倒数的秒速
        }
        @Override
        public void onFinish() {
            mRootView.setCodeButEnable(true);//恢复初始化状态
            mRootView.setCodeButText("获取验证码");
        }
    };

    public LoginPresenter(LoginContract.View rootView) {
        super(rootView);
        mUserInfoBeanDaoImpl = new UserInfoBeanDaoImpl();
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 密码登录
     * @param phone
     * @param pwd
     */
    @Override
    public void loginByPwd(String phone, String pwd) {
        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.pwdLogin(phone, pwd);
        if (userInfoBean == null) {
            mRootView.loginState(false, false, "账号或密码错误");
        } else {
            userInfoBean.setLogin(true);
            mUserInfoBeanDaoImpl.updateSingleData(userInfoBean);
            MyInfoBean myInfoBean = DataUtils.doChangeUserInfo(userInfoBean);
            setUserInfo(myInfoBean);
            mRootView.loginState(true, false, "登录成功");
        }
    }

    /**
     * 验证码登录
     * @param phone
     * @param code
     * @param imageCode
     */
    @Override
    public void loginByCode(String phone, String code, String imageCode) {
        this.phone = phone;
        this.imageCode = imageCode;
        //短信验证
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    private void checkOther() {
        if (!imageCode.equals(mRootView.getRealImageCode())) {
            mRootView.loginState(false, true, "图形验证码错误");
            return;
        }
        UserInfoBean userInfoBean = mUserInfoBeanDaoImpl.selectByPhone(phone);
        if (userInfoBean == null) {
            mRootView.loginState(true, true, "登录成功");
        } else {
            userInfoBean.setLogin(true);
            mUserInfoBeanDaoImpl.updateSingleData(userInfoBean);
            MyInfoBean myInfoBean = DataUtils.doChangeUserInfo(userInfoBean);
            setUserInfo(myInfoBean);
            mRootView.loginState(true, false, "登录成功");
        }
    }

    /**
     * 获得验证码
     * @param phone
     */
    @Override
    public void getCodeByPhone(String phone) {
        mRootView.setCodeButEnable(false);
        timer.start();
        SMSSDK.getVerificationCode("86", phone);
    }

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int i, int i1, Object o) {
            Message message = new Message();
            message.arg1 = i;
            message.arg2 = i1;
            message.obj = o;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            ToastUtils.showLongToast("验证码已发送");
                        } else {
                            // TODO 处理错误的结果
                            ToastUtils.showLongToast("验证码发送失败");
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            checkOther();
                        } else {
                            // TODO 处理错误的结果
                            ToastUtils.showLongToast("验证码错误");
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(message);
        }
    };

    @Override
    public void onDestroy() {
        timer.cancel();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
