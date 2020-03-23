package cn.zhenye.common.tbad.response;

import com.google.gson.annotations.SerializedName;

public class TbkTaokoulingResponse {

    @SerializedName("tbk_tpwd_create_response")
    public Response response;

    public String getResult() {
        if (response == null || response.data == null) {
            return null;
        }
        return response.data.taokouling;
    };

    public class Response {
        @SerializedName("data")
        public Data data;
    }

    public class Data {
        @SerializedName("model")
        public String taokouling;

        @SerializedName("request_id")
        public String requestId;
    }

}
