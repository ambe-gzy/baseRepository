package cn.zhenye.ad.vm;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.base.task.OnCompleteListener;
import cn.zhenye.base.task.Task;
import cn.zhenye.base.tool.ZGsonUtils;
import cn.zhenye.base.tool.ZTimeUtils;
import cn.zhenye.common.ad.DefaultZAdResponse;
import cn.zhenye.common.ad.response.ZAdResponse;
import cn.zhenye.common.constants.HttpUrlConstants;
import cn.zhenye.common.constants.ZAdConstants;
import cn.zhenye.common.server.BaseRequest;
import cn.zhenye.common.server.BaseResponse;
import cn.zhenye.common.server.RequestManager;

public class ZAdVMManager {

    private List<OnAdListener> mAdListener;

    private ZAdVMManager(){}

    private static final ZAdVMManager INSTANCE = new ZAdVMManager();

    public static ZAdVMManager getInstance() {
        return INSTANCE;
    }

    public void loadAd(Context context){

        RequestManager.getInstance().doHttpPost(context, HttpUrlConstants.AD_TBK, new BaseRequest(), new OnCompleteListener<BaseResponse>() {
            @Override
            public void onComplete(@NonNull Task<BaseResponse> task) {
                BaseResponse baseResponse = task.getResult();

                if (baseResponse == null) {
                    notifyAdCallBack(null);
                    return;
                }
                try {
                    Gson gson = new GsonBuilder().setLenient().create();

                    ZAdResponse zAdResponse = gson.fromJson(baseResponse.goods, ZAdResponse.class);

                    if (zAdResponse!= null && zAdResponse.items != null) {
                        ZyCacheStorage.put(ZAdConstants.AD_IS_SEC_DAY_TIMESTAMP, System.currentTimeMillis());
                        ZyCacheStorage.put(ZAdConstants.AD_LOCAL_RESPONSE, zAdResponse);
                        notifyAdCallBack(zAdResponse);
                    } else {
                        notifyAdCallBack(null);
                    }
                } catch (Exception e) {
                    notifyAdCallBack(null);
                    e.printStackTrace();
                }

            }
        });
    }

    public ZAdResponse getAdResponse(Context context) {
        long pastTimeStamp = ZyCacheStorage.getLong(ZAdConstants.AD_IS_SEC_DAY_TIMESTAMP, 0);
        if (!ZTimeUtils.isSameData(System.currentTimeMillis(), pastTimeStamp)) {
            loadAd(context);
        }

        ZAdResponse response = ZyCacheStorage.getData(ZAdConstants.AD_LOCAL_RESPONSE, null);
        if (response == null) {
            //读取默认广告
            String defaultResponseStr = new DefaultZAdResponse().DefaultResponse;
            try {
                response = ZGsonUtils.formJson(defaultResponseStr,ZAdResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public void addListener(OnAdListener listener){
        if (mAdListener == null) {
            mAdListener = new ArrayList<>();
        }
        mAdListener.add(listener);
    }

    public void removeListener(OnAdListener listener){
        if (mAdListener == null) {
            return;
        }
        mAdListener.remove(listener);
    }

    private void notifyAdCallBack(ZAdResponse response){
        if (mAdListener == null) {
            return;
        }
        for (int i = 0;i<mAdListener.size();i++) {
            mAdListener.get(i).onFinish(response);
        }
    }

    public interface OnAdListener {
        void onFinish(ZAdResponse response);
    }
}
