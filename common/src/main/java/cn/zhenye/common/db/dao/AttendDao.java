package cn.zhenye.common.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.zhenye.common.db.entity.AttendEntity;

@Dao
public interface AttendDao {

    @Query("SELECT * FROM ATTEND_ENTITY ")
    List<AttendEntity>  getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(AttendEntity... entity);
}
