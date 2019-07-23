package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ItemViewDelegate;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;

/**
 * 我发送的消息
 */
public class ChatItem implements ItemViewDelegate<ChatBean> {
    private Context context;

    public ChatItem(Context context) {
        this.context = context;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_chat;
    }

    @Override
    public boolean isForViewType(ChatBean item, int position) {
        return item.isMe();
    }

    @Override
    public void convert(ViewHolder holder, ChatBean chatBean, ChatBean lastT, int position, int itemCounts) {
        holder.setText(R.id.tv_time, chatBean.getSend_time().substring(5, 16));
        holder.getImageViwe(R.id.iv_user_head).setImageResource(R.mipmap.ic_logo);
        holder.setText(R.id.tv_content, chatBean.getContext());
    }
}
