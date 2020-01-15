package cn.zhenye.common.credit.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import cn.zhenye.common.credit.manager.CreditStatusManager;

public class CreditStatusViewModel extends AndroidViewModel {
    private CreditStatusManager.OnRefreshListener<Boolean> mOneMinuteStatusListener = new CreditStatusManager.OnRefreshListener<Boolean>() {
        @Override
        public void onRefresh(@NonNull Boolean task) {
            mOneMinuteBtnStatusLiveData.postValue(task);
        }
    };
    private CreditStatusManager.OnRefreshListener<Boolean> mFiveMinuteStatusListener = new CreditStatusManager.OnRefreshListener<Boolean>() {
        @Override
        public void onRefresh(@NonNull Boolean task) {
            mFiveMinuteBtnStatusLiveData.postValue(task);
        }
    };
    private CreditStatusManager.OnRefreshListener<Boolean> mFifteenStatusListener = new CreditStatusManager.OnRefreshListener<Boolean>() {
        @Override
        public void onRefresh(@NonNull Boolean task) {
            mFifteenMinuteBtnStatusLiveData.postValue(task);
        }
    };

    private CreditStatusManager.OnRefreshListener<Long> mOneMinuteResTimeListener = new CreditStatusManager.OnRefreshListener<Long>() {
        @Override
        public void onRefresh(@NonNull Long task) {
            mOneMinuteCountTimerLiveData.postValue(task);
        }
    };
    private CreditStatusManager.OnRefreshListener<Long> mFiveMinuteResTimeListener = new CreditStatusManager.OnRefreshListener<Long>() {
        @Override
        public void onRefresh(@NonNull Long task) {
            mFiveMinuteCountTimerLiveData.postValue(task);
        }
    };
    private CreditStatusManager.OnRefreshListener<Long> mFifteenMinuteResTimeListener = new CreditStatusManager.OnRefreshListener<Long>() {
        @Override
        public void onRefresh(@NonNull Long task) {
            mFifteenMinuteCountTimerLiveData.postValue(task);
        }
    };

    private MutableLiveData<Boolean> mOneMinuteBtnStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFiveMinuteBtnStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFifteenMinuteBtnStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> mOneMinuteCountTimerLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> mFiveMinuteCountTimerLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> mFifteenMinuteCountTimerLiveData = new MutableLiveData<>();

    public CreditStatusViewModel(@NonNull Application application) {
        super(application);
        CreditStatusManager.getInstance().registerOneMinuteBtnStatusListener(mOneMinuteStatusListener);
        CreditStatusManager.getInstance().regiserFiveMinuteBtnStatusListener(mFiveMinuteStatusListener);
        CreditStatusManager.getInstance().regiserFifteenMinuteBtnStatusListener(mFifteenStatusListener);

        CreditStatusManager.getInstance().registerOneMinuteCountTimerListener(mOneMinuteResTimeListener);
        CreditStatusManager.getInstance().registerFiveMinuteCountTimerListener(mFiveMinuteResTimeListener);
        CreditStatusManager.getInstance().registerFifteenMinuteCountTimerListener(mFifteenMinuteResTimeListener);

        //更新按钮状态
        CreditStatusManager.getInstance().notifyStatus();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        CreditStatusManager.getInstance().removeOneMinuteBtnStatusListener(mOneMinuteStatusListener);
        CreditStatusManager.getInstance().removeFiveMinuteBtnStatusListener(mFiveMinuteStatusListener);
        CreditStatusManager.getInstance().removeFifteenMinuteBtnStatusListener(mFifteenStatusListener);
        CreditStatusManager.getInstance().removeOneMinuteCountTimerListener(mOneMinuteResTimeListener);
        CreditStatusManager.getInstance().removeFiveMinuteCountTimerListener(mFiveMinuteResTimeListener);
        CreditStatusManager.getInstance().removeFifteenMinuteCountTimerListener(mFifteenMinuteResTimeListener);
    }

    public MutableLiveData<Boolean> getOneMinuteBtnStatus() {
        return mOneMinuteBtnStatusLiveData;
    }

    public MutableLiveData<Boolean> getFiveMinuteBtnStatus() {
        return mFiveMinuteBtnStatusLiveData;
    }

    public MutableLiveData<Boolean> getFifteenMinuteBtnStatus() {
        return mFifteenMinuteBtnStatusLiveData;
    }

    public MutableLiveData<Long> getOneMinuteCountTimer() {
        return mOneMinuteCountTimerLiveData;
    }

    public MutableLiveData<Long> getFiveMinuteCountTimer() {
        return mFiveMinuteCountTimerLiveData;
    }

    public MutableLiveData<Long> getFifteenMinuteCountTimer() {
        return mFifteenMinuteCountTimerLiveData;
    }
}
