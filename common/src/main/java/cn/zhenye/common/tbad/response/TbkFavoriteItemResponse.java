package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TbkFavoriteItemResponse implements TbkBaseResponse<List<TbkFavoriteItemResponse.ChildItem>> {
    @Override
    public List<ChildItem> getResult() {
        if (tbkFavoriteItemResponse == null || tbkFavoriteItemResponse.childResult == null) {
            return null;
        }
        return tbkFavoriteItemResponse.childResult.childItem;
    }

    @SerializedName("tbk_uatm_favorites_item_get_response")
    private TbkFavoriteItemResponse tbkFavoriteItemResponse;

    @SerializedName("results")
    private ChildResult childResult;

    public class ChildResult {
        @SerializedName("uatm_tbk_item")
        public List<ChildItem> childItem;
    }

    public class ChildItem {
        @SerializedName("num_iid")
        public String  numIid;

        @SerializedName("title")
        public String title;

        @SerializedName("pict_url")
        public String picUrl;

        //许多个小图地址组合成的一个字符串
        @SerializedName("small_images")
        public SmallPic small_pic_urls;

        @SerializedName("reserve_price")
        public String reversePrice;

        @SerializedName("zk_final_price")
        public String finalPrice;

        @SerializedName("coupon_click_url")
        public String couponClickUrl;

        @SerializedName("coupon_info")
        public String couponInfo;

        @SerializedName("coupon_start_time")
        public String couponStartTime;

        @SerializedName("coupon_end_time")
        public String couponEndTime;
    }

    public class SmallPic{
        @SerializedName("string")
        public List<String> smallPic;
    }
}
