package cn.zhenye.base.task;

import androidx.annotation.NonNull;

public interface OnCompleteListener<TResult> {

    void onComplete(@NonNull Task<TResult> task);

}
