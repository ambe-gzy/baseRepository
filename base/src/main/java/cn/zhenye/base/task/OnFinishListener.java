package cn.zhenye.base.task;

/**
 * @author zhaibinme on 2018/8/24
 */
public interface OnFinishListener<TResult> {

    void onFinish(TResult... task);
}
