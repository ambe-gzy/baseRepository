package cn.zhenye.common.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.zhenye.common.db.entity.VoiceEntity;

/**
 * @author zhenye on 20191217
 * 语音倒放挑战相关
 *
 */
@Dao
public interface VoiceDao {

    @Query("SELECT * FROM VOICE_REVERSE_ENTITY")
    LiveData<List<VoiceEntity>> getVoiceEntityListLivedata();

    @Query("SELECT * FROM VOICE_REVERSE_ENTITY ")
    List<VoiceEntity> getVoiceEntityList();

    @Query("SELECT * FROM VOICE_REVERSE_ENTITY WHERE savePath LIKE:path")
    LiveData<List<VoiceEntity>> getVoiceEntityListLiveDataByPath(String path);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVoiceEntity(VoiceEntity... entity);


}
