package com.zz.recycleviewmvptest.mvp.web_page_list;

import android.content.Context;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;

import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-25
 */
public class MenuAdapter extends CommonAdapter<MenuAdapter.MenuBean> {

    public MenuAdapter(Context context, int layoutId, List<MenuBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MenuBean menuBean, int position) {
        holder.setImageResource(R.id.ivMenuLogo, menuBean.getImage());
        holder.setText(R.id.tvMenuTitle, menuBean.getTitle());
    }

    public static class MenuBean{

        private String title;
        private int image;

        public String getTitle() {
            return title;
        }

        public MenuBean(String title, int imageId) {
            this.title = title;
            this.image = imageId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }
}
