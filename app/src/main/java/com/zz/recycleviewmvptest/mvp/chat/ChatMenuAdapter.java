package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.mvp.web_page_list.MenuAdapter;

import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-26
 */
public class ChatMenuAdapter extends CommonAdapter<MenuAdapter.MenuBean> {

    public ChatMenuAdapter(Context context, int layoutId, List<MenuAdapter.MenuBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MenuAdapter.MenuBean menuBean, int position) {
        holder.setImageResource(R.id.ivLogo, menuBean.getImage());
        holder.setText(R.id.tvTitle, menuBean.getTitle());
    }
}
