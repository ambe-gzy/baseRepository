package cn.zhenye.base.tool;

import android.os.Looper;

/**
 * Created by augustdong on 17/6/13.
 */

public class ThreadUtil {

    public static void runOnMainThread(Runnable action) {
        if (isMainThread()) {
            action.run();
        } else {
            ZDaemons.getMainHandler().post(action);
        }
    }

    public static void delayRunOnMainThread(Runnable action, long delayMillis) {
        ZDaemons.getMainHandler().postDelayed(action, delayMillis);
    }

    public static boolean isMainThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return true;
        }

        return false;
    }

}
