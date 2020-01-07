package cn.zhenye.base.tool;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

public class ZStatusbarUtils {
    private static int statusBarHeight = -1;

    /**
     * 设置状态栏背景颜色
     *
     * @param window
     * @param color
     */
    public static void setStatusBarBackgroundColor(Window window, @ColorInt int color) {
        window.setStatusBarColor(color);
    }

    /**
     * 设置状态栏字体是否为黑色，只允许6.0以上版本修改
     */
    public static void setStatusBarTextColor(Window window,boolean dark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(dark ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0);
        }
    }

    /**
     * 设置是否全屏显示内容（会与内容发生重叠，需要进行适配)只允许6.0以上使用全屏，防止字体颜色问题。
     */
    public static void setWindowFullScreenWithStatusBar(Window window, boolean fullscreen){
        if (fullscreen){
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    /**
     * 将dp转化为px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return (int) px;
    }

    /**
     * 获取statusbar高度
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight != -1) {
            return statusBarHeight;
        }

        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }



}
