package cn.zhenye.common.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cn.zhenye.common.db.dao.AttendDao;
import cn.zhenye.common.db.dao.VoiceDao;
import cn.zhenye.common.db.dao.VoiceFileDao;
import cn.zhenye.common.db.entity.AttendEntity;
import cn.zhenye.common.db.entity.VoiceEntity;
import cn.zhenye.common.db.entity.VoiceFileEntity;

@Database(entities = {AttendEntity.class, VoiceFileEntity.class, VoiceEntity.class},version = 2,exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager mAppDatabase;

    private static final String DATABASE_NAME = "ambe.db";

    public static synchronized void init(Context context){
        if (mAppDatabase == null){
            mAppDatabase = Room.databaseBuilder(context,DatabaseManager.class,DATABASE_NAME)
                    .addMigrations(MigrationManager.getMigrations()).build();
        }
    }

    public static synchronized DatabaseManager getInstance(){
        if (mAppDatabase == null){
            throw new NullPointerException(" \n ************ \n 使用Database.getInstance()前你应该执行 DatabaseManager.init()方法进行初始化 \n ********** ");
        }
        return mAppDatabase;
    }

    public abstract AttendDao attendDao();

    public abstract VoiceFileDao voiceFileDao();

    public abstract VoiceDao voiceDao();
}
