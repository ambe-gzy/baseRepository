package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TbkItemDetailResponse {

    @SerializedName("tbk_item_info_get_response")
    public Response response;

    public List<String> getPics() {
        if (response == null || response.result == null ||
                response.result.item == null || response.result.item.get(0) == null ||
                response.result.item.get(0).small_images == null) {
            return null;
        }
        return response.result.item.get(0).small_images.smallPic;
    }

    public class Response {
        @SerializedName("results")
        public Result result;
    }

    public class Result {
        @SerializedName("n_tbk_item")
        public List<Item> item;
    }

    public class Item {

        @SerializedName("zk_final_price")
        public String final_price;

        @SerializedName("title")
        public String title;

        @SerializedName("small_images")
        public SmallPic small_images;

        @SerializedName("reserve_price")
        public String reserve_price;
    }

    public class SmallPic {
        @SerializedName("string")
        public List<String> smallPic;
    }
}
