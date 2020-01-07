package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.base.tool.TimeUtil;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.AudioRecordManager;
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


public class VoiceAuthorFragment extends BaseFragment implements View.OnClickListener , OnRecordListener {
    private static String TAG = VoiceAuthorFragment.class.getName();
    private VoiceViewModel mVoiceViewModel;
    private VoiceGameViewModel mVoiceGameViewModel;
    private ImageView mTitleGif;
    private TextView mGameStartBtn;
    private RelativeLayout mRlPrepareBg;
    private LinearLayout mLlPlayBg;
    private Chronometer mTimer;
    private TextView mTvTotalTime;
    private TextView mPlayBtn;
    private TextView mPlayReverseBtn;
    private TextView mPlayAgainBtn;
    private TextView mPlayFinishBtn;
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
        mPlayBtn = view.findViewById(R.id.tv_voice_play);
        mPlayReverseBtn = view.findViewById(R.id.tv_voice_play_reverse);
        mPlayAgainBtn = view.findViewById(R.id.tv_voice_play_again);
        mPlayFinishBtn = view.findViewById(R.id.tv_voice_play_finish);

        Glide.with(getContext()).load(R.mipmap.gif_rest_book).into(mTitleGif);
        mTimer.setFormat("%mm:%ss");

        mGameStartBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
        mPlayReverseBtn.setOnClickListener(this);
        mPlayAgainBtn.setOnClickListener(this);
        mPlayFinishBtn.setOnClickListener(this);
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
                    mPlayBtn.setText(getResources().getString(R.string.fragment_voice_author_play));
                    return;
                }
                if (aBoolean){
                    mPlayBtn.setClickable(true);
                    mTimer.setBase(SystemClock.elapsedRealtime());
                    mTimer.start();
                }else {
                    mPlayBtn.setClickable(true);
                    mTimer.stop();
                    mTimer.setText(getResources().getString(R.string.fragment_play_default_time));
                    long currentTime = SystemClock.elapsedRealtime() - mTimer.getBase();
                    mTvTotalTime.setText(TimeUtil.secToTime(currentTime/1000));
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
            case R.id.tv_voice_play:
                //TODO 开始播放
                mPlayBtn.setClickable(false);
                if (!AudioRecordManager.getInstance().isRecording()){
                    mPlayBtn.setText(getResources().getString(R.string.fragment_voice_author_stop));
                    String savePath = mSavePath;
                    AudioRecordManager.getInstance().start(savePath);
                }else {
                    mPlayBtn.setText(getResources().getString(R.string.fragment_voice_author_play));
                    AudioRecordManager.getInstance().stop();
                }
                break;
            case R.id.tv_voice_play_reverse:
                //todo 倒放
                break;
            case R.id.tv_voice_play_again:
                //todo 重新录制
                break;
            case R.id.tv_voice_play_finish:
                //todo 完成录制
        }
    }

    @Override
    public void onRecordPrepare() {
        //准备录音

    }

    @Override
    public void onRecordStart(String savePath, String reverseSavePath) {
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
    public void onRecordStop(String savePath, String reverseSavePath) {

        mVoiceGameViewModel.getIsAudioStartRecord().postValue(false);


    }
}
