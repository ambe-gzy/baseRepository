package cn.zhenye.ad.vm;

import java.util.ArrayList;
import java.util.List;

import cn.zhenye.ad.TbkDataFavoriteItem;
import cn.zhenye.base.tool.ZGsonUtils;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.common.tbad.AdManager;
import cn.zhenye.common.tbad.response.TbkFavoriteItemResponse;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.common.tbad.response.TbkItemDetailResponse;
import cn.zhenye.common.tbad.response.TbkTaokoulingResponse;

public class TbkVMManager {
    private static TbkVMManager INSTANCE = new TbkVMManager();
    private List<TbkFavListener> mFavoritesListeners;
    private List<TbkFavItemListener> mFavoriteItemsListener;
    private List<TbkTaokoulingListener> mTaokoulingListeners;
    private List<TbkItemDetailListener> mTbkItemDetailListeners;

    public static TbkVMManager getInstance() {
        return INSTANCE;
    }

    public void loadFavourites(final int pageNo){
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr =  AdManager.getInstance().getTbFavorites(pageNo);
                    if (jsonStr != null) {
                        notifyFavouritesCallBack(ZGsonUtils.formJson(jsonStr, TbkFavoritesResponse.class));
                    } else {
                        notifyFavouritesCallBack(null);
                    }
                } catch (Exception e) {
                    notifyFavouritesCallBack(null);
                    e.printStackTrace();
                }
            }
        });
    }
    public void loadFavoriteItems(final int favouritesId){
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = AdManager.getInstance().getTbkFavoriteItems(favouritesId);
                    TbkFavoriteItemResponse response = ZGsonUtils.formJson(jsonStr, TbkFavoriteItemResponse.class);
                    if (response.getResult() != null ) {
                        TbkDataFavoriteItem item = new TbkDataFavoriteItem();
                        item.favouritesId = favouritesId;
                        item.response = response;
                        notifyFavoriteItemsCallBack(item);
                    }
                } catch (Exception e) {
                    notifyFavouritesCallBack(null);
                    e.printStackTrace();
                }
            }
        });
    }
    public void loadTaokouling(final String text,final String url) {
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = AdManager.getInstance().getTbkTaokoulingStrJson(text,url);
                    TbkTaokoulingResponse response = ZGsonUtils.formJson(jsonStr, TbkTaokoulingResponse.class);
                    notifyTaokoulingCallBack(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadItemDetail(final String num_iids) {
        ZThreadManager.getAds().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = AdManager.getInstance().getTbkItemDetailStrJson(num_iids);
                    TbkItemDetailResponse response = ZGsonUtils.formJson(jsonStr, TbkItemDetailResponse.class);
                    notifyItemDetailCallBack(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addFavouritesListener(TbkFavListener listener) {
        if (mFavoritesListeners == null) {
            mFavoritesListeners = new ArrayList<>();
        }
        mFavoritesListeners.add(listener);
    }
    public void addFavoriteItemsListener(TbkFavItemListener listener){
        if (mFavoriteItemsListener == null) {
            mFavoriteItemsListener = new ArrayList<>();
        }
        mFavoriteItemsListener.add(listener);
    }
    public void addTaokoulingListener(TbkTaokoulingListener listener) {
        if (mTaokoulingListeners == null) {
            mTaokoulingListeners = new ArrayList<>();
        }
        mTaokoulingListeners.add(listener);
    }
    public void addItemDetailListener(TbkItemDetailListener listener){
        if (mTbkItemDetailListeners == null) {
            mTbkItemDetailListeners = new ArrayList<>();
        }
        mTbkItemDetailListeners.add(listener);
    }

    public void removeFavoritesListener(TbkFavListener listener) {
        if (mFavoritesListeners == null) {
            return;
        }
        mFavoritesListeners.remove(listener);
    }
    public void removeFavoriteItemsListener(TbkFavItemListener listener){
        if (mFavoriteItemsListener == null) {
            return;
        }
        mFavoriteItemsListener.remove(listener);
    }
    public void removeTaokoulingListener(TbkTaokoulingListener listener) {
        if (mTaokoulingListeners == null) {
            return;
        }
        mTaokoulingListeners.remove(listener);
    }
    public void removeItemDetailListener(TbkItemDetailListener listener) {
        if (mTbkItemDetailListeners == null) {
            return;
        }
        mTbkItemDetailListeners.remove(listener);
    }

    public void notifyFavouritesCallBack(TbkFavoritesResponse baseResult) {
        if (mFavoritesListeners == null) {
            return;
        }
        for (int i = 0; i< mFavoritesListeners.size(); i++){
            mFavoritesListeners.get(i).onFinish(baseResult);
        }
    }
    public void notifyFavoriteItemsCallBack(TbkDataFavoriteItem response){
        if (mFavoriteItemsListener == null) {
            return;
        }
        for (int i=0;i<mFavoriteItemsListener.size();i++) {
            mFavoriteItemsListener.get(i).onFinish(response);
        }
    }
    public void notifyTaokoulingCallBack(TbkTaokoulingResponse response) {
        if (mTaokoulingListeners == null) {
            return;
        }
        for (int i = 0; i< mTaokoulingListeners.size(); i++) {
            mTaokoulingListeners.get(i).onFinish(response);
        }
    }
    public void notifyItemDetailCallBack(TbkItemDetailResponse response) {
        if (mTbkItemDetailListeners == null) {
            return;
        }
        for (int i =0;i<mTbkItemDetailListeners.size();i++) {
            mTbkItemDetailListeners.get(i).onFinish(response);
        }
    }

    public interface TbkFavItemListener {
        void onFinish(TbkDataFavoriteItem tTbkBaseResponse);
    }

    public interface TbkFavListener {
        void onFinish(TbkFavoritesResponse tTbkBaseResponse);
    }

    public interface TbkTaokoulingListener {
        void onFinish(TbkTaokoulingResponse response);
    }

    public interface TbkItemDetailListener {
        void onFinish(TbkItemDetailResponse response);
    }
}
