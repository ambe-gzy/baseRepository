package cn.zhenye.base.tool;

import android.os.Handler;
import android.os.Looper;

/**
 * @author zhenye on 20191209
 * 获取handler
 */
public class Daemon {
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    public static synchronized Handler getMainHandler() {
        return sMainHandler;
    }
}