package cn.zhenye.common.tbad;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TbAdManager {
    private static String TAG = TbAdManager.class.getSimpleName();
    private static final TbAdManager INSTANCE = new TbAdManager();
    private boolean isInit = false;


    private TbAdManager(){ }

    public static TbAdManager getInstance(){
        return INSTANCE;
    }

    public String getSellerItem() throws IOException {
        Map<String, String> params = getCommonParams();

        //请求的接口
        params.put("method", TbConstants.FavouritesItem.API);
        // 业务参数
        params.put(TbConstants.FavouritesItem.REQUEST_AD_ZONE_ID,TbConstants.ADZONE_ID);
        params.put(TbConstants.FavouritesItem.REQUEST_FAVORITES_ID,TbConstants.PROMOTE_SCENE_ID);
        params.put(TbConstants.FavouritesItem.REQUEST_FIELDS,"num_iid,title,click_url");
        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }


    private Map<String, String> getCommonParams() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        //公共参数
        params.put("app_key", TbConstants.APP_KEY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("timestamp", df.format(new Date()));
        params.put("format", "json");
        params.put("v", "2.0");
        params.put("sign_method", "hmac");
        return params;
    }


    public String getMaterial() throws  IOException{
        Map<String, String> params = getCommonParams();
        //请求的接口
        params.put("method", TbConstants.Material.API);
        // 业务参数
        params.put(TbConstants.Material.REQUEST_AD_ZONE_ID,TbConstants.ADZONE_ID);
        params.put(TbConstants.Material.REQUEST_PAGE_SIZE,"2");

        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }



    public String getTbResponseStr (Map<String, String> businessParams) throws IOException{
        businessParams.putAll(getCommonParams());
        // 签名参数
        businessParams.put("sign", TbUtils.signTopRequest(businessParams, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), businessParams);
    }

    /**
     * 获取选料库Id
     * @param pageNo
     * @return
     * @throws IOException
     */
    public String getTbFavourites(int pageNo) throws IOException{
        Map<String, String> params = getCommonParams();
        //请求的接口
        params.put("method", TbConstants.Favourites.API);
        // 业务参数
        params.put(TbConstants.Favourites.REQUEST_FIELDS,"type,favorites_id,favorites_title");
        params.put(TbConstants.Favourites.REQUESR_PAGE_NO,String.valueOf(pageNo));

        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }


}
