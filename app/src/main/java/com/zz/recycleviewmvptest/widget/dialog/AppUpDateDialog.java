package com.zz.recycleviewmvptest.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.AppVersionBean;
import com.zz.recycleviewmvptest.network.ApiConfig;
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

    NotificationHelper notificationHelper;

    public static AppUpDateDialog appUpDateDialog; //单利

    /**
     * 显示对话框
     * @param activity
     * @param bean
     */
    public static void showAppUpDialog(final Activity activity, AppVersionBean bean) {
        if (activity != null && bean != null) {
            appUpDateDialog = new AppUpDateDialog(activity, bean);
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
        appUpDateDialog.mSbDownload.setProgressNow(progress);
        appUpDateDialog.mTvValue.setText(progress + "%");
        if (progress == 100) {
            appUpDateDialog.hideDialog();
        }
    }

    public AppUpDateDialog(Activity activity, AppVersionBean data) {
        appVersionBean = data;
        mActivity = activity;



        initDialog();
    }

    private void initDialog() {
        if (mActivity == null){
            return;
        }
        notificationHelper = new NotificationHelper(mActivity);
        notificationHelper.showNotification(appVersionBean.getUpdateMessage(), appVersionBean.getDownloadUrl());
        layoutView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_up_app, null);
        tvTitle = layoutView.findViewById(R.id.tv_title);
        tvDesc = layoutView.findViewById(R.id.tv_desc);
        btUp = layoutView.findViewById(R.id.btUp);
        mLlSeekBar = layoutView.findViewById(R.id.llSeekBar);
        mSbDownload = layoutView.findViewById(R.id.sb_download);
        mTvValue = layoutView.findViewById(R.id.tv_value);
        tvTitle.setText(appVersionBean.getVersionName() + "更新啦");
        tvDesc.setText(appVersionBean.getUpdateMessage());
        btUp.setOnClickListener(v -> {
            mLlSeekBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(mActivity, DownloadService.class);
            intent.putExtra(ApiConfig.DOWNLOAD_URL, appVersionBean.getDownloadUrl());
            mActivity.startService(intent);
        });
        dialog = new AlertDialog.Builder(mActivity, R.style.loadingDialogStyle)
                .setCancelable(true)
                .create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dialog -> setWindowAlpha(1.0f));
        showDialog();
        dialog.setContentView(layoutView);

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
