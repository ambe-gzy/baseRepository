package cn.zhenye.appcommon;

import com.umeng.analytics.MobclickAgent;

import cn.zhenye.base.base.BaseFullScreenActivity;

/**
 * 在此配置基础方法
 */
public abstract class ZyCommonActivity extends BaseFullScreenActivity {

    @Override
    protected void onResume() {
        MobclickAgent.onResume(getApplicationContext());
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(getApplicationContext());
        super.onPause();
    }
}
