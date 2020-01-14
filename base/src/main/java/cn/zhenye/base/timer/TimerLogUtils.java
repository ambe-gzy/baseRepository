package cn.zhenye.base.timer;

import android.util.Log;

/**
 * @author zhaibinme on 2018/8/25
 */
class TimerLogUtils {
    private static final String TAG = "CountDown";
//    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final boolean DEBUG = false;

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }
}
