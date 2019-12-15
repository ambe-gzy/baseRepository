package cn.zhenye.voicereverse;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.main.R;

import android.os.Bundle;
import android.view.View;

public class VoiceReverseActivity extends ZyCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reverse);
        getToolbar().setVisibility(View.VISIBLE);
    }
}
