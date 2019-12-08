package cn.zhenye;

import android.content.Context;

import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.common.db.DatabaseManager;

public class MainSupplement {

    public static void init(Context context){
        initCacheStore(context.getApplicationContext());
        initDatabase(context.getApplicationContext());
    }

    //初始化cacheStore
    private static void initCacheStore(Context context){
        ZyCacheStorage.init(context.getApplicationContext());
    }

    //初始化room database
    private static void initDatabase(Context context){
        DatabaseManager.init(context.getApplicationContext());
    }

}
