package com.zz.recycleviewmvptest.widget.button;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.manager.AudioPlayerManager;
import com.zz.recycleviewmvptest.widget.manager.ChatDialogManager;

import static com.zz.recycleviewmvptest.widget.Utils.APP_SYSTEM_PATH;

/**
 * author: wuyangyi
 * date: 2019-09-19
 * 聊天按下说话按钮
 */
public class SpeckButton extends android.support.v7.widget.AppCompatTextView {
    private ChatDialogManager mChatDialogManager;
    private AudioPlayerManager mAudioPlayerManager;

    private float mTime = 0;

    private boolean isRecording;
    private boolean isWantCancel;

    public SpeckButton(Context context) {
        this(context, null);
    }

    public SpeckButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChatDialogManager = ChatDialogManager.getInstance(context);
        String dir = APP_SYSTEM_PATH + "recorder_audio";
        mAudioPlayerManager = AudioPlayerManager.getInstance(dir);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        double down = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = event.getY();
                setBackground(getResources().getDrawable(R.drawable.bg_speck_down));
                setText("松开 结束");
                handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
                break;
            case MotionEvent.ACTION_MOVE:
                double d =  -(event.getY() - down);
                if (d > getHeight()) {
                    isWantCancel = true;
                    setText("松开 取消");
                    mChatDialogManager.setTextContent(ChatDialogManager.ACTION_WANG_CANCEL);
                } else {
                    isWantCancel = false;
                    setText("松开 结束");
                    mChatDialogManager.setTextContent(ChatDialogManager.ACTION_LOADING);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isWantCancel) {//取消
                    mAudioPlayerManager.cancel();
                    handler.sendEmptyMessage(MSG_DIALOG_DIMISS);
                } else if (mTime < 0.6) {
                    mChatDialogManager.setTextContent(ChatDialogManager.ACTION_SHORT);
                    mAudioPlayerManager.cancel();
                    setBackground(getResources().getDrawable(R.drawable.bg_speck_no_down));
                    setText("按住 说话");
                    handler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                } else {//正常结束
                    mAudioPlayerManager.release();
                    handler.sendEmptyMessage(MSG_DIALOG_DIMISS);
                    if (mOnWellFinish != null) {
                        mOnWellFinish.onFinish(mAudioPlayerManager.getCurrentFilePath(), mTime);
                    }
                }
                release();
                break;
        }
        return isEnabled();
    }

    private void release() {
        isRecording = false;
        mTime = 0;
        isWantCancel = false;
    }

    public static final int MSG_AUDIO_PREPARED = 0x100;
    public static final int MSG_VOICE_CHANGED = 0x101; //音量改变
    public static final int MSG_DIALOG_DIMISS = 0x102; //时间短

    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    mChatDialogManager.setTextContent(ChatDialogManager.ACTION_LOADING);
                    mChatDialogManager.show();
                    mAudioPlayerManager.prepareAudio();
                    isRecording = true;
                    new Thread(mGetLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mChatDialogManager.updateSoundLevel(mAudioPlayerManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    setBackground(getResources().getDrawable(R.drawable.bg_speck_no_down));
                    setText("按住 说话");
                    mChatDialogManager.dismiss();
                    break;
            }
        }
    };

    //获取音量大小及时间
    private Runnable mGetLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    handler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private OnWellFinish mOnWellFinish;

    public void setOnWellFinish(OnWellFinish onWellFinish) {
        this.mOnWellFinish = onWellFinish;
    }

    public interface OnWellFinish {
        void onFinish(String path, float time);
    }
}
