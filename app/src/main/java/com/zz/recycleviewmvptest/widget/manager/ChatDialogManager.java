package com.zz.recycleviewmvptest.widget.manager;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.Utils;

/**
 * author: wuyangyi
 * date: 2019-09-19
 */
public class ChatDialogManager{
    public static final String ACTION_LOADING = "action_loading";//正在录制
    public static final String ACTION_WANG_CANCEL = "action_want_cancel";//想要取消
    public static final String ACTION_SHORT = "action_short"; //说话太短

    private Context mContent;
    private Dialog dialog;
    private TextView tvContent;
    private ImageView ivLogo;
    private ImageView ivSound;

    public static ChatDialogManager chatDialogManager;

    public static ChatDialogManager getInstance(Context context) {
        if (chatDialogManager == null) {
            synchronized (ChatDialogManager.class) {
                chatDialogManager = new ChatDialogManager(context);
            }
        }
        return chatDialogManager;
    }

    public ChatDialogManager(Context context) {
        this.mContent = context;
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(mContent, R.style.MyDialog);
        View view = LayoutInflater.from(mContent).inflate(R.layout.dialog_chat, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.width = Utils.dp2px(mContent, 200);
        layoutParams.height = layoutParams.width;
        tvContent = view.findViewById(R.id.tvContent);
        ivLogo = view.findViewById(R.id.ivLogo);
        ivSound = view.findViewById(R.id.ivSound);
        dialog.addContentView(view, layoutParams);
    }

    public void show() {
        if (dialog == null) {
            initDialog();
        }
        dialog.show();
    }


    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
        chatDialogManager = null;
    }

    /**
     * 设置状态
     */
    public void setTextContent(String action) {
        switch (action) {
            case ACTION_LOADING:
                tvContent.setText("手指上滑，取消发送");
                tvContent.setTextSize(12);
                ivSound.setVisibility(View.VISIBLE);
                ivLogo.setImageDrawable(mContent.getResources().getDrawable(R.mipmap.tt_sound_speck));
                break;
            case ACTION_WANG_CANCEL:
                tvContent.setText("松开手指，取消发送");
                tvContent.setTextSize(12);
                ivLogo.setImageDrawable(mContent.getResources().getDrawable(R.mipmap.tt_sound_cancel));
                break;
            case ACTION_SHORT:
                tvContent.setText("说话时间太短");
                tvContent.setTextSize(15);
                ivLogo.setImageDrawable(mContent.getResources().getDrawable(R.mipmap.tt_sound_hint));
                ivSound.setVisibility(View.GONE);
                break;

        }
    }

    public void updateSoundLevel(int level) {
        int id;
        Log.d("音量等级", level+"");
        switch (level) {
            case 1:
                id = R.mipmap.tt_sound_volume_01;
                break;
            case 2:
                id = R.mipmap.tt_sound_volume_02;
                break;
            case 3:
                id = R.mipmap.tt_sound_volume_03;
                break;
            case 4:
                id = R.mipmap.tt_sound_volume_04;
                break;
            case 5:
                id = R.mipmap.tt_sound_volume_05;
                break;
            case 6:
                id = R.mipmap.tt_sound_volume_06;
                break;
            case 7:
                id = R.mipmap.tt_sound_volume_07;
                break;
            default:
                id = R.mipmap.tt_sound_volume_07;
                break;
        }
        ivSound.setImageDrawable(mContent.getResources().getDrawable(id));
    }
}
