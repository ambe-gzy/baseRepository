package cn.zhenye.base.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZTimeUtils {
    public static String ACCURATE_TO_S = "yyyy-MM-dd HH:mm:ss";
    public static String ACCURATE_TO_DAY = "yyyy-MM-dd";
    public static String ACCURATE_TO_S_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static String ACCURATE_TO_DAY_CN = "yyyy年MM月dd日";
    public static String ACCURATE_TO_MIN = "yyyy年MM月dd日 HH时mm分";
    /**
     * 获取系统时间戳
     * @return
     */
    public long getCurTimeLong(){
        long time=System.currentTimeMillis();
        return time;
    }
    /**
     * 获取当前时间
     * @param pattern
     * @return
     */
    public static String getCurDate(String pattern){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 时间戳转换成字符窜
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 两个时间戳是否是同一天 时间戳是long型的（11或者13）
     * @param currentTime
     * @param lastTime
     * @return
     */
    public static boolean isSameData(String currentTime,String lastTime) {
        try {
            Calendar nowCal = Calendar.getInstance();
            Calendar dataCal = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            Long nowLong = Long.valueOf(currentTime);
            Long dataLong = Long.valueOf(lastTime);
            String data1 = df1.format(nowLong);
            String data2 = df2.format(dataLong);
            java.util.Date now = df1.parse(data1);
            java.util.Date date = df2.parse(data2);
            nowCal.setTime(now);
            dataCal.setTime(date);
            return isSameDay(nowCal, dataCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 两个时间戳是否是同一天 时间戳是long型的（11或者13）
     * @param currentTime
     * @param lastTime
     * @return
     */
    public static boolean isSameData(long currentTime,long lastTime) {
        try {
            Calendar nowCal = Calendar.getInstance();
            Calendar dataCal = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            String data1 = df1.format(currentTime);
            String data2 = df2.format(lastTime);
            java.util.Date now = df1.parse(data1);
            java.util.Date date = df2.parse(data2);
            nowCal.setTime(now);
            dataCal.setTime(date);
            return isSameDay(nowCal, dataCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            return false;
        }
    }

    /**
     * 转化为时分秒格式
     * @param seconds
     * @return
     */
    public static String secToTime(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int second = (seconds - hour * 3600 - minute * 60);

        StringBuilder  sb = new StringBuilder();
        if (hour > 0 && hour<10) {
            sb.append("0").append(hour).append(":");
        }else if (hour>=10){
            sb.append(hour).append(":");
        }
        if (minute >= 0 && minute<10) {
            sb.append("0").append(minute).append(":");
        }else if (minute>=10){
            sb.append(minute).append(":");
        }
        if (second >= 0 && second <10) {
            sb.append("0").append(second);
        }else {
            sb.append(second);
        }
        return sb.toString();
    }
    public static String secToTime(long seconds) {
        long hour = seconds / 3600;
        long minute = (seconds - hour * 3600) / 60;
        long second = (seconds - hour * 3600 - minute * 60);

        StringBuilder  sb = new StringBuilder();
        if (hour > 0 && hour<10) {
            sb.append("0").append(hour).append(":");
        }else if (hour>=10){
            sb.append(hour).append(":");
        }
        if (minute >= 0 && minute<10) {
            sb.append("0").append(minute).append(":");
        }else if (minute>=10){
            sb.append(minute).append(":");
        }
        if (second >= 0 && second <10) {
            sb.append("0").append(second);
        }else {
            sb.append(second);
        }
        return sb.toString();
    }
}
