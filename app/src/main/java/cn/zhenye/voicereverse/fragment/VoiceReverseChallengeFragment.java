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


public class VoiceReverseChallengeFragment extends Fragment implements View.OnClickListener {
    private VoiceRecorderManager mVoiceRecorderManager;
    private String mSavePath = new String();
    public static String mSavePathKey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_reverse_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initRecorder();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

        }
    }

    private void initUI(View view){
    }

    private String getFileName(){
        String name  = System.currentTimeMillis()+".wav";
        return name;
    }

    private void initRecorder() {
        mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mVoiceRecorderManager = VoiceRecorderManager.getInstance();
    }

}
