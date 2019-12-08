package cn.zhenye.runfuns;

import android.app.Application;

import cn.zhenye.MainSupplement;

public class ZyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainSupplement.init(getApplicationContext());
    }
}
