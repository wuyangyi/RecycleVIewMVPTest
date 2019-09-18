package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ItemViewDelegate;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.List;

import static com.zz.recycleviewmvptest.mvp.chat.ChatOtherItem.moreOneMinute;

/**
 * 我发送的消息
 */
public class ChatItem implements ItemViewDelegate<ChatBean> {
    private Context context;
    private List<ChatBean> mListBean;
    private OnViewLongClick mOnViewLongClick;
    private ImageClick mImageClick;

    public ChatItem(Context context, List<ChatBean> listBean) {
        this.context = context;
        this.mListBean = listBean;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_chat;
    }

    @Override
    public boolean isForViewType(ChatBean item, int position) {
        return item.isMe();
    }

    public void setOnViewLongClick(OnViewLongClick onViewLongClick) {
        this.mOnViewLongClick = onViewLongClick;
    }

    public void setImageClick(ImageClick imageClick) {
        this.mImageClick = imageClick;
    }

    @Override
    public void convert(ViewHolder holder, ChatBean chatBean, ChatBean lastT, int position, int itemCounts) {
        holder.setText(R.id.tv_time, chatBean.getSend_time().substring(5, 16));
        holder.getImageViwe(R.id.iv_user_head).setImageResource(R.mipmap.ic_logo);
        holder.setText(R.id.tv_content, chatBean.getContext());
        if (position == 0) {
            holder.getTextView(R.id.tv_time).setVisibility(View.VISIBLE);
        } else {
            if (moreOneMinute(mListBean.get(position - 1).getSend_time(), mListBean.get(position).getSend_time())) {
                holder.getTextView(R.id.tv_time).setVisibility(View.VISIBLE);
            } else {
                holder.getTextView(R.id.tv_time).setVisibility(View.GONE);
            }
        }

        holder.getView(R.id.tv_content).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnViewLongClick != null) {
                    mOnViewLongClick.onViewLongClickListener(v.getRight() - (v.getWidth() / 2), v.getBottom() - v.getHeight(), position);
                }
                return true;
            }
        });

        if (chatBean.getContext() == null || chatBean.getContext().isEmpty()) {
            holder.getTextView(R.id.tv_content).setVisibility(View.GONE);
        } else {
            holder.getTextView(R.id.tv_content).setVisibility(View.VISIBLE);
        }
        if (chatBean.getImagePath() != null && !chatBean.getImagePath().isEmpty()) {
            holder.setImageBitmap(R.id.iv_image, Utils.getBitmapForPath(chatBean.getImagePath()));
            holder.getImageViwe(R.id.iv_image).setVisibility(View.VISIBLE);
        } else {
            holder.getImageViwe(R.id.iv_image).setVisibility(View.GONE);
        }
        holder.getImageViwe(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return;
                }
                if (mImageClick != null) {
                    mImageClick.onImageClickListener(chatBean.getImagePath());
                }

            }
        });
    }

    public interface OnViewLongClick{
        void onViewLongClickListener(int x, int y, int position);
    }

    //图片点击事件
    public interface ImageClick{
        void onImageClickListener(String path);
    }
}
