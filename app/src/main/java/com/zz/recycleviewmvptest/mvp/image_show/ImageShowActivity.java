package com.zz.recycleviewmvptest.mvp.image_show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-17
 */
public class ImageShowActivity extends BaseActivity {
    public final static String IMAGE_PATH = "image_path";

    @Override
    protected ImageShowFragment getFragment() {
        return ImageShowFragment.newIntents(getIntent().getExtras());
    }

    public static void startToImageShowActivity(Context context, String url) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_PATH , url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
