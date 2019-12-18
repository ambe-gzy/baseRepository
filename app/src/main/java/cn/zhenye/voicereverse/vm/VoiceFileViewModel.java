package cn.zhenye.voicereverse.vm;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import cn.zhenye.base.tool.ThreadManager;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.db.dao.VoiceFileDao;
import cn.zhenye.common.db.entity.VoiceFileEntity;

public class VoiceFileViewModel extends AndroidViewModel {
    private LiveData<List<VoiceFileEntity>> voiceFileEntityLiveData;
    private VoiceFileDao dao;

    public VoiceFileViewModel(@NonNull Application application) {
        super(application);
        dao = DatabaseManager.getInstance().voiceFileDao();
        voiceFileEntityLiveData = dao.getAllEntityLiveData();
     }


    public LiveData<List<VoiceFileEntity>> getVoiceFileEntityLiveData() {
        return voiceFileEntityLiveData;
    }

    public void setVoiceFileEntityLiveData(final LiveData<VoiceFileEntity> voiceFileEntityLiveData) {
        ThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                dao.saveVoiceFileEntity(voiceFileEntityLiveData.getValue());
            }
        });
    }
}
