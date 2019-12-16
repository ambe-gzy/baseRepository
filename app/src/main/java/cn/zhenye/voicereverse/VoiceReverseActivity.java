package cn.zhenye.voicereverse;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.tool.PermissionUtil;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.common.voicereverse.VoiceRecorderManager;
import cn.zhenye.main.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.security.Permissions;
import java.security.acl.Permission;


public class VoiceReverseActivity extends ZyCommonActivity implements View.OnClickListener {
    private VoiceRecorderManager mVoiceRecorderManager;
    private String mSavePath = new String();
    public static String mSavePathKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_reverse);
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        getToolbar().setVisibility(View.VISIBLE);
        PermissionUtil.requestRecordPermission(this);
        handleIntent();
        initUI();
        if (!requestPermission()){
            initRecorder();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断权限是否已获取
        if (requestCode == PermissionUtil.REQUEST_STORAGE_PERMISSION_AND_AUDIO){
            if (grantResults.length>0){
                boolean isSuccess = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        isSuccess = false;
                    }
                }
                if (isSuccess){
                    initRecorder();
                }else {
                    //todo 弹窗，告诉用户需要获取权限。
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_record_voice_start:
                if (mVoiceRecorderManager!=null && mSavePath != null){
                    mVoiceRecorderManager.startRecord(mSavePath,getFileName());
                }
                break;
            case R.id.tv_record_voice_stop:
                if (mVoiceRecorderManager!=null){
                    mVoiceRecorderManager.stopRecord();
                }
                break;
        }
    }

    private void handleIntent(){
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mSavePath = intent.getStringExtra(mSavePathKey);
    }

    private void initRecorder() {
        mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mVoiceRecorderManager = VoiceRecorderManager.getInstance();
    }

    private void initUI(){
        TextView mStartTv = findViewById(R.id.tv_record_voice_start);
        TextView mStopTv = findViewById(R.id.tv_record_voice_stop);
        mStartTv.setOnClickListener(this);
        mStopTv.setOnClickListener(this);
    }

    private boolean requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
         || ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //展示为什么要录音权限,存储权限
                PermissionUtil.requestRecordPermission(this);
            }else {
                PermissionUtil.requestRecordPermission(this);
            }
            return true;
        }else {
            return false;
        }
    }

    private String getFileName(){
        String name  = System.currentTimeMillis()+".wav";
        return name;
    }

}
