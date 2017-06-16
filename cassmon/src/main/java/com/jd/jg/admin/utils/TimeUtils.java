package com.jd.jg.admin.utils;

public class TimeUtils {

    public static String getReadableTime(int seconds){
        int day = seconds/86400;
        int hour = (seconds%86400)/3600;
        int min = (seconds%3600)/60;
        int sec = seconds%60;

        StringBuilder sb = new StringBuilder();
        if( day > 0 ) {
            sb.append(day).append("天");
        }
        if( hour > 0 ) {
            sb.append(hour).append("小时");
        }
        sb.append(min).append("分钟").append(sec).append("秒");
        return sb.toString();
    }
}
