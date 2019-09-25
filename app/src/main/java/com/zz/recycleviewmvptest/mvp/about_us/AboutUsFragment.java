package com.zz.recycleviewmvptest.mvp.about_us;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;

/**
 * author: wuyangyi
 * date: 2019-09-25
 */
public class AboutUsFragment extends BaseFragment {
    private TextView tvName;
    private TextView tvNumber;
    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_about_us;
    }

    @Override
    protected void initView(View rootView) {
        tvName = rootView.findViewById(R.id.tvName);
        tvNumber = rootView.findViewById(R.id.tvNumber);
        tvNumber.setText("当前版本号：v " + packageName(getContext()));
        tvName.setText("当前第 " + packageCode(getContext()) + " 个版本");
    }

    @Override
    protected String setCenterTitle() {
        return "关于我们";
    }

    @Override
    protected void initData() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    //获得当前版本号
    private int packageCode(Context context) {
        // 获取packagemanager的实例
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
    //获得当前版本名称
    private String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
