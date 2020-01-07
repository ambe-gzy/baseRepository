package cn.zhenye.voicereverse.fragment;

import android.media.TimedText;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.base.tool.ZTimeUtils;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.AudioPlayer;
import cn.zhenye.common.voicereverse.AudioRecordManager;
import cn.zhenye.common.voicereverse.OnAudioPlayListener;
import cn.zhenye.common.voicereverse.OnRecordListener;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.dialog.VoicePlayConfirmDialog;
import cn.zhenye.voicereverse.vm.VoiceGameViewModel;
import cn.zhenye.voicereverse.vm.VoiceViewModel;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mintegral.msdk.base.fragment.BaseFragment;


public class VoiceAuthorFragment extends BaseFragment
        implements View.OnClickListener , OnRecordListener , OnAudioPlayListener {
    private static String TAG = VoiceAuthorFragment.class.getName();
    private VoiceViewModel mVoiceViewModel;
    private VoiceGameViewModel mVoiceGameViewModel;
    private ImageView mTitleGif;
    private TextView mGameStartBtn;
    private RelativeLayout mRlPrepareBg;
    private LinearLayout mLlPlayBg;
    private Chronometer mTimer;
    private TextView mTvTotalTime;
    private Chronometer mCurrentTime;
    private TextView mRecordBtn;
    private TextView mPlayReverseBtn;
    private TextView mPlayBtn;
    private String mSavePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initVM();
        AudioRecordManager.getInstance().setListener(this);
    }

    private void initUI(View view) {
        mTitleGif = view.findViewById(R.id.iv_voice_start_picture);
        mGameStartBtn = view.findViewById(R.id.tv_voice_author_prepare_btn);
        mRlPrepareBg = view.findViewById(R.id.rl_voice_author_prepare_bg);
        mLlPlayBg  = view.findViewById(R.id.ll_voice_author_play_bg);
        mTimer = view.findViewById(R.id.timer_voice_author_play);
        mTvTotalTime = view.findViewById(R.id.tv_voice_total_time);
        mRecordBtn = view.findViewById(R.id.tv_voice_record);
        mPlayReverseBtn = view.findViewById(R.id.tv_voice_play_reverse);
        mPlayBtn = view.findViewById(R.id.tv_voice_play);
        mCurrentTime = view.findViewById(R.id.tv_voice_current_time);

        Glide.with(getContext()).load(R.mipmap.gif_rest_book).into(mTitleGif);
        mTimer.setFormat("%mm:%ss");
        mCurrentTime.setFormat("%mm:%ss");

        mGameStartBtn.setOnClickListener(this);
        mRecordBtn.setOnClickListener(this);
        mPlayReverseBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
    }

    private void initVM() {
        mVoiceViewModel =  ViewModelProviders.of(getActivity()).get(VoiceViewModel.class);
        mVoiceGameViewModel = ViewModelProviders.of(getActivity()).get(VoiceGameViewModel.class);

        mVoiceViewModel.getCurrentFilePah().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG,"save path is: "+s);
                mSavePath = s+"/";
            }
        });
        //是否开始游戏
        mVoiceGameViewModel.getIsAuthorStart().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                       mRlPrepareBg.setVisibility(View.GONE);
                        mLlPlayBg.setVisibility(View.VISIBLE);
                }else {
                    mRlPrepareBg.setVisibility(View.VISIBLE);
                    mLlPlayBg.setVisibility(View.GONE);
                }
            }
        });
        //更新ui
        mVoiceGameViewModel.getIsAudioStartRecord().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (mSavePath == null){
                    Toast.makeText(getContext(),"保存路径为空，请重试",Toast.LENGTH_LONG).show();
                    mRecordBtn.setText(getResources().getString(R.string.fragment_voice_author_play));
                    return;
                }
                if (aBoolean){
                    mRecordBtn.setClickable(true);
                    mTimer.setBase(SystemClock.elapsedRealtime());
                    mTimer.start();
                }else {
                    mRecordBtn.setClickable(true);
                    mTimer.stop();
                    mTimer.setText(getResources().getString(R.string.fragment_play_default_time));
                    long currentTime = SystemClock.elapsedRealtime() - mTimer.getBase();
                    mTvTotalTime.setText(ZTimeUtils.secToTime(currentTime/1000));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_voice_author_prepare_btn:
                VoicePlayConfirmDialog.showDialog(getParentFragmentManager(), new VoicePlayConfirmDialog.ClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Log.d(TAG,"CLICK POSITIVE");
                        mVoiceGameViewModel.setIsAuthorStart(true);
                    }
                });
                break;
            case R.id.tv_voice_record:
                mRecordBtn.setClickable(false);
                if (!AudioRecordManager.getInstance().isRecording()){
                    mRecordBtn.setText(getResources().getString(R.string.fragment_voice_author_stop));
                    String savePath = mSavePath;
                    AudioRecordManager.getInstance().start(savePath);
                }else {
                    mRecordBtn.setText(getResources().getString(R.string.fragment_voice_author_play));
                    AudioRecordManager.getInstance().stop();
                }
                break;
            case R.id.tv_voice_play_reverse:
                if (AudioRecordManager.getInstance().isRecording()){
                    ZToastUtils.showShort(R.string.toast_please_stop_recording);
                    return;
                }

                AudioPlayer.getInstance().play(AudioRecordManager.getInstance().getReversePath(),mPlayReverseBtn,this);
                break;
            case R.id.tv_voice_play:
                if (AudioRecordManager.getInstance().isRecording()){
                    ZToastUtils.showShort(R.string.toast_please_stop_recording);
                    return;
                }

                AudioPlayer.getInstance().play(AudioRecordManager.getInstance().getSavePath(),mPlayBtn,this);
                break;
        }
    }

    @Override
    public void recordPrepare() {
        //准备录音

    }

    @Override
    public void recordStart(String savePath, String reverseSavePath) {
        //保存播放地址
        VoiceEntity entity = mVoiceGameViewModel.getCurrentRecordPath().getValue();
        if (entity == null){
            entity = new VoiceEntity();
        }
        entity.normalVoicePath = savePath;
        entity.answerReverseVoicePath = reverseSavePath;
        mVoiceGameViewModel.getCurrentRecordPath().postValue(entity);
        mVoiceGameViewModel.getIsAudioStartRecord().postValue(true);

    }

    @Override
    public void recordStop(String savePath, String reverseSavePath) {

        mVoiceGameViewModel.getIsAudioStartRecord().postValue(false);


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
        mCurrentTime.setText(getResources().getString(R.string.fragment_play_default_time));
    }
}
