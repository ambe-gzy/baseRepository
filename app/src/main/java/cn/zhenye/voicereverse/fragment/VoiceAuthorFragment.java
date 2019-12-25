package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.common.voicereverse.OnRecordListener;
import cn.zhenye.common.voicereverse.VoiceRecorderManager;
import cn.zhenye.main.R;
import cn.zhenye.voicereverse.dialog.VoicePlayConfirmDialog;
import cn.zhenye.voicereverse.vm.VoiceGameViewModel;
import cn.zhenye.voicereverse.vm.VoiceViewModel;

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


public class VoiceAuthorFragment extends Fragment implements View.OnClickListener , OnRecordListener {
    private static String TAG = VoiceAuthorFragment.class.getName();
    private VoiceViewModel mVoiceViewModel;
    private VoiceGameViewModel mVoiceGameViewModel;
    private ImageView mTitleGif;
    private TextView mGameStartBtn;
    private RelativeLayout mRlPrepareBg;
    private LinearLayout mLlPlayBg;
    private Chronometer mTimer;
    private TextView mTvTotalTime;
    private ImageView mPlayBtn;
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
        initVM();
        initUI(view);
    }

    private void initUI(View view) {
        mTitleGif = view.findViewById(R.id.iv_voice_start_picture);
        mGameStartBtn = view.findViewById(R.id.tv_voice_author_prepare_btn);
        mRlPrepareBg = view.findViewById(R.id.rl_voice_author_prepare_bg);
        mLlPlayBg  = view.findViewById(R.id.ll_voice_author_play_bg);
        mTimer = view.findViewById(R.id.timer_voice_author_play);
        mTvTotalTime = view.findViewById(R.id.tv_voice_total_time);
        mPlayBtn = view.findViewById(R.id.iv_voice_play);
        mPlayReverseBtn = view.findViewById(R.id.tv_voice_play_reverse);
        mPlayAgainBtn = view.findViewById(R.id.tv_voice_play_again);
        mPlayFinishBtn = view.findViewById(R.id.tv_voice_play_finish);

        Glide.with(getContext()).load(R.mipmap.gif_rest_book).into(mTitleGif);
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
                mSavePath = s;
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
        //是否开始录音
        mVoiceGameViewModel.getIsAudioStartRecord().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (mSavePath == null){
                    Toast.makeText(getContext(),"保存路径为空，请重试",Toast.LENGTH_LONG).show();
                    mPlayBtn.setImageResource(R.mipmap.ic_play);
                    return;
                }

                if (!aBoolean){
                    mPlayBtn.setImageResource(R.mipmap.ic_pause);
                    VoiceRecorderManager.getInstance().startRecord(mSavePath,String.valueOf(System.currentTimeMillis()),VoiceAuthorFragment.this);
                }else {
                    mPlayBtn.setImageResource(R.mipmap.ic_play);
                    VoiceRecorderManager.getInstance().stopRecord();
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
            case R.id.iv_voice_play:
                //TODO 操作开始播放
                mVoiceGameViewModel.setIsAudioStartRecord(VoiceRecorderManager.getInstance().isRecord());
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
        //开始录音

    }

    @Override
    public void onRecordStop(String savePath, String reverseSavePath) {
        //停止录音

    }
}
