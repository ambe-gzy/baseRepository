package cn.zhenye.base.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

public class ZActivityUtils {

    public static boolean safeStartActivityWithIntentClass(@NonNull Context context, @NonNull Class className) {
        return safeStartActivityWithIntent(context, new Intent(context, className));
    }

    public static boolean safeStartActivityWithIntent(@NonNull Context context, @NonNull Intent intent) {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean safeStartActivityWithIntent(@NonNull Activity activity, @NonNull Intent intent) {
        try {
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean safeStartActivityForResultWithIntent(@NonNull Activity activity, @NonNull Intent intent,
                                                               int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean safeStartActivityWithActivity(@NonNull Activity  fromActivity,@NonNull Class toActivity){
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(fromActivity.getApplicationContext(),toActivity);
            fromActivity.startActivity(intent);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void startTaobaoActivity(Context context){
        if (isInstallTaobao(context)) {
            try {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                        "com.taobao.taobao");
                context.startActivity(intent);
            } catch (Exception e) {
                ZToastUtils.showShort("打开淘宝失败");
            }
        } else {
            ZToastUtils.showShort("未安装淘宝");
        }
    }


    private static boolean isInstallTaobao(Context context){
        PackageInfo info;
        try {
            info = context.getApplicationContext().getPackageManager().getPackageInfo("com.taobao.taobao",0);
        } catch (PackageManager.NameNotFoundException e) {
            info = null;
            e.printStackTrace();
        }
        return info != null;
    }
}
