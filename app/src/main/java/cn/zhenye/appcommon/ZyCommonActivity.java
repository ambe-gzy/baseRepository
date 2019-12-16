package cn.zhenye.appcommon;

import com.umeng.analytics.MobclickAgent;

import cn.zhenye.base.activity.BaseFullScreenActivity;

/**
 * 在此配置基础方法
 */
public abstract class ZyCommonActivity extends BaseFullScreenActivity {

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }
}
