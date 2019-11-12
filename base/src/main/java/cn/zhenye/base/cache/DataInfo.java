package cn.zhenye.base.cache;

/**
 * @author zhaibinme on 2018/7/21
 */
public final class DataInfo {

    public static final char TYPE_OBJECT = '0';
    public static final char TYPE_LIST = '1';
    public static final char TYPE_MAP = '2';
    public static final char TYPE_SET = '3';

    public static final String NEED_ENCRYPT = "0";
    public static final String NEED_NOT_ENCRYPT = "1";

    public final char dataType;
    public final String cipherText;
    public final Class keyClazz;
    public final Class valueClazz;
    public final boolean needEncrypt;

    public DataInfo(String needEncrypt, char dataType, String cipherText, Class keyClazz, Class valueClazz) {
        if (needEncrypt.equals(NEED_ENCRYPT)) {
            this.needEncrypt = true;
        } else {
            this.needEncrypt = false;
        }

        this.cipherText = cipherText;
        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
        this.dataType = dataType;
    }
}
