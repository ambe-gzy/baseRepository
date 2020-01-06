package cn.zhenye.common.voicereverse;

import android.text.TextUtils;
import android.util.Log;

import Jni.FFmpegCmd;
import VideoHandle.CmdList;
import VideoHandle.OnEditorListener;

/**
 * @author zhenye on 20200106
 * ffmpeg命令：
 * -y    覆盖输出文件
 * -i    输入处理的文件路径
 * -b：  $k -bufsize: $k 输出文件的比特率,e.g:-b 64k -bufsize: 64k ,比特率设置为64k
 * -r    强制输出文件的帧频 e.g: -r 24,帧频设置为24
 * -c:v  e.g:-c:v libx264
 * -formats 显示可用的格式，编解码的、协议的
 *
 *
 *
 * 将音频倒放
 */
public class AudioEditor {
    private static String TAG = AudioEditor.class.getSimpleName();

    /**
     * 音频倒放
     *
     * @param audioInput
     * @param audioOutput
     * @param listener
     */
    public static void reverse(String audioInput, String audioOutput, OnEditorListener listener) {
        if (audioInput.equals(audioOutput)) {
            Log.e(TAG, "you can't put the same path of input path and output path.");
            listener.onFailure();
            return;
        }
        if (TextUtils.isEmpty(audioInput) || TextUtils.isEmpty(audioOutput)) {
            Log.e(TAG,"the path is null.");
            listener.onFailure();
            return;
        }
        CmdList cmd = new CmdList();
        cmd.append("ffmpeg").append("-y").append("-i").append(audioInput).append("-af").append("areverse").append(audioOutput);
        String[] cmds = cmd.toArray(new String[cmd.size()]);
        FFmpegCmd.exec(cmds, 0, listener);
    }

}
