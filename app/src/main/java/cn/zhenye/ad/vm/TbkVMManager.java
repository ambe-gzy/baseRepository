package cn.zhenye.ad.vm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.zhenye.base.tool.ZGsonUtils;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.common.tbad.TbkAdManager;
import cn.zhenye.common.tbad.response.TbkBaseResponse;
import cn.zhenye.common.tbad.response.TbkFavoriteItemResponse;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;

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
                    String jsonStr =  TbkAdManager.getInstance().getTbFavorites(pageNo);
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

    public void loadFavoriteItems(final int favouriesId){
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = TbkAdManager.getInstance().getTbkFavoriteItems(favouriesId);
                    TbkFavoriteItemResponse response = ZGsonUtils.formJson(jsonStr,TbkFavoriteItemResponse.class);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
        void onFinish(TbkBaseResponse<T> tTbkBaseResponse);
    }
}
