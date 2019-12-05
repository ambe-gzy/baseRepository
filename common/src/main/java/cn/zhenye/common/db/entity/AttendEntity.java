package cn.zhenye.common.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AttendEntity.TABLE_NAME)
public class AttendEntity {
    public static final String TABLE_NAME = "attend_entity";

    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * 签到的时间戳
     */
    public long timeStamp;

    /**
     * 签到的心情列表
     */
    public String message;

}
