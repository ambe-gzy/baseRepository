package cn.zhenye.common.credit.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import cn.zhenye.common.credit.manager.CreditManager;

public class CreditViewModel extends AndroidViewModel {
    private CreditManager.OnRefreshListener<String > mCurrentCreditListener = new CreditManager.OnRefreshListener<String>() {
        @Override
        public void onRefresh(@NonNull String credit) {
            mCurrentCreditLiveData.postValue(credit);
        }
    };

    public MutableLiveData<String> getCurrentCreditLiveData() {
        return mCurrentCreditLiveData;
    }

    private MutableLiveData<String> mCurrentCreditLiveData = new MutableLiveData<>();


    public CreditViewModel(@NonNull Application application) {
        super(application);
        CreditManager.getInstance().registerCurrentCreditListener(mCurrentCreditListener);
        CreditManager.getInstance().notifyCurrentCreditListener();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        CreditManager.getInstance().removeCurrentCreditListener(mCurrentCreditListener);
    }


}
