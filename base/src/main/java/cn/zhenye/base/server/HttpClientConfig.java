package cn.zhenye.base.server;

import okhttp3.Dns;

/**
 * Created by augustdong on 17/6/12.
 */

public class HttpClientConfig {

    public long connectTimeoutMilliseconds = 10L * 1000L;
    public long readTimeoutMilliseconds = 30L * 1000L;
    public long writeTimeoutMilliseconds = 30L * 1000L;
    public Dns dns = null;

    public HttpClientConfig() {

    }

}
