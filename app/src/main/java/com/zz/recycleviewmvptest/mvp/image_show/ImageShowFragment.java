package com.zz.recycleviewmvptest.mvp.image_show;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import static com.zz.recycleviewmvptest.mvp.image_show.ImageShowActivity.IMAGE_PATH;

/**
 * author: wuyangyi
 * date: 2019-09-17
 */
public class ImageShowFragment extends BaseFragment {
    private ImageView imageView;
    public static ImageShowFragment newIntents(Bundle bundle) {
        ImageShowFragment fragment = new ImageShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        imageView = rootView.findViewById(R.id.iv_image);
        if (getArguments() != null) {
            String path = getArguments().getString(IMAGE_PATH);
            imageView.setImageBitmap(DataUtils.isEmoji(path) ? Utils.getBitmapByName(getContext(), path) :Utils.getBitmapForPath(path));
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                useAnimationIntent();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                useAnimationIntent();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_image_show;
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setStatusbarGrey() {
        return false;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }
}
