package cn.zhenye.base.tool;

import android.os.Process;

import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;

/**
 * @author WilliamChik on 2018/6/8.
 */
public final class ZThreadManager {

    private final static EasyThread normal;

    private final static EasyThread http;

    private final static EasyThread ads;

    public static EasyThread getNormal() {
        return normal;
    }

    public static EasyThread getHttp() {
        return http;
    }

    public static EasyThread getAds() {
        return ads;
    }

    static {
        normal = EasyThread.Builder.createFixed(6).setName("normal").setPriority(Thread.MAX_PRIORITY).setCallback(new DefaultCallback()).build();
        http = EasyThread.Builder.createFixed(6).setName("http").setPriority(Thread.MAX_PRIORITY).build();
        ads = EasyThread.Builder.createFixed(6).setName("ads").setPriority(Thread.MAX_PRIORITY).build();
    }

    private static class DefaultCallback implements Callback {

        @Override
        public void onError(String threadName, Throwable t) {
//            YoLog.e("Task with thread %s has occurs an error: %s", threadName, t.getMessage());
        }

        @Override
        public void onCompleted(String threadName) {
//            YoLog.d("Task with thread %s completed", threadName);
        }

        @Override
        public void onStart(String threadName) {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//            YoLog.d("Task with thread %s start running!", threadName);
        }
    }
}
