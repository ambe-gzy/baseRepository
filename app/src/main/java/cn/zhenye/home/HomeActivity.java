package cn.zhenye.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.ZStatusbarUtils;
import cn.zhenye.drawer.DrawerFragment;

public class HomeActivity extends ZyCommonActivity implements View.OnClickListener {
    public static String TAG = HomeActivity.class.getName();
    private BottomNavigationBar mNavigationBar;
    private volatile Fragment mCurrentFragment;
    private static final String STATE_CURRENT_FRAGMENT_TAG = "state_current_fragment_tag"; // HomeActivity被回收重启后，用于恢复当前Fragment的标志
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initDrawer();
        initToolbar();
        initNavigationBarAndFragment(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // HomeActivity被回收重启，保存当前显示的Fragment tag
        outState.putString(STATE_CURRENT_FRAGMENT_TAG, mCurrentFragment.getTag());
        super.onSaveInstanceState(outState);
    }

    private void initUI(){
        ZStatusbarUtils.setStatusBarTextColor(getWindow(),true);
        mNavigationBar = findViewById(R.id.nvb_main_navigation_bar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
    }

    private void initToolbar(){
        ImageView menu =  (ImageView)getToolbarReturn();
        menu.setImageResource(R.mipmap.ic_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag() != null && view.getTag().equals("open")){
                    mDrawerLayout.closeDrawer(GravityCompat.START | Gravity.LEFT);
                    view.setTag("");
                    return;
                }
                view.setTag("open");
                mDrawerLayout.openDrawer(GravityCompat.START | Gravity.LEFT);
            }
        });
    }

    private void initNavigationBarAndFragment(Bundle savedInstanceState) {
        final Fragment gameFragment = new HomeGameFragment();
        final Fragment coinFragment = new HomeCoinFragment();
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
                    case 0:
                        switchFragment(mCurrentFragment,gameFragment);
                        break;
                    case 1:
                        switchFragment(mCurrentFragment,coinFragment);
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

        mCurrentFragment = gameFragment;
        // HomeActivity重启时，Fragment会被保存恢复，而此时再加载Fragment会重复加载，导致重叠。
        if (savedInstanceState == null) {
            // 正常情况下去加载根Fragment
            if (!gameFragment.isAdded()) {
                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fl_main_container, gameFragment, gameFragment.getClass().getSimpleName())
                        .commitNow();
            }
        } else {
            // HomeActivity被回收重启，需要恢复当前显示的Fragment
            String currentFragmentTab = savedInstanceState.getString(STATE_CURRENT_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(currentFragmentTab)) {
                Fragment targetFragment = getSupportFragmentManager().findFragmentByTag(currentFragmentTab);
                if (targetFragment != null) {
                    mCurrentFragment = targetFragment;
                }
            }
        }
    }

    private void switchFragment(String toFragmentTag) {
        Fragment toFragment =  getSupportFragmentManager().findFragmentByTag(toFragmentTag);
        switchFragment(mCurrentFragment, toFragment);

        mNavigationBar.selectTab((HomeSupplement.getTabPositionByFragmentTag(toFragmentTag)));
    }

    private void switchFragment(final Fragment from, final Fragment to) {
        if (from == null || to == null) {
            return;
        }

        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String toFragmentTag = to.getClass().getSimpleName();
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(R.id.fl_main_container, to, toFragmentTag);
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


    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    private void initDrawer() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DrawerFragment fragment = new DrawerFragment();
        transaction.add(R.id.ll_drawer, fragment);
        try {
            transaction.commitNow();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
