package com.zz.recycleviewmvptest.mvp.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-18
 */
public class WebViewPageActivity extends BaseActivity {
    public static final String WEB_URL = "web_url";
    @Override
    protected WebViewPageFragment getFragment() {
        return WebViewPageFragment.newInstance(getIntent().getExtras());
    }

    public static void startToWebViewPageActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WEB_URL, url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 覆盖系统的回退键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((WebViewPageFragment)mContanierFragment).onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
