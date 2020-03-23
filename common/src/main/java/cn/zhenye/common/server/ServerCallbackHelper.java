package cn.zhenye.common.server;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.zhenye.base.task.OnCompleteListener;
import cn.zhenye.base.tool.ThreadUtil;
import okhttp3.Response;

/**
 * Created by augustdong on 17/6/12.
 */

public class ServerCallbackHelper {

    public static <TResult> void process(@NonNull Context context, Response response, OnCompleteListener<TResult> onCompleteListener) {
        RequestTask<TResult> requestTask  = new RequestTask<TResult>();

        if (response == null) {
//            requestTask.setErrorCode(ServerResultConstants.ERROR_RESPONSE_NULL);
        } else if (response.isSuccessful()) {
            try {
                if (response.body() == null) {
//                    requestTask.setErrorCode(ServerResultConstants.ERROR_RESPONSE_NULL);
                } else {
//                    String iv = response.header(HttpUrlConstants.HEADER_YOLO_AUTH);
//                    if (TextUtils.isEmpty(iv)) {
//                        requestTask.setErrorCode(ServerResultConstants.ERROR_RESPONSE_DECRYPT_ERROR);
//                    } else {
//                        String responseStr = SecurityUtils.decrypt(context, response.body().bytes(), iv);
                        String responseStr = response.body().string();
                        if (TextUtils.isEmpty(responseStr)) {
//                            requestTask.setErrorCode(ServerResultConstants.ERROR_RESPONSE_DECRYPT_ERROR);
                        } else {
                            if (onCompleteListener == null) {
                                return;
                            }
                            Type[] types = onCompleteListener.getClass().getGenericInterfaces();
                            Type[] params = ((ParameterizedType) types[0]).getActualTypeArguments();
                            Class<TResult> resultClass = (Class) params[0];
                            RequestTask<TResult> requestTaskTmp = new Gson().fromJson(responseStr, TypeToken.getParameterized(requestTask.getClass(), resultClass).getType());
                            if (requestTaskTmp == null) {
//                                requestTask.setErrorCode(ServerResultConstants.ERROR_RESPONSE_PARSED_NULL);
                            } else {
                                requestTask = requestTaskTmp;
                            }
                        }
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                requestTask.setErrorCode(ServerResultConstants.ERROR_STATE_JSON_PARSED_EXCEPTION);
            }
        } else {
//            requestTask.setErrorCode(response.code());
//            requestTask.setErrorMsg(response.message());
        }

        notifyComplete(context, requestTask, onCompleteListener);
    }

    public static <TResult> void notifyComplete(@NonNull final Context context, final RequestTask<TResult> requestTask, final OnCompleteListener<TResult> onCompleteListener) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
//                switch (requestTask.getErrorCode()) {
//                    case ServerResultConstants.ERROR_REQUEST_EXCEPTION:
//                    case ServerResultConstants.ERROR_CONTEXT_NULL:
//                    case ServerResultConstants.ERROR_RESPONSE_NULL:
//                    case ServerResultConstants.ERROR_RESPONSE_DECRYPT_ERROR:
//                    case ServerResultConstants.ERROR_RESPONSE_PARSED_NULL:
//                    case ServerResultConstants.ERROR_STATE_JSON_PARSED_EXCEPTION:
////                        ToastImgWithText.showLong(context, false, R.string.unknown_error);
//                        break;
//                    case ServerResultConstants.ERROR_STATE_NETWORK_UNAVAILABLE:
//                        NetworkUtil.showNetworkUnavailableToast(context);
//                        break;
//                    case ServerResultConstants.ERROR_UID:
////                        ToastImgWithText.showLong(context, false, R.string.identify_error);
////                        AuthManager.getInstance(context).signOut();
//                        break;
//                    case ServerResultConstants.ERROR_TOKEN:
//                    case ServerResultConstants.EXPIRED_TOKEN:
////                        ToastImgWithText.showLong(context, false, R.string.token_expired);
////                        AuthManager.getInstance(context).signOut();
//                        break;
//                    case ServerResultConstants.FORBIDDEN_USER_ID:
////                        ToastImgWithText.showLong(context, false, R.string.forbidden_user);
////                        AuthManager.getInstance(context).signOut();
//                        break;
//                    case ServerResultConstants.ERROR_DATABASE:
////                        ToastImgWithText.showLong(context, false, requestTask.getMsg());
//                        break;
//                    case ServerResultConstants.ERROR_ACTIVITY_TEMPORARILY_INVALID:
////                        ToastImgWithText.showLong(context, false, R.string.invalid_account_remind_message);
////                        AuthManager.getInstance(context).signOut();
//                        break;
//                    default:
//                        break;
//                }

//                if (!ServerResultConstants.isHandledError(requestTask.getErrorCode()) && !TextUtils.isEmpty(requestTask.getMsg())) {
////                    ToastImgWithText.showLong(context, false ,requestTask.getMsg());
//                }

                if (onCompleteListener != null) {
                    onCompleteListener.onComplete(requestTask);
                }
            }
        });
    }

}
