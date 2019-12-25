package cn.zhenye.common.voicereverse;

public interface OnRecordListener {
    void onRecordPrepare();

    void onRecordStart(String savePath, String reverseSavePath);

    void onRecordStop(String savePath, String reverseSavePath);
}
