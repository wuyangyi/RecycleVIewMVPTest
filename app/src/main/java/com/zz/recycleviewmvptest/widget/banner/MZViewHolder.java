package com.zz.recycleviewmvptest.widget.banner;

import android.content.Context;
import android.view.View;

/**
 * author: wuyangyi
 * date: 2019-09-03
 */
public interface MZViewHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}