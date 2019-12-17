package cn.zhenye.common.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author zhenye on20191217
 */
@Entity(tableName = VoiceEntity.TABLE_NAME)
public class VoiceEntity {
    public static final String TABLE_NAME = "voice_reverse_entity";

    @PrimaryKey
    public int id;

    public String normalVoicePath;

    public String normalReverseVoicePath;

    public String answerVoicePath;

    public String answerReverseVoicePath;

    public long createTimestamp;

    public String savePath;

    public String saveName;

    public boolean isShow;

    public VoiceEntity(String normalVoicePath,String normalReverseVoicePath,
                       String answerVoicePath,String answerReverseVoicePath,
                       long createTimestamp,String savePath,String saveName,
                       boolean isShow){
        this.normalVoicePath = normalVoicePath;
        this.normalReverseVoicePath = normalReverseVoicePath;
        this.answerVoicePath = answerVoicePath;
        this.answerReverseVoicePath = answerReverseVoicePath;
        this.createTimestamp = createTimestamp;
        this.savePath = savePath;
        this.saveName = saveName;
        this.isShow = isShow;
    }

}
