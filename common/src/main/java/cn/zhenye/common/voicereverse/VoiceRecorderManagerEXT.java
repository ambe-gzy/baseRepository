package cn.zhenye.common.voicereverse;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Nullable;

import cn.zhenye.base.tool.ZThreadManager;

public class VoiceRecorderManagerEXT {
    private static String TAG = VoiceRecorderManagerEXT.class.getName();
    private static VoiceRecorderManagerEXT INSTANCE;

    //音频格式 wav，mp3
    private String VoiceFormat = ".wav";
    private AudioRecord audioRecord = null;

    //录音相关
    private boolean isRecording = false;
    private int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private int sampleRate = 8000;//设置采样频率
    private int bufferSizeInBytes;

    private String mSavePath = null;
    private String mReversePath = null;

    private VoiceRecorderManagerEXT(){
        initData();
    }

    /**
     * 初始化录音功能
     */
    private void initData() {
        //录音配置
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRate,
                channelConfiguration, audioEncoding); // need to be larger than size of a frame
        Log.i(TAG, "bufferSizeInBytes=" + bufferSizeInBytes);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,//配置AudioRecord
                sampleRate, channelConfiguration, audioEncoding,
                bufferSizeInBytes); //麦克风
    }

    public static VoiceRecorderManagerEXT getInstance() {
        try{
            if (INSTANCE == null){
                INSTANCE = new VoiceRecorderManagerEXT();
            }
            return INSTANCE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return INSTANCE;
    }

    public void startRecord(final String path, final String name, final OnRecordListener listener){
        if (path == null || name == null){
            Log.d(TAG,"path or name can't be null");
            return;
        }
        if (isRecording){
            return;
        }
        if (listener!=null){
            listener.recordPrepare();
        }
        Log.d(TAG,"保存目录:"+path+"\n保存名称"+name);
        ZThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                OutputStream outReverse = null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[bufferSizeInBytes];
                byte[] bufferReverse;
                int bufferReadResult;

                String filename = name;
                String reverseFileName;
                if (!name.contains(VoiceFormat)){
                    filename = name+VoiceFormat;
                    reverseFileName = name+"_reverse"+VoiceFormat;
                }else {
                    reverseFileName = System.currentTimeMillis()+"_reverse"+VoiceFormat;
                }
                //正放
                File recordingData = new File(path, filename);
                putSavePath(recordingData);
                //倒放
                File reverseRecordingData = new File(path,reverseFileName);
                putReverseSavePath(reverseRecordingData);
                try {
                    //开始录音
                    audioRecord.startRecording();
                    isRecording = true;
                    Log.d(TAG,"开始录音");
                    if (listener!=null){
                        listener.recordStart(getSavePath(),getReversePath());
                    }
                    while (isRecording) {
                        bufferReadResult = audioRecord.read(buffer, 0,
                                bufferSizeInBytes);
                        if(bufferReadResult>0){
                            baos.write(buffer, 0, bufferReadResult);
                        }
                    }

                    //录音结束，处理录音结果,保存为.wav格式音频
                    buffer = baos.toByteArray();
                    out = new FileOutputStream(recordingData);
                    out.write(getWavHeader(buffer.length));
                    out.write(buffer);

                    //todo 将字符串倒序。
                    bufferReverse = reverseBuffer(buffer);
                    outReverse = new FileOutputStream(reverseRecordingData);
                    outReverse.write(getWavHeader(bufferReverse.length));
                    outReverse.write(bufferReverse);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outReverse !=null){
                        try {
                            outReverse.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (listener!=null){
                        listener.recordStop(getSavePath(),getReversePath());
                    }
                }
            }
        });
    }

    public boolean stopRecord(){
        if (!isRecording){
            return false;
        }
        try {
            audioRecord.stop();
            isRecording = false;
            Log.d(TAG,"结束录音");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void putSavePath(File file){
        if (!file.exists()){
            try {
                if (file.createNewFile()){
                    mSavePath = null;
                    mSavePath = file.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void putReverseSavePath(File file){
        if (!file.exists()){
            try {
                if (file.createNewFile()){
                    mReversePath = null;
                    mReversePath = file.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRecord(){
        return isRecording;
    }

    @Nullable
    public String getSavePath(){
        return mSavePath;
    }

    public String getReversePath(){
        return mReversePath;
    }

    /**
     * getWavHeader
     * 錄音相關
     * 將錄音文件編碼成wav格式
     * startRecognize函數調用了本函數
     */
    private byte[] getWavHeader(long totalAudioLen){
        int mChannels = 1;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = sampleRate;
        long byteRate = sampleRate * 2 * mChannels;
        byte[] header = new byte[44];
        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) mChannels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * mChannels);  // block align
        header[33] = 0;
        header[34] = 16;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        return header;
    }

    private byte[] reverseBuffer(byte[] bytes){
        int current = bytes.length;
        byte[] newBytes = new byte[bytes.length];
        for (int i = 0;i<current;i++){
            Log.d(TAG,bytes[i]+"");
            newBytes[i] = bytes[current-i-1];
        }
        return newBytes;
    }

}
