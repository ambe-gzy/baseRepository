package cn.zhenye.baichuan.base;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

public class BaiChuanSupplement {
    public static void initBaichuanSdk(final Application application){
        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
//                Toast.makeText(application, "初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
//                Toast.makeText(application, "初始化失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
