package cn.zhenye.voicereverse.fragment.adapter;

import android.media.TimedText;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.OnAudioPlayListener;
import cn.zhenye.common.voicereverse.OnRecordListener;
import cn.zhenye.home.R;

public class VoiceChallengerAdapter implements View.OnClickListener , OnRecordListener, OnAudioPlayListener {
    
    private TextView mRecordBtn;
    private TextView mReverseBtn;
    private TextView mPlayBtn;
    private TextView mTotalTime;
    
    private VoiceChallengerAdapter(){}
    
    public VoiceChallengerAdapter(View view){
        initView(view);
    }

    private void initView(View view){
        mRecordBtn = view.findViewById(R.id.tv_voice_record_challenger);
        mReverseBtn = view.findViewById(R.id.tv_voice_play_reverse_challenger);
        mPlayBtn = view.findViewById(R.id.tv_voice_play_challenger);
        mTotalTime = view.findViewById(R.id.tv_voice_total_time_challenger);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void recordPrepare() {
        //准备录音

    }

    @Override
    public void recordStart(String savePath, String reverseSavePath) {


    }

    @Override
    public void recordStop(String savePath, String reverseSavePath) {



    }

    @Override
    public void audioStart(TextView btn) {

    }

    @Override
    public void audioPlaying(TimedText timedText) {
    }

    @Override
    public void audioStop(TextView btn) {

    }
}
