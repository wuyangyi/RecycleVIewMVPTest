package com.zz.recycleviewmvptest.mvp.start;

import android.os.CountDownTimer;

import com.zz.recycleviewmvptest.base.BasePresenter;

import static com.zz.recycleviewmvptest.mvp.login.LoginPresenter.S_TO_MS_SPACING;

/**
 * author: wuyangyi
 * date: 2019-09-24
 */
public class StartPresenter extends BasePresenter<StartContract.View> implements StartContract.Presenter {
    private int mTimeOut = 5 * S_TO_MS_SPACING;

    public StartPresenter(StartContract.View rootView) {
        super(rootView);
    }

    CountDownTimer timer = new CountDownTimer(mTimeOut, S_TO_MS_SPACING) {
        @Override
        public void onTick(long millisUntilFinished) {
            mRootView.setJumpText("跳过\n" + millisUntilFinished / S_TO_MS_SPACING + " s");//显示倒数的秒速
        }
        @Override
        public void onFinish() {
            mRootView.startToHome();
        }
    };

    @Override
    public void startTime() {
        timer.start();
    }

    @Override
    public void stopTime() {
        timer.cancel();
    }
}
