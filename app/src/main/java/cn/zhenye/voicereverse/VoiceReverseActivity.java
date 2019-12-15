package cn.zhenye.voicereverse;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.common.voicereverse.VoiceRecorderManager;
import cn.zhenye.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VoiceReverseActivity extends ZyCommonActivity implements View.OnClickListener {
    private VoiceRecorderManager mVoiceRecorderManager;
    private String mSavePath;
    public static String mSavePathKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reverse);
        getToolbar().setVisibility(View.VISIBLE);
        handleIntent();
        initUI();
        initRecorder();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_record_voice_start:
                if (mVoiceRecorderManager!=null && mSavePath != null){
                    mVoiceRecorderManager.startRecord(mSavePath,getFileName());
                }
                break;
            case R.id.tv_record_voice_stop:
                if (mVoiceRecorderManager!=null){
                    mVoiceRecorderManager.stopRecord();
                }
                break;
        }
    }

    private void handleIntent(){
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mSavePath = intent.getStringExtra(mSavePathKey);
    }

    private void initRecorder() {
        VoiceRecorderManager.initInstance(this);
        mVoiceRecorderManager = VoiceRecorderManager.getInstance();
    }

    private void initUI(){
        TextView mStartTv = findViewById(R.id.tv_record_voice_start);
        TextView mStopTv = findViewById(R.id.tv_record_voice_stop);
        mStartTv.setOnClickListener(this);
        mStopTv.setOnClickListener(this);
    }

    private String getFileName(){
        return null;
    }

}
