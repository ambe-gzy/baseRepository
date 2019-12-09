package cn.zhenye.runfuns;

import androidx.appcompat.app.AppCompatActivity;

import cn.zhenye.base.tool.ThreadManager;
import cn.zhenye.common.db.DatabaseManager;
import cn.zhenye.common.db.dao.AttendDao;
import cn.zhenye.common.db.entity.AttendEntity;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    AttendDao attendDao;
    public void btn1(View view) {
        attendDao = DatabaseManager.getInstance().attendDao();
        AttendEntity entity = new AttendEntity();
        entity.message = "cool";
        entity.timeStamp = 995588;
        ThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                attendDao.insertData();
            }
        });

    }

    public void btn2(View view) {
        ThreadManager.getNormal().execute(new Runnable() {
            @Override
            public void run() {
                attendDao.getAll();
            }
        });
    }
}
