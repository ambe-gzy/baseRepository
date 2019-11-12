package cn.zhenye.base.cache.parser;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.zhenye.base.cache.DataInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zhaibinme on 2018/7/21
 */
public final class GsonParser implements Parser {

    private final Gson gson;

    public GsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override public <T> String toString(T value) {
        if (value == null) {
            return null;
        }
        return toJson(value);
    }

    @SuppressWarnings("unchecked")
    @Override public <T> T fromString(String value, DataInfo info) {
        if (value == null) {
            return null;
        }

        Class<?> keyType = info.keyClazz;
        Class<?> valueType = info.valueClazz;

        switch (info.dataType) {
            case DataInfo.TYPE_OBJECT:
                return toObject(value, keyType);
            case DataInfo.TYPE_LIST:
                return toList(value, keyType, valueType);
            case DataInfo.TYPE_MAP:
                return toMap(value, keyType, valueType);
            case DataInfo.TYPE_SET:
                return toSet(value, keyType);
            default:
                return null;
        }
    }

    private <T> T toObject(String json, Class<?> type) {
        return fromJson(json, type);
    }

    @SuppressWarnings("unchecked")
    private <T> T toList(String json, Class<?> type, Class<?> valueType) {
        if (type == null) {
            return null;
        }
        List<T> list = fromJson(json, (new ArrayList<>()).getClass());
        List<T> valueList = new ArrayList<>();
        assert list != null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            valueList.add(i, (T) fromJson(toJson(list.get(i)), type));
        }
        return (T) valueList;
    }

    @SuppressWarnings("unchecked")
    private <T> T toSet(String json, Class<?> type) {
        Set<T> resultSet = new HashSet<>();
        if (type == null) {
            return (T) resultSet;
        }

        Set<T> set = fromJson(json, (new HashSet<>()).getClass());
        assert set != null;
        for (T t : set) {
            String valueJson = toJson(t);
            T value = fromJson(valueJson, type);
            resultSet.add(value);
        }
        return (T) resultSet;
    }

    @SuppressWarnings("unchecked")
    private <K, V, T> T toMap(String json, Class<?> keyType, Class<?> valueType) {
        Map<K, V> resultMap = new HashMap<>();
        if (keyType == null || valueType == null) {
            return (T) resultMap;
        }

        Map<K, V> map = fromJson(json, new HashMap<>().getClass());

        assert map != null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            String keyJson = toJson(entry.getKey());
            K k = fromJson(keyJson, keyType);

            String valueJson = toJson(entry.getValue());
            V v = fromJson(valueJson, valueType);
            resultMap.put(k, v);
        }
        return (T) resultMap;
    }

    @Override
    public <T> T fromJson(String content, Type type) throws JsonSyntaxException {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        return gson.fromJson(content, type);
    }

    @Override
    public String toJson(Object body) {
        return gson.toJson(body);
    }

}
