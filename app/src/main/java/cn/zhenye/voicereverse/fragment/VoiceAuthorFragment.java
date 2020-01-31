package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.credit.manager.CreditManager;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.voicereverse.AudioRecordManager;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.dialog.VoicePlayConfirmDialog;
import cn.zhenye.voicereverse.fragment.adapter.VoiceAuthorAdapter;
import cn.zhenye.voicereverse.fragment.adapter.VoiceChallengerAdapter;
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

import com.bumptech.glide.Glide;
import com.mintegral.msdk.base.fragment.BaseFragment;


public class VoiceAuthorFragment extends BaseFragment
        implements View.OnClickListener{
    private static String TAG = VoiceAuthorFragment.class.getName();
    private VoiceViewModel mVoiceViewModel;
    private VoiceGameViewModel mVoiceGameViewModel;
    private ImageView mTitleGif;
    private TextView mGameStartBtn;
    private RelativeLayout mRlPrepareBg;
    private LinearLayout mLlPlayBg;
    private Chronometer mTimer;
    private View mAdapterContainer;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AudioRecordManager.getInstance().removeListener();
    }

    private void initUI(View view) {
        mAdapterContainer = view;
        mTitleGif = view.findViewById(R.id.iv_voice_start_picture);
        mGameStartBtn = view.findViewById(R.id.tv_voice_author_prepare_btn);
        mRlPrepareBg = view.findViewById(R.id.rl_voice_author_prepare_bg);
        mLlPlayBg  = view.findViewById(R.id.ll_voice_author_play_bg);
        mTimer = view.findViewById(R.id.timer_voice_author_play);

        Glide.with(getContext()).load(R.mipmap.gif_rest_book).into(mTitleGif);
        mTimer.setFormat("%mm:%ss");
        view.findViewById(R.id.ll_voice_author).setVisibility(View.VISIBLE);

        mGameStartBtn.setOnClickListener(this);
        view.findViewById(R.id.tv_voice_save).setOnClickListener(this);
        view.findViewById(R.id.iv_delete).setOnClickListener(this);
        view.findViewById(R.id.iv_question).setOnClickListener(this);
    }

    private void initVM() {
        mVoiceViewModel =  ViewModelProviders.of(getActivity()).get(VoiceViewModel.class);
        mVoiceGameViewModel = ViewModelProviders.of(getActivity()).get(VoiceGameViewModel.class);

        mVoiceViewModel.getCurrentFilePah().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG,"save path is: "+s);
                mSavePath = s+"/";
                new VoiceAuthorAdapter(VoiceAuthorFragment.this.getContext(),mAdapterContainer,mTimer,mSavePath,mVoiceGameViewModel);
                new VoiceChallengerAdapter(VoiceAuthorFragment.this.getContext(),mAdapterContainer,mTimer,mSavePath,mVoiceGameViewModel);

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
            case R.id.tv_voice_save:
                save();
                break;
            case R.id.iv_delete:
                break;
            case R.id.iv_question:
                VoicePlayConfirmDialog.showDialog(getParentFragmentManager(),null);
                break;
        }
    }

    private void save(){
        VoiceEntity entity = mVoiceGameViewModel.getCurrentRecordPath().getValue();
        if (entity != null){
            if (!CreditManager.getInstance().decreaseCredit(VoiceConstants.SAVE_PER_CREDIT)){
                ZToastUtils.showShort(getResources().getString(R.string.credit_limit));
                return;
            }

            mVoiceViewModel.setVoiceEntityLiveData(entity);
        }else {
            ZToastUtils.showShort("请先录音！");
        }
    }
}
