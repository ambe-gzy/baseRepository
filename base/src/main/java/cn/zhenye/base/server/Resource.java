package cn.zhenye.base.server;

public class Resource<D> {
    private String msg;
    private NetStatus status;
    private D data;

    public Resource() {
    }

    public Resource(String msg, NetStatus status, D data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public NetStatus getStatus() {
        return status;
    }

    public D getData() {
        return data;
    }

    /**
     * 请求前
     */
    public Resource loading(String msg) {
        return new Resource<D>(msg, NetStatus.LOADING, null);
    }

    /**
     * 请求成功
     */
    public Resource<D> success(D data) {
        return new Resource<>(null, NetStatus.SUCCESS, data);
    }

    /**
     * 请求失败
     */
    public Resource<D> error(String msg) {
        return new Resource<>(msg, NetStatus.ERROR, null);
    }

    /**
     * 数据为空
     */
    public Resource<D> empty(String msg) {
        return new Resource<>(msg, NetStatus.EMPTY, null);
    }
}
