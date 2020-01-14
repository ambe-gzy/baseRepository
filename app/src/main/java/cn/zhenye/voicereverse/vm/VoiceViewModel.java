package cn.zhenye.voicereverse.vm;

import android.app.Application;
import android.os.SystemClock;

import com.umeng.commonsdk.statistics.common.DataHelper;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.zhenye.base.tool.ZThreadManager;
import cn.zhenye.base.tool.ZToastUtils;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.db.dao.VoiceDao;
import cn.zhenye.common.db.dao.VoiceFileDao;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.db.entity.VoiceFileEntity;

public class VoiceViewModel extends AndroidViewModel {
    //录音文件目录
    private LiveData<List<VoiceFileEntity>> mVoiceFileEntityLiveData;
    //当前选中的录音目录
    private MutableLiveData<String> mCurrentFilePah = new MutableLiveData<>();

    //保存的所有录音文件
    private LiveData<List<VoiceEntity>> mVoiceEntityLiveData;

    private VoiceFileDao dao;
    private VoiceDao voiceDao;

    public VoiceViewModel(@NonNull Application application) {
        super(application);
        dao = DatabaseManager.getInstance().voiceFileDao();
        voiceDao = DatabaseManager.getInstance().voiceDao();
        mVoiceFileEntityLiveData = dao.getAllEntityLiveData();
        mVoiceEntityLiveData = voiceDao.getVoiceEntityListLivedata();
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
        ZThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                dao.saveVoiceFileEntity(voiceFileEntityLiveData.getValue());
            }
        });
    }

    public LiveData<List<VoiceEntity>> getVoiceEntityLiveData(){
        return mVoiceEntityLiveData;
    }

    public void setVoiceEntityLiveData(final VoiceEntity entity){
        String currentPath = mCurrentFilePah.getValue();
        if (currentPath == null){
            ZToastUtils.showShort("保存出错，请重试");
            return;
        }
        entity.savePath = currentPath;
        entity.saveName = String.valueOf(System.currentTimeMillis());
        entity.createTimestamp = System.currentTimeMillis();
        entity.isShow = true;
        ZThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                voiceDao.insertVoiceEntity(entity);
                ZToastUtils.showShort("保存成功");
            }
        });
    }
}
