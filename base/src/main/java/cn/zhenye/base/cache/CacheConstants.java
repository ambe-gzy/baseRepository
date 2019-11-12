package cn.zhenye.base.cache;

/**
 * @author zhaibinme on 2018/7/21
 */
public class CacheConstants {

    public static final String BASE_STORAGE_CONFIG = "base_storage_";

    public static final String COMMON_STORAGE_CONFIG = "base_storage_common";

    public static String parserKey(String value) {
        return BASE_STORAGE_CONFIG + value;
    }

    public static String KET_SPACE_SP_IMG_CACHE = "sp_key_common_img_cache_";
}
