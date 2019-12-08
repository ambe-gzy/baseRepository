package cn.zhenye.runfuns;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhenye.MainSupplement;
import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.common.db.DatabaseManager;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1(View view) {
        DatabaseManager.getInstance();
    }

    public void btn2(View view) {

    }
}
