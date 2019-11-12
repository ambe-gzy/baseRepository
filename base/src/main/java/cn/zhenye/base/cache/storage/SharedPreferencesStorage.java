package cn.zhenye.base.cache.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.zhenye.base.cache.CacheConstants;

/**
 *
 * @author zhaibinme on 2018/7/21
 */
public class SharedPreferencesStorage {

    private static SharedPreferences getStorage(Context context, String key) {
        if (context == null) {
            return null;
        }

        if (TextUtils.isEmpty(key)) {
            return null;
        }

        String[] configs;
        configs = key.split("_");
        String configName;
        if (configs.length > 0) {
            configName = CacheConstants.BASE_STORAGE_CONFIG + configs[1];
        } else {
            configName = CacheConstants.COMMON_STORAGE_CONFIG;
        }
        return context.getSharedPreferences(configName, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context, String key) {
        return getStorage(context, key).edit();
    }

    public static <T> boolean put(Context context, String key, T value) {
        return getEditor(context, key).putString(key, String.valueOf(value)).commit();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Context context, String key) {
        return (T) getStorage(context, key).getString(key, null);
    }

    public static boolean delete(Context context, String key) {
        return getEditor(context, key).remove(key).commit();
    }

    public static boolean contains(Context context, String key) {
        return getStorage(context, key).contains(key);
    }

}
