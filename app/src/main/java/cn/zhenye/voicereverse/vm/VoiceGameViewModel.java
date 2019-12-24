package cn.zhenye.voicereverse.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class VoiceGameViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isAuthorStart = new MutableLiveData<>();
    private MutableLiveData<Boolean> isChallengerStart = new MutableLiveData<>();

    public VoiceGameViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsAuthorStart() {
        return isAuthorStart;
    }

    public void setIsAuthorStart(boolean isAuthorStart) {

        this.isAuthorStart.setValue(isAuthorStart);
    }

    public MutableLiveData<Boolean> getIsChallengerStart() {
        return isChallengerStart;
    }

    public void setIsChallengerStart(Boolean isChallengerStart) {
        this.isChallengerStart.setValue(isChallengerStart);
    }
}
