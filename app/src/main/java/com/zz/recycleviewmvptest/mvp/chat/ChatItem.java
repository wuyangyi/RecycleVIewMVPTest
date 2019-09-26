package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ItemViewDelegate;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.DataUtils;
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
    private SoundClick mSoundClick;

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

    public void setSoundClick(SoundClick soundClick) {
        this.mSoundClick = soundClick;
    }

    public void setImageClick(ImageClick imageClick) {
        this.mImageClick = imageClick;
    }

    @Override
    public void convert(ViewHolder holder, ChatBean chatBean, ChatBean lastT, int position, int itemCounts) {
        holder.setText(R.id.tv_time, chatBean.getSend_time().substring(5, 16));
        if (chatBean.getMyInfoBean().getHead() != null || !chatBean.getMyInfoBean().getHead().isEmpty()) {
            holder.getImageViwe(R.id.iv_user_head).setImageBitmap(Utils.getBitmapForPath(chatBean.getMyInfoBean().getHead()));
        }
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
                    int y = v.getBottom() - v.getHeight();
                    mOnViewLongClick.onViewLongClickListener(v.getRight() - (v.getWidth() / 2), y - v.getHeight() / 2, position, chatBean.isMe(), DataUtils.MESSAGE_CONTEXT);
                }
                return true;
            }
        });
        holder.getView(R.id.iv_image).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnViewLongClick != null) {
                    int y = v.getBottom() - v.getHeight();
                    mOnViewLongClick.onViewLongClickListener(v.getRight() - (v.getWidth() / 2), y - v.getHeight() / 2, position, chatBean.isMe(), DataUtils.MESSAGE_IMAGE);
                }
                return true;
            }
        });

        holder.getView(R.id.llSound).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnViewLongClick != null) {
                    int y = v.getBottom() - v.getHeight();
                    mOnViewLongClick.onViewLongClickListener(v.getRight() - (v.getWidth() / 2), y - v.getHeight() / 2, position, chatBean.isMe(), DataUtils.MESSAGE_SOUND);
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
            holder.setImageBitmap(R.id.iv_image, DataUtils.isEmoji(chatBean.getImagePath()) ? Utils.getBitmapByName(context, chatBean.getImagePath()) : Utils.getBitmapForPath(chatBean.getImagePath()));
            holder.getImageViwe(R.id.iv_image).setVisibility(View.VISIBLE);
        } else {
            holder.getImageViwe(R.id.iv_image).setVisibility(View.GONE);
        }
        if (chatBean.getSoundPath() != null && !chatBean.getSoundPath().isEmpty()) {
            holder.getView(R.id.llSound).setVisibility(View.VISIBLE);
            holder.getTextView(R.id.tvSoundTime).setText((int)chatBean.getSoundTime() + "\"");
        } else {
            holder.getView(R.id.llSound).setVisibility(View.GONE);
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
        holder.getView(R.id.llSound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSoundClick.OnSoundClickListener(chatBean.getSoundPath(), holder.getImageViwe(R.id.ivSound), chatBean.isMe());
            }
        });
    }

    public interface OnViewLongClick{
        void onViewLongClickListener(int x, int y, int position, boolean isMe, String type);
    }

    //图片点击事件
    public interface ImageClick{
        void onImageClickListener(String path);
    }

    //语音点击事件
    public interface SoundClick{
        void OnSoundClickListener(String path, ImageView imageView, boolean isMe);
    }
}
