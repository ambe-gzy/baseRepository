package cn.zhenye.base.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by augustdong on 17/6/28.
 */

public class NetworkUtil {

    private static final String TAG = NetworkUtil.class.getSimpleName();

    private static final int TYPE_NO_NETWORK = 1;
    private static final int TYPE_WIFI = 2;
    private static final int TYPE_2G = 3;
    private static final int TYPE_3G = 4;
    private static final int TYPE_4G = 5;

    public static boolean isNetworkConnected(@NonNull Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null) {
            return false;
        }

        if (info.isConnected()) {
            return true;
        }

        return false;
    }

    public static int getNetworkType(@NonNull Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null) {
            return TYPE_NO_NETWORK;
        }

        if (!info.isConnected()) {
            return TYPE_NO_NETWORK;
        }

        return getNetworkType(info);
    }

    public static InetAddress parseNumericAddress(String numericAddress) {
        try {
            Method parseNumericAddressMethod = InetAddress.class.getMethod("parseNumericAddress", String.class);

            return (InetAddress) parseNumericAddressMethod.invoke(null, numericAddress);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "InetAddress class has no parseNumericAddress method", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "InetAddress class has no parseNumericAddress method", e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.e(TAG, "InetAddress class has no parseNumericAddress method", e);
        }

        return null;
    }

    private static int getNetworkType(NetworkInfo info) {
        if (info == null) {
            return TYPE_NO_NETWORK;
        }

        int type = TYPE_NO_NETWORK;

        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                type = TYPE_WIFI;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                type = getMobileNetType(info.getSubtype());
                break;
            default:
                break;
        }

        return type;
    }

    private static int getMobileNetType(int subtype) {
        switch (subtype) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return TYPE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return TYPE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return TYPE_4G;
        }

        return TYPE_NO_NETWORK;
    }

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }

        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectMgr == null) {
                return null;
            }

            return connectMgr.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void showNetworkUnavailableToast(Context context) {
        ZToastUtils.showShort("Network unavailable, please open the network!");
    }

    public static boolean isVpnUsed() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if (niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {}
        return false;
    }

}
