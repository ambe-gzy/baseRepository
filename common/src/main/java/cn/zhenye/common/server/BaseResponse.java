package cn.zhenye.common.server;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("msg")
    public String goods;

    @SerializedName("code")
    public int code;
}
