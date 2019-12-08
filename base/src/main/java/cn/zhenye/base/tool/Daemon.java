package cn.zhenye.base.tool;

import android.os.Handler;
import android.os.Looper;

public class Daemon {
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    public static synchronized Handler getMainHandler() {
        return sMainHandler;
    }
}