package cn.zhenye.common.tbad;

import android.app.Application;

//import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
//import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;


public class TbAdManager {
    private static String TAG = TbAdManager.class.getSimpleName();
    private static final TbAdManager INSTANCE = new TbAdManager();
    private boolean isInit = false;


    private TbAdManager(){ }

    public static TbAdManager getInstance(){
        return INSTANCE;
    }


    public void init(Application application){
//        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG,"success init");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Log.d(TAG,"fail init");
//            }
//        });

    }

}
