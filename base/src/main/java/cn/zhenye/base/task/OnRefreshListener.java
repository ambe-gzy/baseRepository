package cn.zhenye.base.task;

import androidx.annotation.NonNull;

/**
 * @author zhaibinme on 2018/8/24
 */
public interface OnRefreshListener<TResult> {

    void onRefresh(@NonNull TResult task);
}
