package cn.zhenye.voicereverse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.zhenye.common.voicereverse.VoiceRecorderManager;
import cn.zhenye.main.R;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class VoiceChallengeFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice_reverse_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initRecorder();
    }

    private void initUI(View view){
    }

    private void initRecorder() {
        VoiceRecorderManager.getInstance();
    }

}
