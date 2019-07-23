package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ItemViewDelegate;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;

/**
 * 别人发送的消息
 */
public class ChatOtherItem implements ItemViewDelegate<ChatBean> {
    private Context context;

    public ChatOtherItem(Context context) {
        this.context = context;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_chat_other;
    }

    @Override
    public boolean isForViewType(ChatBean item, int position) {
        return !item.isMe();
    }

    @Override
    public void convert(ViewHolder holder, ChatBean chatBean, ChatBean lastT, int position, int itemCounts) {
        holder.setText(R.id.tv_time, chatBean.getSend_time().substring(5, 16));
        Glide.with(context)
                .load(chatBean.getUser().getUrl())
                .into(holder.getImageViwe(R.id.iv_user_head));
        holder.setText(R.id.tv_content, chatBean.getContext());
    }
}
