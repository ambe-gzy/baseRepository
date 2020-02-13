package cn.zhenye.common.test;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;


import cn.zhenye.base.base.BaseFullScreenActivity;
import cn.zhenye.base.tool.ZFileUtils;
import cn.zhenye.common.R;

public class TestActivity extends BaseFullScreenActivity {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity);
        initUI();
    }

    private void initUI(){
        tv1 = findViewById(R.id.tv_test1);
        tv2 = findViewById(R.id.tv_test2);
        tv3 = findViewById(R.id.tv_test3);
        tv4 = findViewById(R.id.tv_test4);
        tv5 = findViewById(R.id.tv_test5);
        tv6 = findViewById(R.id.tv_test6);

        tv1.setText(String.format("尝试读取app保存目录:\n%s", ZFileUtils.getExternalAppDir()));
        tv2.setText(String.format("尝试读取缓存目录:\n%s",ZFileUtils.getCacheDir(getApplicationContext())));
        tv3.setText(String.format("读取Music目录: \n%s", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)));
        tv4.setText(String.format("读取根目录目录:\n%s",Environment.getRootDirectory()));
        tv5.setText(String.format("安卓Q读取目录： \n%s",getApplicationContext().getExternalCacheDir()));
        tv6.setText(String.format("安卓Q读取Music目录:  \n%s",getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)));
    }

}
