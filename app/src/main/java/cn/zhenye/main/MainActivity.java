package cn.zhenye.main;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.voicereverse.VoiceFileActivity;
import cn.zhenye.voicereverse.VoiceReverseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;


public class MainActivity extends ZyCommonActivity implements View.OnClickListener {
    private LinearLayout mVoiceReverseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        mVoiceReverseBtn = findViewById(R.id.ll_main_voice_reverse);
        mVoiceReverseBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ll_main_voice_reverse:
                ActivityUtil.safeStartActivityWithActivity(this, VoiceFileActivity.class);
        }
    }
}
