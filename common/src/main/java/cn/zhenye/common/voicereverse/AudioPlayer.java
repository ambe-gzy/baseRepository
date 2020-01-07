package cn.zhenye.common.voicereverse;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioRouting;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.net.Uri;
import android.widget.TextView;

import java.io.IOException;

public class AudioPlayer {
    private static AudioPlayer INSTANCE;
    private MediaPlayer mMediaPlayer;
    public static AudioPlayer getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AudioPlayer();
        }
        return INSTANCE;
    }

    private AudioPlayer(){
    }

    public void play( String path, final TextView btn, final OnAudioPlayListener listener){
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
                    listener.audioStop(btn);
                }
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mMediaPlayer.release();
                if (listener != null) {
                    listener.audioStop(btn);
                }
                return false;
            }
        });

    }

}
