package cn.zhenye.common.tbad;

public class TbConstants {

    public static final String APP_KEY = "28304735"; // 可替换为您的沙箱环境应用的appKey
    public static final String APP_SECURE = "98b1075fa4cdee98a8646803e1f2b246"; // 可替换为您的沙箱环境应用的appSecret
    public static final String SERVER_URL = "http://gw.api.taobao.com/router/rest";// TOP服务地址，正式环境需要设置为http://gw.api.taobao.com/router/rest

    //广告位id
    public static final String ADZONE_ID = "109997750080";
    public static final String PROMOTE_SCENE_ID = "20170841";

    public static final String SESSION_KEY = ""; // 必须替换为沙箱账号授权得到的真实有效sessionKey

    public static final String SIGN_METHOD_MD5 = "md5";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CONTENT_ENCODING_GZIP = "gzip";

    /**
     * 淘宝联盟官方活动推广API-媒体，从官方活动列表页取出“官方活动ID”，支持二方、三方
     */
    public static class ActivityLinkGet {
        public static final String API = "taobao.tbk.activitylink.get";
        public static final String REQUEST_AD_ZONE_ID = "adzone_id";
        public static final String REQUEST_AD_PROMOTION_SCENE_ID = "promotion_scene_id";
    }

    /**
     * 选品库内的商品
     */
    public static class FavouritesItem {
        public static final String API = "taobao.tbk.uatm.favorites.item.get";
        public static final String REQUEST_AD_ZONE_ID = "adzone_id";
        public static final String REQUEST_FAVORITES_ID = "favorites_id";
        public static final String REQUEST_FIELDS = "fields";
    }

    /**
     * 选品库
     */
    public static class Favourites {
        public static final String API = "taobao.tbk.uatm.favorites.get";
        public static final String REQUEST_FIELDS = "fields";
        public static final String REQUESR_PAGE_NO = "page_no";
    }

    /**
     * 物料精选id
     */
    public static class Material {
        public static final String API = "taobao.tbk.dg.optimus.material";
        public static final String REQUEST_AD_ZONE_ID = "adzone_id";
        public static final String REQUEST_PAGE_SIZE = "page_size";
    }

    /**
     * 搜索
     */
    public static class Search {
        public static final String API = "taobao.tbk.dg.material.optional";
        public static final String REQUEST_AD_ZONE_ID = "adzone_id";
        public static final String REQUEST_Q = "q";

    }

    /**
     * 淘口令
     */
    public static class Taokouling {
        public static final String API = "taobao.tbk.tpwd.create";
        public static final String TEXT = "text";
        public static final String URL = "url";
    }

    public static class ItemDetail {
        public static final String API = "taobao.tbk.item.info.get";
        public static final String NUM_IID = "num_iids";
    }

}
