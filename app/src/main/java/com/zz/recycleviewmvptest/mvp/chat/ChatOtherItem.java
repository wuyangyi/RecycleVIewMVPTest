package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.ItemViewDelegate;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 别人发送的消息
 */
public class ChatOtherItem implements ItemViewDelegate<ChatBean> {
    private Context context;
    private List<ChatBean> mListBean;
    public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ChatItem.ImageClick mImageClick;
    private ChatItem.SoundClick mSoundClick;

    public ChatOtherItem(Context context, List<ChatBean> listBean) {
        this.context = context;
        this.mListBean = listBean;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_chat_other;
    }

    public void setImageClick(ChatItem.ImageClick imageClick) {
        this.mImageClick = imageClick;
    }

    public void setSoundClick(ChatItem.SoundClick soundClick) {
        this.mSoundClick = soundClick;
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

    //发送间隔超过1分钟
    public static boolean moreOneMinute(String lastTime, String nowTime) {
        try {
            Date lastDate = simpleDateFormat.parse(lastTime);
            Date nowDate = simpleDateFormat.parse(nowTime);
            long time = nowDate.getTime() - lastDate.getTime();
            if (time / (1000 * 60) >= 1.0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
