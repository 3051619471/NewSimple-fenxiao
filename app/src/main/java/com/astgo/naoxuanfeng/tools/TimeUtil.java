package com.astgo.naoxuanfeng.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.astgo.naoxuanfeng.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/4/7.
 * 时间转换工具
 */

public class TimeUtil {

    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int WEEK = 7 * 24 * 60 * 60;// 周
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 当前日期与指定日期比较、、日期格式为yyyy-MM-dd

     */
    public static boolean isBigger(String str) {
        boolean isBigger = false;
        Log.e("过期时间",str);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > dt.getTime()) {
            isBigger = true;
        } else  {
            isBigger = false;
        }
        return isBigger;
    }
    /**
     * 获取当前日期MM-dd
     * @param time
     * @return
     */
    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(time));
    }

    /**
     * 转换时间（毫秒）为所在的星期
     */
    public static String getWeek(String strDate) {
        // 再转换为时间
        Date date = null;
        try {
            date = shortDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return new SimpleDateFormat("EEEE").format(calendar.getTime());
    }

    /**
     * 获取当前时间
     * @param time 格式HH:mm
     */
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 获取时间戳到现在的时间差
     * @param timestamp 时间戳
     */
    public static String date(Context context, long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒
        String timeStr;
        if (timeGap > DAY) {// 1天以上
            timeStr = getDate(timestamp) + "\n" + getTime(timestamp);
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = getTime(timestamp);
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + context.getString(R.string.time_util_minutes_ago);
        } else {// 1秒钟-59秒钟
            timeStr = context.getString(R.string.time_util_now);
        }
        return timeStr;
    }
    /**
     * 获取时间戳到现在的时间差(详情界面的格式)
     * @param timestamp 时间戳
     */
    public static String date1(Context context, long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒
        String timeStr;
        if (timeGap > DAY) {// 1天以上
            timeStr = getDate(timestamp) + " " + getTime(timestamp);
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = getTime(timestamp);
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + context.getString(R.string.time_util_minutes_ago);
        } else {// 1秒钟-59秒钟
            timeStr = context.getString(R.string.time_util_now);
        }
        return timeStr;
    }

    public TimeUtil() {
        super();
    }

    /**
     * 通话记录通话时长
     */
    public static String getDuration(Context context, long time) {
        long tempTime = time/1000;
        int hours = (int) (tempTime / HOUR);
        int minutes = (int) ((tempTime - hours * HOUR) / MINUTE);
        int seconds = (int) ((tempTime - hours * HOUR) % 60);

        if (hours > 0) {
            return hours + " " + context.getString(R.string.time_util_hours)
                    + minutes + " " + context.getString(R.string.time_util_minutes)
                    + seconds + " " + context.getString(R.string.time_util_seconds);
        } else {
            return minutes + " " + context.getString(R.string.time_util_min)
                    + seconds + " " + context.getString(R.string.time_util_sec);
        }
    }

    //将long时间戳转换为通话时长的格式
    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String res = getTwoLength(minute) + ":" + getTwoLength(second);
        if (hour != 0 ) res =  getTwoLength(hour) + ":" + res;
        return res;

    }
    //两位数的时间格式
    private static String getTwoLength(final int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }
}
