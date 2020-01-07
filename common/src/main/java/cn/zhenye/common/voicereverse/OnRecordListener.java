package cn.zhenye.common.voicereverse;

public interface OnRecordListener {
    void recordPrepare();

    void recordStart(String savePath, String reverseSavePath);

    void recordStop(String savePath, String reverseSavePath);
}
