package cn.zhenye;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.common.constants.UMConstants;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.main.BuildConfig;

public class MainSupplement {

    public static void init(Context context){
        initCacheStore(context.getApplicationContext());
        initDatabase(context.getApplicationContext());
        //友盟
        MainSupplement.initUM(context,
                UMConstants.UM_APP_KEY, UMConstants.TEST_CHANNEL
                ,UMConfigure.DEVICE_TYPE_PHONE,null);
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
     * @param appkey appid
     * @param channel 渠道
     * @param deviceType {UMConfigure}
     * @param pushSecret 输入null即可
     */
    private static void initUM(Context context, String appkey,
                              String channel, int deviceType, String pushSecret){
        //初始化sdk
        UMConfigure.init(context, appkey, channel, deviceType, pushSecret);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);
        //打开友盟log输出日志
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
    }
}
