package cn.zhenye.main;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.voicereverse.VoiceFileActivity;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends ZyCommonActivity implements View.OnClickListener {
    private LinearLayout mVoiceReverseBtn;
    private ImageView mIvGuide;
    private BottomNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initNavigationBar();
    }

    private void initUI(){
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        mVoiceReverseBtn = findViewById(R.id.ll_main_voice_reverse);
        mIvGuide = findViewById(R.id.iv_main_guide);
        mNavigationBar = findViewById(R.id.nvb_activity_main_navigation_bar);
        mIvGuide.setColorFilter(getResources().getColor(R.color.color_3C3885), PorterDuff.Mode.SRC_IN);
        mVoiceReverseBtn.setOnClickListener(this);
        mIvGuide.setOnClickListener(this);
    }

    private void initNavigationBar() {
        final Drawable gameIcon = getResources().getDrawable(R.mipmap.ic_tab_game);
        final Drawable coinIcon = getResources().getDrawable(R.mipmap.ic_tab_coin);
        BottomNavigationItem itemGame  = new BottomNavigationItem(gameIcon,R.string.activity_main_navigation_game)
                .setActiveColorResource(R.color.white);//item被选中时BottomNavigationBar的背景颜色
        BottomNavigationItem itemCoin  = new BottomNavigationItem(coinIcon,R.string.activity_main_navigation_coin)
                .setActiveColorResource(R.color.white);//item被选中时BottomNavigationBar的背景颜色

        mNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING).
                setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE).
                setAnimationDuration(250).//动画效果持续时长
                setActiveColor(R.color.white).//BottomNavigationBar背景顏色
                setBarBackgroundColor(R.color.color_3C3885).//tittle/icon选中颜色
                setInActiveColor(R.color.color_A6A0C6);//tittle/icon未选中颜色
        mNavigationBar.addItem(itemGame).addItem(itemCoin).initialise();//添加图标
        mNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){

                }
            }

            @Override
            public void onTabUnselected(int position) {
                switch (position){

                }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        mNavigationBar.selectTab(0);
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
