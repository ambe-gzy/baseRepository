package cn.zhenye.appcommon;

import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.base.tool.ZDaemons;
import cn.zhenye.home.HomeActivity;
import cn.zhenye.home.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 这是demo工程的入口Activity，在这里会首次调用广点通的SDK。
 *
 * 在调用SDK之前，如果您的App的targetSDKVersion >= 23，那么建议动态申请相关权限。
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ZDaemons.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchActivity();
            }
        },1000);
    }

    //闪屏页选择activity进入
    private void switchActivity(){
        ZActivityUtils.safeStartActivityWithIntentClass(getApplicationContext(), HomeActivity.class);
        finish();
    }

}
