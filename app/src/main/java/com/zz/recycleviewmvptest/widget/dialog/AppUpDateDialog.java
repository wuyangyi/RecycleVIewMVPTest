package com.zz.recycleviewmvptest.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.AppVersionBean;
import com.zz.recycleviewmvptest.network.ApiConfig;
import com.zz.recycleviewmvptest.network.download.DownState;
import com.zz.recycleviewmvptest.notification.NotificationHelper;
import com.zz.recycleviewmvptest.service.DownloadService;
import com.zz.recycleviewmvptest.widget.seekbar.RectangleRadioSeekBar;

/**
 * author: wuyangyi
 * date: 2019-11-20
 */
public class AppUpDateDialog {

    private AlertDialog dialog;
    private AppVersionBean appVersionBean;

    private View layoutView;
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView btUp;
    private View mLlSeekBar;
    private RectangleRadioSeekBar mSbDownload;
    private TextView mTvValue;

    private Activity mActivity;

    private NotificationHelper notificationHelper;

    private DownState state;
    private int mProgress;

    public static AppUpDateDialog appUpDateDialog; //单利

    /**
     * 显示对话框
     * @param activity
     * @param bean
     */
    public static void showAppUpDialog(final Activity activity, AppVersionBean bean, int progress, DownState state) {
        if (activity != null && bean != null) {
            appUpDateDialog = new AppUpDateDialog(activity, bean, progress, state);
        }
    }

    /**
     * 更新进度条
     * @param progress
     */
    public static void upProgress(int progress) {
        if (appUpDateDialog == null) {
            return;
        }
        if (progress == -1) {
            appUpDateDialog.btUp.setText("继续");
        } else {
            appUpDateDialog.mProgress = progress;
            appUpDateDialog.mSbDownload.setProgressNow(progress);
            appUpDateDialog.mTvValue.setText(progress + "%");
        }
//        if (progress == 100) {
//            appUpDateDialog.hideDialog();
//        }
    }

    public AppUpDateDialog(Activity activity, AppVersionBean data, int progress, DownState state) {
        appVersionBean = data;
        mActivity = activity;
        this.state = state;
        mProgress = progress;
        initDialog();
    }

    private void initDialog() {
        if (mActivity == null){
            return;
        }
        Log.d("dialogprogress:", "" + mProgress  +  "    "  + state);
        notificationHelper = new NotificationHelper(mActivity);
        layoutView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_up_app, null);
        tvTitle = layoutView.findViewById(R.id.tv_title);
        tvDesc = layoutView.findViewById(R.id.tv_desc);
        btUp = layoutView.findViewById(R.id.btUp);
        mLlSeekBar = layoutView.findViewById(R.id.llSeekBar);
        mSbDownload = layoutView.findViewById(R.id.sb_download);
        mTvValue = layoutView.findViewById(R.id.tv_value);
        tvTitle.setText(appVersionBean.getVersionName() + "更新啦");
        tvDesc.setText(appVersionBean.getUpdateMessage());

        initFinishButtonStatus(mProgress);
        btUp.setOnClickListener(v -> {
            mLlSeekBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(mActivity, DownloadService.class);
            intent.putExtra(ApiConfig.DOWNLOAD_URL, appVersionBean.getDownloadUrl());
            if (state == null || state == DownState.FINISH){ //点击升级
                intent.setAction(DownloadService.DOWNLOAD_START);
                state = DownState.START;
                btUp.setText("暂停");
                notificationHelper.updateProgress(mProgress == -1 ? 0 : mProgress);
            } else if(state == DownState.START) { //点击暂停
                intent.setAction(DownloadService.DOWNLOAD_STOP);
                state = DownState.PAUSE;
                btUp.setText("继续");
                notificationHelper.stopProgress(mProgress == -1 ? 0 : mProgress);
            } else if (state == DownState.PAUSE) { //点击继续
                intent.setAction(DownloadService.DOWNLOAD_GO_ON_START);
                state = DownState.START;
                btUp.setText("暂停");
                notificationHelper.updateProgress(mProgress == -1 ? 0 : mProgress);
            }
            mActivity.startService(intent);
        });
        dialog = new AlertDialog.Builder(mActivity, R.style.loadingDialogStyle)
                .setCancelable(true)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(dialog -> setWindowAlpha(1.0f));
        showDialog();
        dialog.setContentView(layoutView);
        if (mProgress != -1) {
            mLlSeekBar.setVisibility(View.VISIBLE);
            Log.d("进度", ""+mProgress);
            mTvValue.setText(mProgress + "%");
            mSbDownload.setProgressNow(mProgress);
        }

    }

    private void initFinishButtonStatus(int progress) {
        if (state == null || state == DownState.FINISH){
            btUp.setText("升级");
        } else if(state == DownState.START) {
            btUp.setText("暂停");
        } else if (state == DownState.PAUSE) {
            notificationHelper.stopProgress(progress == -1 ? 0 : progress);
            btUp.setText("继续");
        }
    }


    public void showDialog() {
        setWindowAlpha(0.8f);
        if (mActivity != null && dialog != null) {
            dialog.show();
        }
    }

    public void hideDialog() {
        if (mActivity != null && dialog != null) {
            dialog.dismiss();
        }
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        params.verticalMargin = 100;
        mActivity.getWindow().setAttributes(params);
    }

}
