package cn.zhenye.base.cache.serializer;

import android.text.TextUtils;
import android.util.Log;

import cn.zhenye.base.cache.DataInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zhaibinme on 2018/7/21
 */
public class YoloSerializer implements Serializer {


    private static final char DELIMITER = '@';
    private static final String INFO_DELIMITER = "#";
    private static final char NEW_VERSION = 'V';

    public YoloSerializer() {}

    @Override
    public <T> String serialize(boolean needEncrypt, String cipherText, T originalGivenValue) {
        if (TextUtils.isEmpty(cipherText)) {
            return null;
        }

        if (originalGivenValue == null) {
            return null;
        }

        String keyClassName = "";
        String valueClassName = "";
        char dataType;
        if (List.class.isAssignableFrom(originalGivenValue.getClass())) {
            List<?> list = (List<?>) originalGivenValue;
            if (!list.isEmpty()) {
                keyClassName = list.get(0).getClass().getName();
                valueClassName = list.getClass().getName();
            }
            dataType = DataInfo.TYPE_LIST;
        } else if (Map.class.isAssignableFrom(originalGivenValue.getClass())) {
            dataType = DataInfo.TYPE_MAP;
            Map<?, ?> map = (Map) originalGivenValue;
            if (!map.isEmpty()) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    keyClassName = entry.getKey().getClass().getName();
                    valueClassName = entry.getValue().getClass().getName();
                    break;
                }
            }
        } else if (Set.class.isAssignableFrom(originalGivenValue.getClass())) {
            Set<?> set = (Set<?>) originalGivenValue;
            if (!set.isEmpty()) {
                Iterator<?> iterator = set.iterator();
                if (iterator.hasNext()) {
                    keyClassName = iterator.next().getClass().getName();
                }
            }
            dataType = DataInfo.TYPE_SET;
        } else {
            dataType = DataInfo.TYPE_OBJECT;
            keyClassName = originalGivenValue.getClass().getName();
        }

        return (needEncrypt ? DataInfo.NEED_ENCRYPT : DataInfo.NEED_NOT_ENCRYPT) + INFO_DELIMITER +
                keyClassName + INFO_DELIMITER +
                valueClassName + INFO_DELIMITER +
                dataType + NEW_VERSION + DELIMITER +
                cipherText;
    }

    @Override
    public DataInfo deserialize(String serializedText) {
        String[] infos = serializedText.split(INFO_DELIMITER);

        if (infos.length < 4) {
            return null;
        }

        char type = infos[3].charAt(0);

        // if it is collection, no need to create the class object
        Class<?> keyClazz = null;
        String firstElement = infos[1];
        if (firstElement != null && firstElement.length() != 0) {
            try {
                keyClazz = Class.forName(firstElement);
            } catch (ClassNotFoundException e) {
                Log.d(YoloSerializer.class.getSimpleName(),"YoloSerializer -> " + e.getMessage());
            }
        }

        Class<?> valueClazz = null;
        String secondElement = infos[2];
        if (secondElement != null && secondElement.length() != 0) {
            try {
                valueClazz = Class.forName(secondElement);
            } catch (ClassNotFoundException e) {
                Log.d(YoloSerializer.class.getSimpleName(),"YoloSerializer -> " + e.getMessage());
            }
        }

        String cipherText = getCipherText(infos[infos.length - 1]);
        return new DataInfo(infos[0], type, cipherText, keyClazz, valueClazz);
    }

    private String getCipherText(String serializedText) {
        int index = serializedText.indexOf(DELIMITER);
        if (index == -1) {
            throw new IllegalArgumentException("Text should contain delimiter");
        }
        return serializedText.substring(index + 1);
    }

}
