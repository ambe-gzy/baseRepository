package cn.zhenye.common.voicereverse;

import android.app.Application;
import android.content.Context;
import android.media.AudioFormat;
import android.util.Log;

import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import VideoHandle.OnEditorListener;
import cn.zhenye.common.R;

public class AudioRecordManager {
    private String TAG = AudioRecordManager.class.getSimpleName();
    private static AudioRecordManager INSTANCE;
    private OnRecordListener mRecordListener;
    private String mSavePath;
    private String mReversePath;
    private boolean mRecording = false;
    private RecordConfig.RecordFormat mFormat = RecordConfig.RecordFormat.MP3;

    public static AudioRecordManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AudioRecordManager();

        }
        return INSTANCE;
    }

    public void init(Application application){
        RecordManager.getInstance().init(application,false);
        RecordManager.getInstance().changeFormat(mFormat);
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setSampleRate(16000));
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_8BIT));
        initListener();
    }

    public void start(String savePath){
        RecordManager.getInstance().changeRecordDir(savePath);
        RecordManager.getInstance().start();
    }

    public void pause(){
        RecordManager.getInstance().pause();
    }

    public void resume(){
        RecordManager.getInstance().resume();
    }

    public void stop(){
        RecordManager.getInstance().stop();
    }

    private void initListener(){
        RecordManager.getInstance().setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                Log.d(TAG,"RESULT");
                String reversePath = result.getAbsolutePath().replace(".mp3","_reverse.mp3");
                putSavePath(result);
                putReverseSavePath(new File(reversePath));
                AudioEditor.reverse(getSavePath(), getReversePath(), new OnEditorListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onProgress(float progress) {

                    }
                });
                if (mRecordListener!=null){
                    mRecordListener.onRecordStop(getSavePath(),getReversePath());
                }
            }
        });
        RecordManager.getInstance().setRecordStateListener(new RecordStateListener() {
            @Override
            public void onStateChange(RecordHelper.RecordState state) {
                switch (state){
                    case IDLE:
                        Log.d(TAG,"IDLE");
                        changeIsRecording(false);
                        break;
                    case RECORDING:
                        Log.d(TAG,"RECORDING");
                        if (mRecordListener!=null){
                            mRecordListener.onRecordStart(getSavePath(),getReversePath());
                        }
                        changeIsRecording(true);
                        break;
                    case STOP:
                        Log.d(TAG,"STOP");
                        changeIsRecording(false);
                        break;
                    case PAUSE:
                        Log.d(TAG,"PAUSE");
                        changeIsRecording(false);
                        break;
                    case FINISH:
                        Log.d(TAG,"FINISH");
                        changeIsRecording(false);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG,error);
            }
        });
    }

    public void setListener(OnRecordListener listener){
        mRecordListener = listener;
    }

    public void changeIsRecording(boolean isRecording){
            mRecording = isRecording;
    }

    private void putSavePath(File file){
        mSavePath = file.getAbsolutePath();
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

    public boolean isRecording(){
        return mRecording;
    }

    @Nullable
    public String getSavePath(){
        return mSavePath;
    }

    public String getReversePath(){
        return mReversePath;
    }
}
