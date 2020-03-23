package cn.zhenye.common.server;


import cn.zhenye.base.tool.ThreadUtil;

/**
 * Created by augustdong on 17/6/22.
 */

public class ServerSimpleCallbackHelper {

    public static void notifyResult(final IServerSimpleCallback serverSimpleCallback, final boolean isSuccessful) {
        if (serverSimpleCallback == null) {
            return;
        }

        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccessful) {
                    serverSimpleCallback.onSuccess();
                } else {
                    serverSimpleCallback.onFail();
                }
            }
        });
    }

    public static void notifyRetry(final IServerSimpleCallback serverSimpleCallback) {
        if (serverSimpleCallback == null) {
            return;
        }

        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                serverSimpleCallback.onRetry();
            }
        });
    }

}
