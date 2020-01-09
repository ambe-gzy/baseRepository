package cn.zhenye.common.voicereverse;

import android.app.Application;
import android.media.AudioFormat;
import android.util.Log;

import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import VideoHandle.OnEditorListener;

public class AudioRecordManager {
    public static int AUTHOR = 0;
    public static int CHALLENGER = 1;
    private int mSource = -1;

    private String TAG = AudioRecordManager.class.getSimpleName();
    private static AudioRecordManager INSTANCE;
    private List<OnRecordListener> mRecordListener = new ArrayList<>();
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

    public void start(String savePath,int source){
        RecordManager.getInstance().changeRecordDir(savePath);
        RecordManager.getInstance().start();
        mSource = source;
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
                    notifyListenerStop(getSavePath(),getReversePath());
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
                            notifyListenerStart(getSavePath(),getReversePath());
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

    private void notifyListenerStart(String savePath, String reverseSavePath){
        for (int i = 0;i< mRecordListener.size();i++){
            mRecordListener.get(i).recordStart(savePath, reverseSavePath);
        }
    }

    private void notifyListenerStop(String savePath, String reverseSavePath){
        for (int i = 0;i< mRecordListener.size();i++){
            mRecordListener.get(i).recordStop(savePath, reverseSavePath);
        }
    }

    public void registerListener(OnRecordListener listener){
        mRecordListener.add(listener);
    }

    public void removeListener(){
        mRecordListener.clear();
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
    private String getSavePath(){
        return mSavePath;
    }

    private String getReversePath(){
        return mReversePath;
    }

    public int getSource(){
        return mSource;
    }
}
