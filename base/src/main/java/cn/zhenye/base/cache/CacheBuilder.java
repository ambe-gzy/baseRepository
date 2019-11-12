package cn.zhenye.base.cache;

import android.content.Context;

import com.google.gson.GsonBuilder;

import cn.zhenye.base.cache.encrypt.ConcealEncryption;
import cn.zhenye.base.cache.encrypt.Encryption;
import cn.zhenye.base.cache.parser.GsonParser;
import cn.zhenye.base.cache.parser.Parser;
import cn.zhenye.base.cache.serializer.Serializer;
import cn.zhenye.base.cache.serializer.YoloSerializer;

/**
 * @author zhaibinme on 2018/7/21
 */
public class CacheBuilder {

    private Context mContext;
    private Parser mParser;
    private Serializer mSerializer;
    private Encryption mEncryption;


    public CacheBuilder(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public Parser getParser() {
        if (mParser == null) {
            mParser = new GsonParser(new GsonBuilder().serializeNulls().create());
        }
        return mParser;
    }

    public Serializer getSerializer() {
        if (mSerializer == null) {
            mSerializer = new YoloSerializer();
        }
        return mSerializer;
    }

    public Encryption getEncryption() {
        if (mEncryption == null) {
            mEncryption = new ConcealEncryption(mContext);
            if (!mEncryption.init()) {
                return null;
            }
        }
        return mEncryption;
    }
}
