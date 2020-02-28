package cn.zhenye;

import android.app.Application;
import android.content.Context;

import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.common.constants.UMConstants;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.tbad.TbAdManager;
import cn.zhenye.home.BuildConfig;

public class AppSupplement {

    public static void init(Context context){
        initCacheStore(context.getApplicationContext());
        initDatabase(context.getApplicationContext());
        //友盟
        AppSupplement.initUM(context
        );
    }

    //初始化cacheStore
    private static void initCacheStore(Context context){
        ZyCacheStorage.init(context.getApplicationContext());
    }

    //初始化room database
    private static void initDatabase(Context context){
        DatabaseManager.init(context.getApplicationContext());
    }

    /**
     * 初始化友盟接口
     * @param context 上下文
     *
     */
    private static void initUM(Context context){
        //初始化sdk
        String channel = BuildConfig.DEBUG?UMConstants.TEST_CHANNEL:WalleChannelReader.getChannel(context.getApplicationContext());
        UMConfigure.init(context, UMConstants.UM_APP_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        //打开友盟log输出日志
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
    }

    /**
     * 初始化淘宝广告
     * @param application
     */
    public static void initTbAd(Application application){
        TbAdManager.getInstance().init(application);
    }
}
