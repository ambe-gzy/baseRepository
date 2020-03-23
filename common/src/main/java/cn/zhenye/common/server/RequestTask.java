package cn.zhenye.common.server;

import com.google.gson.annotations.SerializedName;

import cn.zhenye.base.task.Task;

public class RequestTask<TResult> extends Task<TResult> {

    @SerializedName("data")
    public TResult mResult = null;

    @Override
    public TResult getResult() {
        return mResult;
    }

    @Override
    public void setResult(TResult mResult) {
        this.mResult = mResult;
    }



}
