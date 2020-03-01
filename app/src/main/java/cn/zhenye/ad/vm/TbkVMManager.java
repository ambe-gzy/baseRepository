package cn.zhenye.ad.vm;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.zhenye.base.tool.ZDaemons;
import cn.zhenye.base.tool.ZGsonUtils;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.common.tbad.TbAdManager;
import cn.zhenye.common.tbad.response.TbkBaseResult;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.home.HomeCoinFragment;

public class TbkVMManager {
    private static TbkVMManager INSTANCE = new TbkVMManager();
    private List<TbkListener> mFavoriteslisteners;

    public static TbkVMManager getInstance() {
        return INSTANCE;
    }

    public void loadFavourites(final int pageNo){
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr =  TbAdManager.getInstance().getTbFavourites(pageNo);
                    if (jsonStr != null) {
                        notifyFavouritesCallBack(ZGsonUtils.formJson(jsonStr,TbkFavoritesResponse.class));
                    } else {
                        notifyFavouritesCallBack(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadFavoriteItems(){

    }

    public void addFavouritesListener(TbkListener listener) {
        if (mFavoriteslisteners == null) {
            mFavoriteslisteners = new ArrayList<>();
        }
        mFavoriteslisteners.add(listener);
    }

    public void removeFavoritesListener(TbkListener listener) {
        if (mFavoriteslisteners == null) {
            return;
        }
        mFavoriteslisteners.remove(listener);
    }

    public void notifyFavouritesCallBack(TbkFavoritesResponse baseResult) {
        if (mFavoriteslisteners == null) {
            return;
        }
        for (int i = 0; i<mFavoriteslisteners.size();i++){
            mFavoriteslisteners.get(i).onFinish(baseResult);
        }
    }


    public interface TbkListener<T> {
        void onFinish(TbkBaseResult<T> tTbkBaseResult);
    }
}
