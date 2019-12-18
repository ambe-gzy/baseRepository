package cn.zhenye.base.tool;

import android.content.Context;

public class PathUtil {
    public static String getCacheDir(Context context){
        return context.getCacheDir().getPath();
    }

    public static String getExternalCacheDir(Context context) throws Exception {
        if (context.getExternalCacheDir()!=null){
            return context.getExternalCacheDir().getPath();
        }
        throw new Exception("can't get read external sd card");
    }
}
