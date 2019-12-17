package cn.zhenye.voicereverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhenye.base.tool.StatusbarUtil;
import cn.zhenye.main.R;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class VoiceFileActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLlAddFile;
    private RecyclerView mFileRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_file_create);
        StatusbarUtil.setStatusBarTextColor(getWindow(),true);
        initUI();
        initRecyclerView();
    }

    private void initUI() {
        mLlAddFile = findViewById(R.id.ll_voice_file_create_add);
        mFileRecyclerView = findViewById(R.id.rv_voice_file_create_recycler_view);
    }

    private void initRecyclerView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_voice_file_create_add:
                
                break;
        }
    }
}
