package cn.zhenye.voicereverse.vm;

import android.app.Application;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.zhenye.base.tool.ThreadManager;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.db.dao.VoiceFileDao;
import cn.zhenye.common.db.entity.VoiceFileEntity;

public class VoiceViewModel extends AndroidViewModel {
    //录音文件目录
    private LiveData<List<VoiceFileEntity>> mVoiceFileEntityLiveData;
    //当前选中的录音目录
    private MutableLiveData<String> mCurrentFilePah = new MutableLiveData<>();

    private VoiceFileDao dao;

    public VoiceViewModel(@NonNull Application application) {
        super(application);
        dao = DatabaseManager.getInstance().voiceFileDao();
        mVoiceFileEntityLiveData = dao.getAllEntityLiveData();
     }

    public MutableLiveData<String> getCurrentFilePah() {
        return mCurrentFilePah;
    }

    public void setCurrentFilePah(String mCurrentFilePah) {
        File file = new File(mCurrentFilePah);
        if (!file.exists()){
            file.mkdirs();
        }
        this.mCurrentFilePah.setValue(mCurrentFilePah);
    }

    public LiveData<List<VoiceFileEntity>> getVoiceFileEntityLiveData() {
        return mVoiceFileEntityLiveData;
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
