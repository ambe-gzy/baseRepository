package cn.zhenye.base.cache;

/**
 * @author zhaibinme on 2018/7/21
 */
public interface Cache {

    <T> boolean put(String key, T value, boolean needEncrypt);

    <T> T get(String key);

    <T> T get(String key, T defaultValue);

    boolean delete(String key);

    boolean contains(String key);
}
