package cn.zhenye.ad.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cn.zhenye.common.tbad.response.TbkBaseResult;
import cn.zhenye.common.tbad.response.TbkFavoritesResponse;

public class TbkViewModel extends AndroidViewModel {

    private MutableLiveData<TbkFavoritesResponse> mutableLiveData = new MutableLiveData<>();
    private TbkVMManager.TbkListener listener = new TbkVMManager.TbkListener() {
        @Override
        public void onFinish(TbkBaseResult baseResult) {
            if(baseResult instanceof TbkFavoritesResponse) {
                getFavorites().postValue((TbkFavoritesResponse) baseResult);
            } else {
                getFavorites().postValue(null);
            }
        }
    };

    public TbkViewModel(@NonNull Application application) {
        super(application);
        TbkVMManager.getInstance().addFavouritesListener(listener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        TbkVMManager.getInstance().removeFavoritesListener(listener);
    }

    public MutableLiveData<TbkFavoritesResponse> getFavorites() {
        return mutableLiveData;
    }


}
