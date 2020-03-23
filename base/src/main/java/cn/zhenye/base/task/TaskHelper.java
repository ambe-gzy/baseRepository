package cn.zhenye.base.task;

import androidx.annotation.NonNull;

import cn.zhenye.base.tool.ThreadUtil;

public class TaskHelper {

    public static <TResult> void notifyOnCompleteOnMainThread(@NonNull final Task<TResult> task, @NonNull final OnCompleteListener<TResult> onCompleteListener) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onCompleteListener.onComplete(task);
            }
        });
    }

}
