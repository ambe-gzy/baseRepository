package cn.zhenye.voicereverse;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhenye.main.R;

import android.os.Bundle;

public class VoiceFileCreateActivity extends AppCompatActivity {
    public static int VOICE_FILE_CREATE_KEY = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_file_create);
    }
}
