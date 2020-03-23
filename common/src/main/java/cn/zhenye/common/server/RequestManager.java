package cn.zhenye.common.server;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import cn.zhenye.base.server.HttpClient;
import cn.zhenye.base.server.HttpClientConfig;
import cn.zhenye.base.task.OnCompleteListener;
import cn.zhenye.base.tool.NetworkUtil;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.common.constants.ServerConfigConstants;
import cn.zhenye.common.constants.ServerResultConstants;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * 网络请求管理
 *
 * @author zhaibinme on 2018/8/7
 */
public class RequestManager {

    private static RequestManager mRequestManager = null;

    private HttpClient mHttpClient = null;

    private RequestManager() {
        initHttpClient();
    }

    private void initHttpClient() {
        HttpClientConfig httpClientConfig = new HttpClientConfig();
        httpClientConfig.connectTimeoutMilliseconds = ServerConfigConstants.SERVER_HTTP_CONNECT_TIMEOUT_MILLISECONDS;
        httpClientConfig.readTimeoutMilliseconds = ServerConfigConstants.SERVER_HTTP_READ_TIMEOUT_MILLISECONDS;
        httpClientConfig.writeTimeoutMilliseconds = ServerConfigConstants.SERVER_HTTP_WRITE_TIMEOUT_MILLISECONDS;
        httpClientConfig.dns = null;
        mHttpClient = new HttpClient(httpClientConfig);
    }

    @NonNull
    public static synchronized RequestManager getInstance() {
        if (mRequestManager == null) {
            mRequestManager = new RequestManager();
        }
        return mRequestManager;
    }

    public <TResult> void doHttpPost(@NonNull final Context context, @NonNull final String url, @NonNull final BaseRequest request,
                                     @NonNull final OnCompleteListener<TResult> onCompleteListener) {
        final Context applicationContext = context.getApplicationContext();
        ZThreadManager.getHttp().execute(new Runnable() {
            @Override
            public void run() {
                if (!NetworkUtil.isNetworkConnected(applicationContext)) {
                    notifyErrorRequest(applicationContext, onCompleteListener, ServerResultConstants.ERROR_STATE_NETWORK_UNAVAILABLE);
                    return;
                }

                HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
                Map<String, String> headers = new HashMap<>();

                //公共参数
//                request.setDid(DeviceUtil.getUniqueID(applicationContext));
//                request.setCnl(getChannel());
//                request.setMcc(DeviceUtil.getMcc(applicationContext));
//                request.setMnc(DeviceUtil.getMnc(applicationContext));
//                request.setLang(DeviceUtil.getOSRegionCodeLang(applicationContext));
//                request.setCv(APPConstants.VERSION_NAME);
//                request.setTs(DeviceUtil.getTsForPython());
//                request.setPkg(APPConstants.APP_PACKAGE_NAME);
//                request.setIsRooted(DeviceUtil.isRooted(applicationContext));
//                request.setIsVPNUsed(NetworkUtil.isVpnUsed());

//                if (AuthManager.getInstance().getUser() != null) {
//                    request.setUid(AuthManager.getInstance().getUser().getUid());
//                    request.setToken(AuthManager.getInstance().getUser().getToken());
//                }
                String requestWithJsonStr = new GsonBuilder().create().toJson(request);

                // authorization
//                String authorization = HmacMD5.encode(requestWithJsonStr, ServerConfigConstants.AUTHORIZATION_HMAC_MD5_KEY);
//                if (!TextUtils.isEmpty(authorization)) {
//                    headers.put(HttpUrlConstants.HEADER_COMMON_AUTH, authorization);
//                }

                // iv
//                String iv = HexUtils.generateRandomHexString(16);
//                headers.put(HttpUrlConstants.HEADER_YOLO_AUTH, iv);

                // call
//                Call call = mHttpClient.newCallWithPost(
//                        httpUrlBuilder.build().toString(),
//                        headers,
//                        SecurityUtils.encrypt(mAppContext, requestWithJsonStr, iv));

                Call call = mHttpClient.newCallWithPost(
                        httpUrlBuilder.build().toString(),
                        headers,
                        requestWithJsonStr);

                try {
                    //同步请求，会阻塞主线程
                    Response response = call.execute();
                    ServerCallbackHelper.process(applicationContext, response, onCompleteListener);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (e.getClass().equals(SocketTimeoutException.class)) {
                        notifyErrorRequest(applicationContext, onCompleteListener, ServerResultConstants.ERROR_REQUEST_TIME_OUT);
                    } else {
                        notifyErrorRequest(applicationContext, onCompleteListener, ServerResultConstants.ERROR_REQUEST_EXCEPTION);
                    }
                }
            }
        });
    }

//    public <TResult> void doHttpGet(@NonNull Context context, @NonNull final String url,
//                                    @NonNull final OnCompleteListener<TResult> onCompleteListener) {
//        doHttpGet(context, url, null, onCompleteListener);
//    }

//    public <TResult> void doHttpGet(@NonNull Context context, @NonNull final String url,
//                                    @NonNull final Map<String, String> bizParams,
//                                    @NonNull final OnCompleteListener<TResult> onCompleteListener) {
//        Context application = context.getApplicationContext();
//
//        ThreadManager.getHttp().execute(new Runnable() {
//            @Override
//            public void run() {
//                if (!NetworkUtil.isNetworkConnected(application)) {
//                    notifyErrorRequest(application, onCompleteListener, ServerResultConstants.ERROR_STATE_NETWORK_UNAVAILABLE);
//                    return;
//                }
//
//                HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
//
//                httpUrlBuilder.addEncodedQueryParameter("cnl", getChannel());
//                httpUrlBuilder.addEncodedQueryParameter("mcc", DeviceUtil.getMcc(application));
//                httpUrlBuilder.addEncodedQueryParameter("mnc", DeviceUtil.getMnc(application));
//                httpUrlBuilder.addEncodedQueryParameter("lang", DeviceUtil.getOSRegionCodeLang(application));
//                httpUrlBuilder.addEncodedQueryParameter("cv", APPConstants.VERSION_NAME);
//                httpUrlBuilder.addEncodedQueryParameter("pkg", APPConstants.APP_PACKAGE_NAME);
////                httpUrlBuilder.addEncodedQueryParameter("enc", "1");
////                httpUrlBuilder.addEncodedQueryParameter(HttpUrlConstants.HEADER_YOLO_AUTH, HexUtils.generateRandomHexString(16));
//
//                if (bizParams != null) {
//                    for (Map.Entry<String, String> entry : bizParams.entrySet()) {
//                        httpUrlBuilder.addEncodedQueryParameter(entry.getKey(), entry.getValue());
//                    }
//                }
//
//                final Call call = mHttpClient.newCallWithGet(
//                        httpUrlBuilder.build().toString(),
//                        null);
//
//                try {
//                    Response response = call.execute();
//                    ServerCallbackHelper.process(application, response, onCompleteListener);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    notifyErrorRequest(application, onCompleteListener, ServerResultConstants.ERROR_REQUEST_EXCEPTION);
//                }
//            }
//        });
//    }

    private <TResult> void notifyErrorRequest(@NonNull Context context, @NonNull final OnCompleteListener<TResult> onCompleteListener, int errorCode) {
        RequestTask<TResult> requestTask = new RequestTask<>();
//        requestTask.setErrorCode(errorCode);
        ServerCallbackHelper.notifyComplete(context, requestTask, onCompleteListener);
    }

    /**
     * 获取渠道号
     * @return
     */
//    private String getChannel() {
//        AuthUser user = AuthManager.getInstance().getUser();
//        if (user == null) {
//            return APPConstants.CNL;
//        }
//
//        if (user.getUserProfile() == null) {
//            return APPConstants.CNL;
//        }
//
//        if (TextUtils.isEmpty(user.getUserProfile().getCnl())) {
//            return APPConstants.CNL;
//        }
//
//        return user.getUserProfile().getCnl();
//    }
}
