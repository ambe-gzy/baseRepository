package cn.zhenye.baichuan.base;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
        AlibcBasePage page = new AlibcDetailPage("599496118272");
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setClientType("taobao");
        showParams.setBackUrl("alisdk://");
        showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpBROWER);


        AlibcTaokeParams taokeParams = new AlibcTaokeParams("","","");
        taokeParams.setPid("mm_118744786_1281250035_109997750080");
        taokeParams.setAdzoneid("109997750080");
        taokeParams.extraParams.put("taokeAppkey","28304735");

        Map<String,String> trackParams = new HashMap<>();

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
