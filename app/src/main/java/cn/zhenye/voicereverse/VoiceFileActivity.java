package cn.zhenye.voicereverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.main.R;

import android.os.Bundle;
import android.widget.LinearLayout;

public class VoiceFileActivity extends AppCompatActivity {
    private LinearLayout mLlAddFile;
    private RecyclerView mFileRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_file_create);
        initUI();
        initRecyclerView();
    }

    private void initUI() {
        mLlAddFile = findViewById(R.id.ll_voice_file_create_add);
        mFileRecyclerView = findViewById(R.id.rv_voice_file_create_recycler_view);
    }

    private void initRecyclerView() {

    }

}
