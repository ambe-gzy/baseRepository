package cn.zhenye.common.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.zhenye.common.db.entity.VoiceFileEntity;

/**
 * @author zhenye on 20191217
 */
@Dao
public interface VoiceFileDao {

    @Query("SELECT * FROM VOICE_FILE_ENTITY")
    LiveData<List<VoiceFileEntity>> getAllEntityLiveData();

    @Query("SELECT * FROM VOICE_FILE_ENTITY")
    List<VoiceFileEntity> getAllEntity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVoiceFileEntity(VoiceFileEntity... voiceFileEntity);

    @Query("SELECT * FROM VOICE_FILE_ENTITY WHERE savePath LIKE:path")
    VoiceFileEntity getVoiceFileEntity(String path);
}
