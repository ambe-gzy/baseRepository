package cn.zhenye.voicereverse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.base.tool.ActivityUtil;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.common.db.entity.VoiceFileEntity;
import cn.zhenye.dialog.CreateVoiceFileDialog;
import cn.zhenye.main.R;
import cn.zhenye.voicereverse.adapter.VoiceFileAdapter;
import cn.zhenye.voicereverse.vm.VoiceFileViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class VoiceFileActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLlAddFile;
    private RecyclerView mFileRecyclerView;
    private VoiceFileViewModel mVoiceFileViewModel;
    private VoiceFileAdapter mVoiceFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_file_create);
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        initUI();
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mVoiceFileViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(VoiceFileViewModel.class);
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
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_voice_file_create_add:
                CreateVoiceFileDialog dialog = new CreateVoiceFileDialog();
                dialog.show(getSupportFragmentManager(),"cool");
                break;
        }
    }
}
