package cn.zhenye.appcommon;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.base.tool.Daemon;
import cn.zhenye.runfuns.MainActivity;
import cn.zhenye.runfuns.R;

import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Daemon.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchActivity();
            }
        },1000);

    }

    //闪屏页选择activity进入
    private void switchActivity(){
        ActivityUtil.safeStartActivityWithIntentClass(getApplicationContext(), MainActivity.class);
    }
}
