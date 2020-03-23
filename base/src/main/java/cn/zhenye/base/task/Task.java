package cn.zhenye.base.task;

public abstract class Task<TResult> {

    public abstract TResult getResult();

    public abstract void setResult(TResult result);


}
