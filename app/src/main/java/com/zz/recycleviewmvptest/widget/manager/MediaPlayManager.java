package com.zz.recycleviewmvptest.widget.manager;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * author: wuyangyi
 * date: 2019-09-20
 * 语音播放
 */
public class MediaPlayManager {
    public static MediaPlayer mMediaPlayer;
    public static boolean isPause;
    public static MediaPlayManager mMediaPlayManager;

    public static MediaPlayManager getInstance() {
        if (mMediaPlayManager == null) {
            synchronized (MediaPlayManager.class) {
                mMediaPlayManager = new MediaPlayManager();
            }
        }
        return mMediaPlayManager;
    }

    public static void playSound(String filePath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mMediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    public void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
