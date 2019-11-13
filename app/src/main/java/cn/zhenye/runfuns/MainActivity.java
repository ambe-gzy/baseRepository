package cn.zhenye.runfuns;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhenye.base.cache.ZyCacheStorage;
import cn.zhenye.base.constants.ZyConstants;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZyCacheStorage.init(getApplicationContext());

    }

    public void btn1(View view) {
        int time  = 100;
        ZyCacheStorage.put(ZyConstants.KEY_HOME_LOGIN_TIME,time);
    }

    public void btn2(View view) {
        int time = ZyCacheStorage.getInt(ZyConstants.KEY_HOME_LOGIN_TIME,0);
    }
}
