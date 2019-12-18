package cn.zhenye.main;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.voicereverse.VoiceFileActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends ZyCommonActivity implements View.OnClickListener {
    private LinearLayout mVoiceReverseBtn;
    private ImageView mIvGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        mVoiceReverseBtn = findViewById(R.id.ll_main_voice_reverse);
        mIvGuide = findViewById(R.id.iv_main_guide);
        mIvGuide.setColorFilter(getResources().getColor(R.color.color_3C3885), PorterDuff.Mode.SRC_IN);


        mVoiceReverseBtn.setOnClickListener(this);
        mIvGuide.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ll_main_voice_reverse:
                ActivityUtil.safeStartActivityWithActivity(this, VoiceFileActivity.class);
                break;
            case R.id.iv_main_guide:
                //todo 显示如何玩；
                break;
        }
    }
}
