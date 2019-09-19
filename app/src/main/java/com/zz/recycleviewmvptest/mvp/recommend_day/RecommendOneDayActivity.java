package com.zz.recycleviewmvptest.mvp.recommend_day;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zz.recycleviewmvptest.base.BaseActivity;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * 每日推荐
 */
public class RecommendOneDayActivity extends BaseActivity<RecommendOneDayPresenter, RecommendOneDayFragment> {
    public final static String IMAGE_HEAD_PATH = "image_head_path";

    @Override
    protected RecommendOneDayFragment getFragment() {
        return RecommendOneDayFragment.newInstance(getIntent().getExtras());
    }

    public static void startToRecommendOneDayActivity(Context context, String imagePath) {
        Intent intent = new Intent(context, RecommendOneDayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_HEAD_PATH, imagePath);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
