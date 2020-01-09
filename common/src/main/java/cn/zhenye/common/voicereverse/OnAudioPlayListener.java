package cn.zhenye.common.voicereverse;

import android.media.TimedText;
import android.widget.TextView;

public interface OnAudioPlayListener {
    void audioStart(TextView btn);
    void audioPlaying(TimedText timedText);
    void audioStop(TextView btn);
    void audioPlayError(String message);
}
