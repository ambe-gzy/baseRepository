package cn.zhenye.ad.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cn.zhenye.common.ad.response.ZAdResponse;

public class ZAdViewModel extends AndroidViewModel {
    private MutableLiveData<ZAdResponse> adResponseMutableLiveData = new MutableLiveData<>();

    private ZAdVMManager.OnAdListener listener = new ZAdVMManager.OnAdListener() {
        @Override
        public void onFinish(ZAdResponse response) {
            getAdResponseMutableLiveData().postValue(response);
        }
    };

    public ZAdViewModel(@NonNull Application application) {
        super(application);
        ZAdVMManager.getInstance().addListener(listener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ZAdVMManager.getInstance().removeListener(listener);
    }

    public MutableLiveData<ZAdResponse> getAdResponseMutableLiveData() {
        return adResponseMutableLiveData;
    }
}
