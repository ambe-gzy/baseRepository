package cn.zhenye.base.server;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by augustdong on 17/6/12.
 */

public class HttpClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mOkHttpClient;

    public HttpClient(HttpClientConfig httpClientConfig) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (httpClientConfig != null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.json(LogUtils.W, "JSON", message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(httpClientConfig.connectTimeoutMilliseconds, TimeUnit.MILLISECONDS)
                    .readTimeout(httpClientConfig.readTimeoutMilliseconds, TimeUnit.MILLISECONDS)
                    .writeTimeout(httpClientConfig.writeTimeoutMilliseconds, TimeUnit.MILLISECONDS);

            if (httpClientConfig.dns != null) {
                okHttpClientBuilder.dns(httpClientConfig.dns);
            }
        }

        mOkHttpClient = okHttpClientBuilder.build();
    }

    public Call newCallWithPost(@NonNull String url, @Nullable Map<String, String> headers, @Nullable byte[] body) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, body == null ? new byte[0] : body));

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.addHeader(key, value);
            }
        }

        return mOkHttpClient.newCall(requestBuilder.build());
    }

    public Call newCallWithPost(@NonNull String url, @Nullable Map<String, String> headers, @Nullable String body) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, body == null ? "" : body));

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.addHeader(key, value);
            }
        }

        return mOkHttpClient.newCall(requestBuilder.build());
    }

    public Call newCallWithGet(String url, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestBuilder.addHeader(key, value);
            }
        }

        return mOkHttpClient.newCall(requestBuilder.build());
    }

}
