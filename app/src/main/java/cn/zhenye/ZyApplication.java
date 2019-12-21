package cn.zhenye;

import android.app.Application;

public class ZyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppSupplement.init(getApplicationContext());
    }
}
