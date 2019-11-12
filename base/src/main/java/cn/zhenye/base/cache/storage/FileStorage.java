package cn.zhenye.base.cache.storage;

import android.content.Context;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import cn.zhenye.base.cache.storage.file.FileCache;
import cn.zhenye.base.cache.storage.file.DiskFileUtils;

import java.io.File;

import androidx.annotation.NonNull;

/**
 * 缓存模块，基于 FileCache 再做一层包装，采用硬件缓存的方式进行存储，代替SharedPreferences处理所有和 app 持久化相关的缓存
 *
 * @author WilliamChik on 2015/8/21
 * @author modifed by zhaibinme on 2018/7/29
 */
public class FileStorage {

    private static final String TAG = FileStorage.class.getSimpleName();

    /**
     * STABLE最小的缓存文件大小
     */
    private static final long STABLE_CACHE_FILE_SIZE = 8 * 1024 * 1024;

    /**
     * STABLE缓存文件大小FACTOR
     */
    private static final int STABLE_CACHE_FILE_FACTOR = 2;

    /**
     * STABLE缓存文件名
     */
    private static final String STABLE_CACHE_FILE_NAME = "persistance_cache";

    /**
     * FileCache 对象，属于硬件缓存
     */
    private static FileCache mFileCache;

    public static void init(Context context) {
        initPersistenceCache(context, STABLE_CACHE_FILE_NAME, STABLE_CACHE_FILE_FACTOR, STABLE_CACHE_FILE_SIZE);
    }

    /**
     * 根据缓存文件的路径和大小参数，初始化持久化缓存
     * 优先使用 External Storage，当 External Storage 无法使用时，使用 internal cache dir
     *
     * @param context    context
     * @param fileName   缓存文件名
     * @param sizeFactor 缓存大小因子，代表每一个G的文件系统，使用多少M来作为缓存；例如 factor 为5，如果 external storage 总容量为8G,则使用 8*5=40M 的大小作为缓存
     * @param minSize    最小的缓存大小，如果计算出来的默认 size 小于这个值，则以这个值作为缓存大小
     */
    private static void initPersistenceCache(Context context, String fileName, int sizeFactor, long minSize) {

        // external cache
        try {
            String state = android.os.Environment.getExternalStorageState();
            // 判断SD卡是否存在，并且是否具有读写权限
            if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
                initCache(DiskFileUtils.getExternalCacheDir(context), fileName, sizeFactor, minSize);
            }
        } catch (Exception e) {
            Log.e(TAG, "getData external cache dir error ", e);
        }

        // internal cache
        try {
            if (context.getCacheDir() != null) {
                // 内部缓存容量减半
                initCache(context.getCacheDir(), fileName, sizeFactor / 2, minSize / 2);
            }
        } catch (Exception e) {
            Log.e(TAG, "getData internal cache dir error ", e);
        }

    }

    /**
     * 根据缓存文件的路径和大小参数，初始化持久化缓存
     *
     * @param fileDir    sd上的缓存目录，正常返回的情况下是 : /storage/sdcard0/Android/data/com.srain.sdk/cache
     * @param fileName   缓存文件名
     * @param sizeFactor 缓存大小因子，代表每一个G的文件系统，使用多少M来作为缓存；例如 factor 为5，如果 external storage 总容量为8G,则使用 8*5=40M 的大小作为缓存
     * @param minSize    最小的缓存大小，如果计算出来的缓存 size 小于这个值，则以这个值作为缓存大小
     */
    private static void initCache(File fileDir, String fileName, int sizeFactor, long minSize) {
        //计算存储区域大小
        long size = 0;

        StatFs stat = new StatFs(fileDir.getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            size = stat.getBlockSizeLong() * stat.getBlockCountLong();
        } else {
            size = (long) stat.getBlockSize() * (long) stat.getBlockCount();
        }

        size = (size * sizeFactor) / 1024;

        // 最小缓存空间检查
        if (size < minSize) {
            size = minSize;
        }

        //存储文件path
        String path = fileDir + File.separator + fileName;

        Log.i(TAG, "cache file parameters - size: " + size + " path: " + path);

        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("maxSize exceeds the FileCache size limit!");
        }

        initCache(path, size);
    }

    /**
     * 初始化ACache
     *
     * @param cacheFilePath 缓存文件 Path
     * @param maxSize       最大的缓存文件大小，单位 byte
     */
    private static void initCache(String cacheFilePath, long maxSize) {
        if (maxSize > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("maxSize exceeds the FileCache size limit!");
        }

        try {
            mFileCache = FileCache.get(new File(cacheFilePath), maxSize, Integer.MAX_VALUE);
            //  开启先进先出 的缓存策略
//      mFileCache.set2FIFO(true);
            if (mFileCache == null) {
                Log.e(TAG, "FileCache open failed " + cacheFilePath + " " + maxSize);
            } else {
                Log.d(TAG, "FileCache open success " + cacheFilePath + " " + maxSize);
            }
        } catch (Exception e) {
            Log.e(TAG, "FileCache open error " + cacheFilePath + " " + maxSize, e);
        }
    }

    /**
     * 保存 String 到缓存中
     *
     * @param key   保存的 key
     * @param value 保存的 String 数据，可以为空字符串，但不能是null
     * @return true 保存成功 | false 保存失败
     */
    public static boolean put(String key, @NonNull String value) {
        if (mFileCache == null || TextUtils.isEmpty(key) || value == null) {
            return false;
        }

        try {
            boolean writeSuccess = mFileCache.put(key, value);

            if (writeSuccess) {
                Log.d(TAG, key + " write FileCache success");
                return true;
            } else {
                Log.e(TAG, key + " write FileCache failed");
            }
        } catch (Throwable e) {
            Log.e(TAG, key + " write FileCache exception ", e);
        }

        return false;
    }

    /**
     * 保存 Double 到缓存中
     *
     * @param key   保存的 key
     * @param value 保存的 int 数据
     * @return true 保存成功 | false 保存失败
     */
    public static boolean put(String key, double value) {
        if (mFileCache == null || TextUtils.isEmpty(key)) {
            return false;
        }

        try {
            boolean writeSuccess = put(key, String.valueOf(value));

            if (writeSuccess) {
                Log.d(TAG, key + " write FileCache success");
                return true;
            } else {
                Log.e(TAG, key + " write FileCache failed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e(TAG, key + " write FileCache exception ", e);
        }

        return false;
    }

    /**
     * 保存 Boolean 到缓存中
     *
     * @param key   保存的 key
     * @param value 保存的 Boolean 数据
     * @return true 保存成功 | false 保存失败
     */
    public static boolean put(String key, boolean value) {
        if (mFileCache == null || TextUtils.isEmpty(key)) {
            return false;
        }

        try {
            boolean writeSuccess = put(key, String.valueOf(value));

            if (writeSuccess) {
                Log.d(TAG, key + " write FileCache success");
                return true;
            } else {
                Log.e(TAG, key + " write FileCache failed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e(TAG, key + " write FileCache exception ", e);
        }

        return false;
    }

    /**
     * 保存 Integer 到缓存中
     *
     * @param key   保存的 key
     * @param value 保存的 Integer 数据
     * @return true 保存成功 | false 保存失败
     */
    public static boolean put(String key, int value) {
        if (mFileCache == null || TextUtils.isEmpty(key)) {
            return false;
        }

        try {
            boolean writeSuccess = put(key, String.valueOf(value));

            if (writeSuccess) {
                Log.d(TAG, key + " write FileCache success");
                return true;
            } else {
                Log.e(TAG, key + " write FileCache failed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e(TAG, key + " write FileCache exception ", e);
        }

        return false;
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key 缓存的key
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static String getStringFromCache(String key) {
        return getStringFromCache(key, "");
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key          缓存的key
     * @param defaultValue 默认值
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static String getStringFromCache(String key, String defaultValue) {
        if (mFileCache == null) {
            return defaultValue;
        }

        return mFileCache.getAsString(key);
    }

    /**
     * 获取 key 存储的 Double
     *
     * @param key 缓存的key
     * @return key 对应的缓存值，如果出错则返回 -1
     */
    public static double getDoubleFromCache(String key) {
        return getDoubleFromCache(key, 0d);
    }

    /**
     * 获取 key 存储的 Double
     *
     * @param key          缓存的key
     * @param defaultValue 默认值
     * @return key 对应的缓存值，如果出错则返回 -1
     */
    public static double getDoubleFromCache(String key, double defaultValue) {
        if (mFileCache == null) {
            return defaultValue;
        }

        String result = getStringFromCache(key);
        try {
            return Double.valueOf(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key 缓存的key
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static boolean getBooleanFromCache(String key) {
        return getBooleanFromCache(key, false);
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key          缓存的key
     * @param defaultValue 默认值
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static boolean getBooleanFromCache(String key, boolean defaultValue) {
        if (mFileCache == null) {
            return defaultValue;
        }

        String result = getStringFromCache(key);
        try {
            return Boolean.valueOf(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key 缓存的key
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static int getIntegerFromCache(String key) {
        return getIntegerFromCache(key, 0);
    }

    /**
     * 获取 key 存储的 String
     *
     * @param key          缓存的key
     * @param defaultValue 默认值
     * @return key 对应的缓存值，如果出错则返回 null
     */
    public static int getIntegerFromCache(String key, int defaultValue) {
        if (mFileCache == null) {
            return defaultValue;
        }

        String result = getStringFromCache(key);
        try {
            return Integer.valueOf(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    public static <T> boolean put(String key, T value) {
        put(key, String.valueOf(value));
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) getStringFromCache(key);
    }

    public static boolean delete(String key) {
        if (mFileCache == null || TextUtils.isEmpty(key)) {
            return false;
        }

        try {
            boolean writeSuccess = mFileCache.remove(key);

            if (writeSuccess) {
                Log.d(TAG, key + " write FileCache success");
                return true;
            } else {
                Log.e(TAG, key + " write FileCache failed");
            }
        } catch (Throwable e) {
            Log.e(TAG, key + " write FileCache exception ", e);
        }

        return false;
    }

    public static void deleteAll() {
        if (mFileCache == null) {
            return;
        }

        mFileCache.clear();
    }

    public static boolean contains(String key) {
        return false;
    }

}
