package cn.zhenye.voicereverse;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.appcommon.ZyCommonActivity;
import cn.zhenye.base.base.BaseFullScreenActivity;
import cn.zhenye.base.tool.ZActivityUtils;
import cn.zhenye.base.tool.ZPermissionUtils;
import cn.zhenye.base.tool.ZStatusbarUtils;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.dialog.CreateVoiceFileDialog;
import cn.zhenye.home.R;
import cn.zhenye.voicereverse.adapter.VoiceFileAdapter;
import cn.zhenye.voicereverse.vm.VoiceViewModel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class VoiceFileActivity extends ZyCommonActivity implements View.OnClickListener {
    private LinearLayout mLlAddFile;
    private RecyclerView mFileRecyclerView;
    private VoiceViewModel mVoiceFileViewModel;
    private VoiceFileAdapter mVoiceFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_file_create);
        ZStatusbarUtils.setStatusBarTextColor(getWindow(),true);
        initUI();
        initToolbar();
        initRecyclerView();
        initViewModel();
        ZPermissionUtils.requestRecordPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断权限是否已获取
        if (requestCode == ZPermissionUtils.REQUEST_STORAGE_PERMISSION_AND_AUDIO){
            if (grantResults.length>0){
                boolean isSuccess = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        isSuccess = false;
                    }
                }
                if (isSuccess){
                }else {
                    //todo 弹窗，告诉用户需要获取权限。
                    finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initToolbar(){
        initToolbar(getResources().getString(R.string.activity_voice_create_file));
    }

    private void initViewModel() {
        mVoiceFileViewModel =  ViewModelProviders.of(this).get(VoiceViewModel.class);
        mVoiceFileViewModel.getVoiceFileEntityLiveData().observe(this, new Observer<List<VoiceFileEntity>>() {
            @Override
            public void onChanged(List<VoiceFileEntity> voiceFileEntities) {
                mVoiceFileAdapter.refreshAdapter(voiceFileEntities);
            }
        });
    }

    private void initUI() {
        mLlAddFile = findViewById(R.id.ll_voice_file_create_add);
        mFileRecyclerView = findViewById(R.id.rv_voice_file_create_recycler_view);
        mLlAddFile.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mVoiceFileAdapter = new VoiceFileAdapter();
        mFileRecyclerView = findViewById(R.id.rv_voice_file_create_recycler_view);
        mFileRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mFileRecyclerView.setAdapter(mVoiceFileAdapter);
        mVoiceFileAdapter.setListenr(new VoiceFileAdapter.OnItemClickListener() {
            @Override
            public void onClick(VoiceFileEntity entity) {
                Intent intent = new Intent(VoiceFileActivity.this,VoiceReverseActivity.class);
                intent.putExtra(VoiceReverseActivity.SAVE_PATH_KEY,entity.savePath);
                ZActivityUtils.safeStartActivityWithIntent(getApplicationContext(),intent);
            }
        });
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_voice_file_create_add:
                CreateVoiceFileDialog.CreateVoiceFileDialog(getSupportFragmentManager());
                break;
        }
    }
}
