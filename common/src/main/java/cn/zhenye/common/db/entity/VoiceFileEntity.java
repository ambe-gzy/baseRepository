package cn.zhenye.common.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author zhenye on 20191217
 */
@Entity(tableName = VoiceFileEntity.TABLE_NAME,indices = {@Index(value = {"savePath"},unique = true)})
public class VoiceFileEntity {
    public static final String TABLE_NAME = "voice_file_entity";

    @PrimaryKey(autoGenerate = true)
    public int id;

    //文件名
    public String fileName;

    //创建的时间戳
    public long createTimestamp;

    //保存目录
    public String savePath;

    //文件夹内的文件数量
    public int fileNum;

    public VoiceFileEntity(String fileName,long createTimestamp, String savePath, int fileNum){
        this.fileName = fileName;
        this.createTimestamp = createTimestamp;
        this.savePath = savePath;
        this.fileNum = fileNum;
    }

}
