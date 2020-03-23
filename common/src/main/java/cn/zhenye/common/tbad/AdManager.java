package cn.zhenye.common.tbad;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AdManager {
    private static String TAG = AdManager.class.getSimpleName();
    private static final AdManager INSTANCE = new AdManager();
    private boolean isInit = false;


    private AdManager(){ }

    public static AdManager getInstance(){
        return INSTANCE;
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

    /**
     * 获取选料库Id
     * @param pageNo
     * @return
     * @throws IOException
     */
    public String getTbFavorites(int pageNo) throws IOException{
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

    /**
     * 获取选料库详细商品
     * @param favoritesId
     * @return
     * @throws IOException
     */
    public String getTbkFavoriteItems (int favoritesId) throws IOException {
        Map<String, String> params = getCommonParams();
        //请求的接口
        params.put("method", TbConstants.FavouritesItem.API);
        // 业务参数
        params.put(TbConstants.FavouritesItem.REQUEST_AD_ZONE_ID,TbConstants.ADZONE_ID);
        params.put(TbConstants.FavouritesItem.REQUEST_FAVORITES_ID,String.valueOf(favoritesId));
        params.put(TbConstants.FavouritesItem.REQUEST_FIELDS,"num_iid,title,pict_url,small_images,reserve_price," +
                "zk_final_price,coupon_click_url,coupon_info,coupon_start_time,coupon_end_time");
        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }

    public String getTbkTaokoulingStrJson(String text,String url) throws IOException {
        Map<String, String> params = getCommonParams();
        //请求的接口
        params.put("method", TbConstants.Taokouling.API);
        // 业务参数
        params.put(TbConstants.Taokouling.TEXT,text);
        params.put(TbConstants.Taokouling.URL,url);
        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }

    public String getTbkItemDetailStrJson(String num_iid) throws IOException{
        Map<String, String> params = getCommonParams();
        //请求的接口
        params.put("method", TbConstants.ItemDetail.API);
        // 业务参数
        params.put(TbConstants.ItemDetail.NUM_IID,num_iid);
        // 签名参数
        params.put("sign", TbUtils.signTopRequest(params, TbConstants.APP_SECURE, TbConstants.SIGN_METHOD_HMAC));
        // 请用API
        return TbUtils.callApi(new URL(TbConstants.SERVER_URL), params);
    }
}
