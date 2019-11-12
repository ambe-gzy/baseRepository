package cn.zhenye.base.cache;

import android.content.Context;

import cn.zhenye.base.cache.storage.FileStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zhaibinme on 2018/7/21
 */
public class ZyCacheStorage {

    private static Cache mCache = null;

    public static void init(Context context) {
        CacheBuilder cacheBuilder = new CacheBuilder(context);
        mCache = new DefaultCache(cacheBuilder);
        FileStorage.init(context);
    }

    public static <T> boolean put(String key, @NonNull T value) {
        return put(key, value, false);
    }

    public static <T> boolean put(String key, @NonNull T value, boolean needEncrypt) {
        if (mCache == null) {
            return false;
        }
        return mCache.put(key, value, needEncrypt);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Boolean value = get(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public static long getLong(String key, @Nullable long defaultValue) {
        Long value = (Long) get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static int getInt(String key, @NonNull int defaultValue) {
        Integer value = (Integer) get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static String getString(String key, @NonNull String defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static Double getDouble(String key, @NonNull double defaultValue) {
        Double value = get(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public static Float getFloat(String key, @NonNull float defaultValue) {
        Float value = get(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    private static <T> T get(String key) {
        if (mCache == null) {
            return null;
        }
        T t = (T) mCache.get(key);
        return t;
    }

    /**
     * 支持对象，数组，set，map类型
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getData(String key, T defaultValue) {
        if (mCache == null) {
            return defaultValue;
        }
        return mCache.get(key, defaultValue);
    }

    public static boolean delete(String key) {
        if (mCache == null) {
            return false;
        }
        return mCache.delete(key);
    }

    public static boolean contains(String key) {
        if (mCache == null) {
            return false;
        }
        return mCache.contains(key);
    }

    public static  <T> String toEncrypt(T value) {
        if (!(mCache instanceof DefaultCache)) {
            return null;
        }

        return ((DefaultCache)mCache).toEncrypt( value);
    }

    public static <T> T getValueByDecrypt(String value, Class classValue) {
        if (!(mCache instanceof DefaultCache)) {
            return null;
        }

        return ((DefaultCache)mCache).getValueByDecrypt(value, classValue);
    }

}
