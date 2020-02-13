package cn.zhenye.voicereverse;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.base.BaseFragment;
import cn.zhenye.base.tool.ZStatusbarUtils;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.fragment.VoicePlayFragment;
import cn.zhenye.voicereverse.fragment.VoiceRecordFragment;
import cn.zhenye.voicereverse.vm.VoiceViewModel;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class VoiceReverseActivity extends ZyCommonActivity {
    private static String TAG = VoiceReverseActivity.class.getName();
    public static String SAVE_PATH_KEY = "voice_reverse_save_path_key";

    private BottomNavigationBar mNavigationBar;
    private volatile Fragment mCurrentFragment;
    private static final String STATE_CURRENT_FRAGMENT_TAG = "state_current_fragment_tag"; // HomeActivity被回收重启后，用于恢复当前Fragment的标志
    private VoiceViewModel mVoiceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reverse);
        ZStatusbarUtils.setStatusBarTextColor(getWindow(),true);
        initVM();
        handleIntent();
        initToolbar();
        initUI();
        initNavigationBarAndFragment(savedInstanceState);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // HomeActivity被回收重启，保存当前显示的Fragment tag
        outState.putString(STATE_CURRENT_FRAGMENT_TAG, mCurrentFragment.getTag());
        super.onSaveInstanceState(outState);
    }

    private void initUI(){
        mNavigationBar = findViewById(R.id.nvb_voice_navigation_bar);
    }

    private void initToolbar(){
        initToolbar(null);
    }

    private void initNavigationBarAndFragment(Bundle savedInstanceState) {
        final Fragment challengeFragment = new VoicePlayFragment();
        final Fragment recordFragment = new VoiceRecordFragment();
        final Drawable gameIcon = getResources().getDrawable(R.mipmap.ic_tab_game_micro);
        final Drawable coinIcon = getResources().getDrawable(R.mipmap.ic_tab_game_micro_result);
        BottomNavigationItem itemGame  = new BottomNavigationItem(gameIcon,R.string.activity_voice_navigation_challenge)
                .setActiveColorResource(R.color.white);//item被选中时BottomNavigationBar的背景颜色
        BottomNavigationItem itemCoin  = new BottomNavigationItem(coinIcon,R.string.activity_voice_navigation_record)
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
                    case 0:
                        setToolbarTittle(getResources().getText(R.string.activity_voice_tittle_challenge));
                        switchFragment(mCurrentFragment,challengeFragment);
                        break;
                    case 1:
                        setToolbarTittle(getResources().getText(R.string.activity_voice_tittle_record));
                        switchFragment(mCurrentFragment,recordFragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        mNavigationBar.selectTab(0);

        mCurrentFragment = challengeFragment;
        // HomeActivity重启时，Fragment会被保存恢复，而此时再加载Fragment会重复加载，导致重叠。
        if (savedInstanceState == null) {
            // 正常情况下去加载根Fragment
            if (!challengeFragment.isAdded()) {
                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fl_voice_container, challengeFragment, challengeFragment.getClass().getSimpleName())
                        .commitNow();
            }
        } else {
            // HomeActivity被回收重启，需要恢复当前显示的Fragment
            String currentFragmentTab = savedInstanceState.getString(STATE_CURRENT_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(currentFragmentTab)) {
                BaseFragment targetFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(currentFragmentTab);
                if (targetFragment != null) {
                    mCurrentFragment = targetFragment;
                }
            }
        }
    }

    private void initVM() {
        mVoiceViewModel = ViewModelProviders.of(this).get(VoiceViewModel.class);
        mVoiceViewModel.getCurrentFilePah().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, s);
            }
        });

    }

    private void handleIntent(){
        Intent intent = getIntent();
        if (intent == null) {
            //todo 退出activity，并告知用户原因
            finish();
            return;
        }
        String mSavePath = intent.getStringExtra(SAVE_PATH_KEY);
        if (mSavePath !=null){
            mVoiceViewModel.setCurrentFilePah(mSavePath);
        }else {
            finish();
        }
    }

    private void switchFragment(final Fragment from, final Fragment to) {
        if (from == null || to == null) {
            return;
        }

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String toFragmentTag = to.getClass().getSimpleName();
            // 先判断是否被add
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(R.id.fl_voice_container, to, toFragmentTag);
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to);
            }
            transaction.commitNow();

            mCurrentFragment = to;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
