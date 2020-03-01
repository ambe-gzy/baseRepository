package cn.zhenye.base.tool;

import com.blankj.utilcode.util.GsonUtils;

public class ZGsonUtils {

    public static  <T>T formJson(String jsonStr,Class<T> tClass) {
        return GsonUtils.fromJson(jsonStr,tClass);
    }

}
