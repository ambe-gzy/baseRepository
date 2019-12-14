package cn.zhenye.voicereverse;

import cn.zhenye.base.activity.FullScreenActivity;
import cn.zhenye.main.R;

import android.os.Bundle;
import android.view.View;

public class VoiceReverseActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reverse);
        getToolbar().setVisibility(View.VISIBLE);
    }
}
