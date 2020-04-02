package cn.zhenye.baichuan.base;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

public class BaiChuanManager {
    public static BaiChuanManager INSTANCE = new BaiChuanManager();

    public static BaiChuanManager getInstance() {
        return INSTANCE;
    }

    public void openTb(String goodId, final Activity activity){
        AlibcBasePage page = new AlibcDetailPage(goodId);
        //展示参数
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setClientType("taobao");
        showParams.setBackUrl("alisdk://");
        showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpBROWER);

        AlibcTaokeParams taokeParams = new AlibcTaokeParams("","","");
        taokeParams.setPid(BaiChuanConstants.PID);
        taokeParams.setAdzoneid(BaiChuanConstants.ADZONE_ID);

        Map<String,String> trackParams = new HashMap<>();
        trackParams.put("taokeAppkey", BaiChuanConstants.APP_KEY);
        trackParams.put("sellerId","");

        taokeParams.setExtraParams(trackParams);

        AlibcTrade.openByBizCode(activity, page, null,
                new WebViewClient(), new WebChromeClient(), "detail",
                showParams, taokeParams, trackParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

    }

}
