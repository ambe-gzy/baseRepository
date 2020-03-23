package cn.zhenye.common.ad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZAdResponse {

    @SerializedName("datas")
    public List<Item> items;

    public static class Item{
        @SerializedName("goods_id")
        public String goodsId;

        @SerializedName("goods_name")
        public String goodsName;

        @SerializedName("goods_pic")
        public String goodsPic;

        @SerializedName("goods_price")
        public String goods_price;

        @SerializedName("goods_taokouling")
        public String goods_taokouling;

        @SerializedName("goods_youhuiquan")
        public String goods_youhuiquan;

        @SerializedName("goods_discount")
        public String goods_discount;
    }
}
