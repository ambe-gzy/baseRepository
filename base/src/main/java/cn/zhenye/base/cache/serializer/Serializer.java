package cn.zhenye.base.cache.serializer;

import cn.zhenye.base.cache.DataInfo;

/**
 * Intermediate layer that is used to serialize/deserialize the cipher text
 * <p>
 * <p>Use custom implementation if built-in implementation is not enough.</p>
 *
 * @see YoloSerializer
 */
public interface Serializer {

    /**
     * Serialize the cipher text along with the given data type
     *
     * @return serialized string
     */
    <T> String serialize(boolean needEncrypt, String cipherText, T value);

    /**
     * Deserialize the given text according to given DataInfo
     *
     * @return original object
     */
    DataInfo deserialize(String plainText);
}