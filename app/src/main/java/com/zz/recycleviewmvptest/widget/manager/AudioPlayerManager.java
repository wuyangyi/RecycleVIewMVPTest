package com.zz.recycleviewmvptest.widget.manager;

import android.media.MediaRecorder;

import java.io.File;
import java.util.UUID;

/**
 * author: wuyangyi
 * date: 2019-09-20
 */
public class AudioPlayerManager {
    public static AudioPlayerManager audioPlayerManager;

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private boolean isPrepared; //是否准备完毕
    private String mCurrentFilePath; //音频路径

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    public static AudioPlayerManager getInstance(String dir) {
        if (audioPlayerManager == null) {
            synchronized (AudioPlayerManager.class) {
                audioPlayerManager = new AudioPlayerManager(dir);
            }
        }
        return audioPlayerManager;
    }

    public AudioPlayerManager(String dir) {
        this.mDir = dir;
    }

    public void prepareAudio() {
        try {
            isPrepared = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = getFileName();
            File file = new File(dir, fileName);
            mCurrentFilePath = file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置MediaRecorder的音频原为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置编码方式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机生成文件名
     * @return
     */
    private String getFileName() {
        return UUID.randomUUID().toString() + "amr";
    }

    /**
     * 获得音量等级(1-maxLevel)
     * @param maxLevel
     * @return
     * mMediaRecorder.getMaxAmplitude()  1 - 32768
     */
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        }
        return 1;
    }

    //释放资源
    public void release() {
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
        isPrepared = false;
    }

    //取消发送
    public void cancel() {
        release();
        //删除文件
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }
}
