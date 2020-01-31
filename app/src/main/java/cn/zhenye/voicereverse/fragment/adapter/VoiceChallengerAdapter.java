package cn.zhenye.voicereverse.fragment.adapter;

import android.content.Context;
import android.media.TimedText;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import cn.zhenye.base.tool.ZTimeUtils;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.credit.manager.CreditManager;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.AudioPlayManager;
import cn.zhenye.common.voicereverse.AudioRecordManager;
import cn.zhenye.common.voicereverse.OnAudioPlayListener;
import cn.zhenye.common.voicereverse.OnRecordListener;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.fragment.VoiceConstants;
import cn.zhenye.voicereverse.vm.VoiceGameViewModel;
import cn.zhenye.voicereverse.vm.VoiceViewModel;

public class VoiceChallengerAdapter implements View.OnClickListener , OnRecordListener, OnAudioPlayListener {
    private VoiceGameViewModel mVoiceGameViewModel;
    private TextView mRecordBtn;
    private TextView mReverseBtn;
    private TextView mPlayBtn;
    private TextView mTotalTime;
    private Chronometer mCurrentTime;
    private Context mContext;
    private String mSavePath;
    
    private VoiceChallengerAdapter(){}

    public VoiceChallengerAdapter(FragmentActivity activity, View view, Chronometer currentTime, String savePath, VoiceGameViewModel voiceGameViewModel){
        initView(view);
        AudioRecordManager.getInstance().registerListener(this);
        mContext = activity.getApplicationContext();
        mCurrentTime = currentTime;
        mSavePath = savePath;
        mVoiceGameViewModel = voiceGameViewModel;
        initVM(activity);
    }

    private void initVM(FragmentActivity activity) {
        mVoiceGameViewModel.getCurrentRecordPath().observe(activity, new Observer<VoiceEntity>() {
            @Override
            public void onChanged(VoiceEntity entity) {
                if (entity == null){
                    mTotalTime.setText(mContext.getResources().getString(R.string.fragment_play_default_time));
                    mPlayBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_unable));
                    mReverseBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_unable));
                    return;
                }
                if  (!TextUtils.isEmpty(entity.answerVoicePath)) {
                    mPlayBtn.setBackground(mContext.getResources().getDrawable(R.drawable.ripple_btn));
                    mReverseBtn.setBackground(mContext.getResources().getDrawable(R.drawable.ripple_btn));
                } else {
                    mPlayBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_unable));
                    mReverseBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_unable));
                }
            }
        });
    }

    private void initView(View view){
        mRecordBtn = view.findViewById(R.id.tv_voice_record_challenger);
        mReverseBtn = view.findViewById(R.id.tv_voice_play_reverse_challenger);
        mPlayBtn = view.findViewById(R.id.tv_voice_play_challenger);
        mTotalTime = view.findViewById(R.id.tv_voice_total_time_challenger);

        mRecordBtn.setOnClickListener(this);
        mReverseBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_voice_play_challenger:
                if (AudioRecordManager.getInstance().isRecording()){
                    ZToastUtils.showShort(R.string.toast_please_stop_recording);
                    return;
                }
                if (!AudioPlayManager.getInstance().isPlaying()){
                    AudioPlayManager.getInstance().play(getPlayPath(),mPlayBtn,this);
                }
                break;
            case R.id.tv_voice_record_challenger:
                if (AudioPlayManager.getInstance().isPlaying()){
                    ZToastUtils.showShort(R.string.toast_please_stop_play);
                    return;
                }

                mRecordBtn.setClickable(false);
                if (!AudioRecordManager.getInstance().isRecording()){
                    if (!CreditManager.getInstance().decreaseCredit(VoiceConstants.RECORD_PER_CREDIT)){
                        ZToastUtils.showShort(mContext.getResources().getString(R.string.credit_limit));
                        return;
                    }

                    mRecordBtn.setText(mContext.getResources().getString(R.string.fragment_voice_author_stop));
                    String savePath = mSavePath;
                    AudioRecordManager.getInstance().start(savePath,AudioRecordManager.CHALLENGER);
                }else {
                    if (AudioRecordManager.getInstance().getSource() != AudioRecordManager.CHALLENGER){
                        return;
                    }
                    mRecordBtn.setText(mContext.getResources().getString(R.string.fragment_voice_author_play));
                    AudioRecordManager.getInstance().stop();
                }
                break;
            case R.id.tv_voice_play_reverse_challenger:
                if (AudioRecordManager.getInstance().isRecording()){
                    ZToastUtils.showShort(R.string.toast_please_stop_recording);
                    return;
                }
                if (!AudioPlayManager.getInstance().isPlaying()){
                    AudioPlayManager.getInstance().play(getReversePath(),mReverseBtn,this);
                }
                break;
        }
    }

    @Override
    public void recordPrepare() {
        //准备录音
        if (AudioRecordManager.getInstance().getSource() != AudioRecordManager.CHALLENGER){
            return;
        }
    }

    @Override
    public void recordStart(String savePath, String reverseSavePath) {
        if (AudioRecordManager.getInstance().getSource() != AudioRecordManager.CHALLENGER){
            return;
        }
        //保存播放地址
        VoiceEntity entity = new VoiceEntity();
        entity.normalVoicePath = savePath;
        entity.answerReverseVoicePath = reverseSavePath;
        mRecordBtn.setClickable(true);
        mCurrentTime.setBase(SystemClock.elapsedRealtime());
        mCurrentTime.start();
    }

    @Override
    public void recordStop(String savePath, String reverseSavePath) {
        if (AudioRecordManager.getInstance().getSource() != AudioRecordManager.CHALLENGER){
            return;
        }
        mRecordBtn.setClickable(true);
        mCurrentTime.stop();
        mCurrentTime.setText(mContext.getResources().getString(R.string.fragment_play_default_time));
        long currentTime = SystemClock.elapsedRealtime() - mCurrentTime.getBase();
        mTotalTime.setText(ZTimeUtils.secToTime(currentTime/1000));
        // 录音保存到ViewModel中。

        VoiceEntity entity = mVoiceGameViewModel.getCurrentRecordPath().getValue();
        if (entity == null){
            entity = new VoiceEntity();
        }
        entity.answerVoicePath = savePath;
        entity.answerReverseVoicePath = reverseSavePath;
        mVoiceGameViewModel.getCurrentRecordPath().setValue(entity);

    }

    @Override
    public void audioStart(TextView btn) {
        btn.setBackgroundResource(R.drawable.bg_btn_unable);
        btn.setClickable(false);
        mCurrentTime.setBase(SystemClock.elapsedRealtime());
        mCurrentTime.start();
    }

    @Override
    public void audioPlaying(TimedText timedText) {
    }

    @Override
    public void audioStop(TextView btn) {
        btn.setBackgroundResource(R.drawable.ripple_btn);
        btn.setClickable(true);
        mCurrentTime.stop();
        mCurrentTime.setText(mContext.getResources().getString(R.string.fragment_play_default_time));
    }

    @Override
    public void audioPlayError(String message) {
        ZToastUtils.showShort(message);
    }

    private String getPlayPath(){
        VoiceEntity entity = mVoiceGameViewModel.getCurrentRecordPath().getValue();
        if (entity == null){
            return null;
        }
        return entity.answerVoicePath;
    }

    private String getReversePath(){
        VoiceEntity entity = mVoiceGameViewModel.getCurrentRecordPath().getValue();
        if (entity == null){
            return null;
        }
        return entity.answerReverseVoicePath;
    }

}
