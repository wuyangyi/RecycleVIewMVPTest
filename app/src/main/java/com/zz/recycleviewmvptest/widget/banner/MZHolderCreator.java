package com.zz.recycleviewmvptest.widget.banner;

/**
 * author: wuyangyi
 * date: 2019-09-03
 */
public interface MZHolderCreator<VH extends MZViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}