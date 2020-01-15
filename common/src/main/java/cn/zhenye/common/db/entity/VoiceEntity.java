package cn.zhenye.common.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author zhenye on20191217
 */
@Entity(tableName = VoiceEntity.TABLE_NAME)
public class VoiceEntity {
    public static final String TABLE_NAME = "voice_reverse_entity";

    @PrimaryKey(autoGenerate = true)
    public int id;

    //出题者正常播放的语音保存地址
    public String normalVoicePath;

    //出题者倒放的语音保存地址
    public String normalReverseVoicePath;

    //挑战者正常播放的语音保存地址
    public String answerVoicePath;

    //挑战者倒放的语音保存地址
    public String answerReverseVoicePath;

    //保存时间
    public long createTimestamp;

    //保存路径
    public String savePath;

    //保存名字
    public String saveName;

    //是否显示
    public boolean isShow = false;

    public VoiceEntity(){}

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
