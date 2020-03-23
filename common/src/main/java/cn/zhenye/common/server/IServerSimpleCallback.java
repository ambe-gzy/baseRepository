package cn.zhenye.common.server;

/**
 * Created by augustdong on 17/6/22.
 */

public interface IServerSimpleCallback {

    void onSuccess();
    void onFail();

    void onRetry();

}
