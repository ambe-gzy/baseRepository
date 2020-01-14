package cn.zhenye.common.voicereverse;

import android.media.MediaPlayer;
import android.media.TimedText;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import cn.zhenye.base.tool.ZToastUtils;

public class AudioPlayManager {
    private static AudioPlayManager INSTANCE;
    private MediaPlayer mMediaPlayer;
    public static AudioPlayManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AudioPlayManager();
        }
        return INSTANCE;
    }

    private AudioPlayManager(){
    }

    public void play( String path, final TextView btn, final OnAudioPlayListener listener){
        if (TextUtils.isEmpty(path)){
            ZToastUtils.showShort("播放路径为空");
            return;
        }
        File file = new File(path);
        if (!file.exists()){
            ZToastUtils.showShort("找不到该文件");
        }

        if (mMediaPlayer != null){
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepareAsync();
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        mMediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
                if (listener != null) {
                    listener.audioPlaying(timedText);
                }
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //开始播放
                mediaPlayer.start();
                if (listener != null){
                    listener.audioStart(btn);
                }
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //播放完毕
                mMediaPlayer.release();
                if (listener != null) {
                    mMediaPlayer = null;
                    listener.audioStop(btn);
                }
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mMediaPlayer.release();
                if (listener != null) {
                    mMediaPlayer = null;
                    listener.audioStop(btn);
                }
                return false;
            }
        });

    }

    public boolean isPlaying(){
        if (mMediaPlayer != null){
            try {
                return mMediaPlayer.isPlaying();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

}
