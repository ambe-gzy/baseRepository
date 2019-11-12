package cn.zhenye.base.cache;

import android.text.TextUtils;
import android.util.Base64;

import cn.zhenye.base.cache.encrypt.Encryption;
import cn.zhenye.base.cache.parser.Parser;
import cn.zhenye.base.cache.serializer.Serializer;
import cn.zhenye.base.cache.storage.FileStorage;
import cn.zhenye.base.cache.storage.SharedPreferencesStorage;
import cn.zhenye.base.cache.storage.StorageConstants;

/**
 * @author zhaibinme on 2018/7/21
 */
public class DefaultCache implements Cache {
    private CacheBuilder mCacheBuilder;
    private Parser mParser;
    private Serializer mSerializer;
    private Encryption mEncryption;

    DefaultCache(CacheBuilder cacheBuilder) {
        mCacheBuilder = cacheBuilder;
        mParser = mCacheBuilder.getParser();
        mSerializer = mCacheBuilder.getSerializer();
        mEncryption = mCacheBuilder.getEncryption();
    }

    @Override
    public <T> boolean put(String key, T value, boolean needEncrypt) {
        if (TextUtils.isEmpty(key) || !(key.startsWith(StorageConstants.STORAGE_MODE_FILE) || key.startsWith(StorageConstants.STORAGE_MODE_SP))) {
            // key 不能为空，或者不以："sp" or "file"开头，否则无法判断需要存储的格式：是文件还是SharePreferences
            throw new IllegalArgumentException("key cannot be empty, and must start with \"sp\" or \"file\". Please check the notes in YoloCacheConstants" +
                    ".java");
        }

        // 1: 转成JSON
        String parserValue = mParser.toJson(value);
        if (TextUtils.isEmpty(parserValue)) {
            return false;
        }

        // 2: 判断是否需要加密存储字符串 needEncrypt = true;
        String encryptValue;
        if (needEncrypt) {
            if (mEncryption == null) {
                return false;
            }

            try {
                encryptValue = mEncryption.encrypt(key, parserValue);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            encryptValue = parserValue;
        }

        // 3：序列化存储字符串和存储的信息
        String serializerValue = mSerializer.serialize(needEncrypt, encryptValue, value);
        if (TextUtils.isEmpty(serializerValue)) {
            return false;
        }

        // 4：存储
        if (key.startsWith(StorageConstants.STORAGE_MODE_FILE)) {
            return FileStorage.put(key, serializerValue);
        } else if (key.startsWith(StorageConstants.STORAGE_MODE_SP)) {
            return SharedPreferencesStorage.put(mCacheBuilder.getContext(), key, serializerValue);
        }

        return false;
    }

    @Override
    public <T> T get(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }

        // 1：获取存储字符串
        String serializerValue = null;
        if (key.startsWith(StorageConstants.STORAGE_MODE_FILE)) {
            serializerValue = FileStorage.getStringFromCache(key);
        } else if (key.startsWith(StorageConstants.STORAGE_MODE_SP)) {
            serializerValue = SharedPreferencesStorage.get(mCacheBuilder.getContext(), key);
        }

        if (TextUtils.isEmpty(serializerValue)) {
            return null;
        }

        // 2：反序列化字符串
        DataInfo dataInfo = mSerializer.deserialize(serializerValue);
        if (dataInfo == null) {
            return null;
        }

        // 3：判断是否需要加密，如需要加密，则对字符串进行解密
        String decryptValue;
        if (dataInfo.needEncrypt) {
            if (mEncryption == null) {
                return null;
            }

            try {
                decryptValue = mEncryption.decrypt(key, dataInfo.cipherText);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            decryptValue = dataInfo.cipherText;
        }

        // 4：返回存储的值
        T result = null;
        try {
            result = mParser.fromString(decryptValue, dataInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        T t = get(key);
        if (t == null) {
            return defaultValue;
        }
        return t;
    }

    @Override
    public boolean delete(String key) {
        if (key.startsWith(StorageConstants.STORAGE_MODE_FILE)) {
            return FileStorage.delete(key);
        } else if (key.startsWith(StorageConstants.STORAGE_MODE_SP)) {
            return SharedPreferencesStorage.delete(mCacheBuilder.getContext(), key);
        }
        return false;
    }

    @Override
    public boolean contains(String key) {
        if (key.startsWith(StorageConstants.STORAGE_MODE_FILE)) {
            return FileStorage.contains(key);
        } else if (key.startsWith(StorageConstants.STORAGE_MODE_SP)) {
            return SharedPreferencesStorage.contains(mCacheBuilder.getContext(), key);
        }
        return false;
    }

    public <T> String toEncrypt(T value) {
        if (mEncryption == null) {
            return "";
        }

        String parserValue = mParser.toJson(value);
        String encryptValue = "";
        try {
            encryptValue = Base64.encodeToString(parserValue.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptValue;
    }

    public <T> T getValueByDecrypt(String value, Class classValue) {
        if (mEncryption == null) {
            return null;
        }

        String decryptValue = "";

        try {
            decryptValue = new String(Base64.decode(value.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            return (T) mParser.fromJson(decryptValue, classValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
