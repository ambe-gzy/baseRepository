package cn.zhenye.base.tool;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {
    //权限获取
    public static final int REQUEST_STORAGE_PERMISSION_AND_AUDIO = 10;
    private static final String[] permissionsRecord = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    public static void requestRecordPermission(Activity activity){
        //请求权限
        ActivityCompat.requestPermissions(activity, permissionsRecord, REQUEST_STORAGE_PERMISSION_AND_AUDIO);
    }
}
