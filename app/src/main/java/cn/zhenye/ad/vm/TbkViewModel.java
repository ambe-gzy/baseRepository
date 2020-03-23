package cn.zhenye.ad.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cn.zhenye.ad.TbkDataFavoriteItem;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;
import cn.zhenye.common.tbad.response.TbkItemDetailResponse;
import cn.zhenye.common.tbad.response.TbkTaokoulingResponse;

public class TbkViewModel extends AndroidViewModel {

    private MutableLiveData<TbkFavoritesResponse> mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<TbkDataFavoriteItem> tbkFavItemLiveData = new MutableLiveData<>();

    private MutableLiveData<TbkTaokoulingResponse> taoloulingLiveData = new MutableLiveData<>();

    private MutableLiveData<TbkItemDetailResponse> itemDetailLiveData = new MutableLiveData<>();

    private TbkVMManager.TbkFavListener listener = new TbkVMManager.TbkFavListener() {
        @Override
        public void onFinish(TbkFavoritesResponse baseResult) {
            getFavorites().postValue(baseResult);
        }
    };

    private TbkVMManager.TbkFavItemListener mFavoriteItemsListener = new TbkVMManager.TbkFavItemListener() {
        @Override
        public void onFinish(TbkDataFavoriteItem tbkBaseResponse) {
            getFavoriteItems().postValue(tbkBaseResponse);
        }
    };

    private TbkVMManager.TbkTaokoulingListener mTbkTaokoulingListener = new TbkVMManager.TbkTaokoulingListener() {
        @Override
        public void onFinish(TbkTaokoulingResponse response) {
            getTaoloulingLiveData().postValue(response);
        }
    };

    private TbkVMManager.TbkItemDetailListener mItemDetailListener = new TbkVMManager.TbkItemDetailListener() {
        @Override
        public void onFinish(TbkItemDetailResponse response) {
            getItemDetailLiveData().postValue(response);
        }
    };

    public TbkViewModel(@NonNull Application application) {
        super(application);
        TbkVMManager.getInstance().addFavouritesListener(listener);
        TbkVMManager.getInstance().addFavoriteItemsListener(mFavoriteItemsListener);
        TbkVMManager.getInstance().addTaokoulingListener(mTbkTaokoulingListener);
        TbkVMManager.getInstance().addItemDetailListener(mItemDetailListener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        TbkVMManager.getInstance().removeFavoritesListener(listener);
        TbkVMManager.getInstance().removeFavoriteItemsListener(mFavoriteItemsListener);
        TbkVMManager.getInstance().removeTaokoulingListener(mTbkTaokoulingListener);
        TbkVMManager.getInstance().removeItemDetailListener(mItemDetailListener);
    }

    public MutableLiveData<TbkFavoritesResponse> getFavorites() {
        return mutableLiveData;
    }

    public MutableLiveData<TbkDataFavoriteItem> getFavoriteItems() {
        return tbkFavItemLiveData;
    }

    public MutableLiveData<TbkTaokoulingResponse> getTaoloulingLiveData() {
        return taoloulingLiveData;
    }

    public MutableLiveData<TbkItemDetailResponse> getItemDetailLiveData() {
        return itemDetailLiveData;
    }
}
