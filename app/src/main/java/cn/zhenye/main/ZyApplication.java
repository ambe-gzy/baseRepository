package cn.zhenye.main;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

import cn.zhenye.MainSupplement;
import cn.zhenye.common.constants.UMConstants;

public class ZyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainSupplement.init(getApplicationContext());
    }
}
