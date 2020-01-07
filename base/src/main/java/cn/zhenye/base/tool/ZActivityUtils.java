package cn.zhenye.base.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
}
