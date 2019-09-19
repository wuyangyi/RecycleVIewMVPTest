package com.zz.recycleviewmvptest.mvp.base_adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-18
 * Viewpage的adapter
 */
public class ViewPagerAdapte extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    private String[] mLitles;

    public ViewPagerAdapte(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void bindData(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void bindData(List<Fragment> list, String[] titles) {
        this.list = list;
        this.mLitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mLitles != null) {
            return mLitles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        if (list.contains(object)) {
            return POSITION_UNCHANGED;
        } else {
            return POSITION_NONE;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        View view = f.getView();
        if (view != null) {
            container.addView(view);
        }
        return f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        View view = null;
        if (object instanceof Fragment) {
            view = ((Fragment) object).getView();
        }
        if (view == null && position < list.size()) {
            view = list.get(position).getView();
        }
        if (view != null && container != null) {
            container.removeView(view);
        }
    }
}
