package com.chx.chat.utils;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author intel小陈
 * @date 2023年08月03日 14:55
 */
public class TimeUtil {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_SHANGHAI = "Asia/Shanghai";

    public static void main(String[] args) {
        before30Days();
    }


    public static LocalDateTime now(){
        return LocalDateTime.now(ZoneId.of(TIME_SHANGHAI));
    }

    public static String nowString(){
        return LocalDateTime.now(ZoneId.of(TIME_SHANGHAI)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean isOneDayApart(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        LocalDate date1 = dateTime1.toLocalDate();    // 提取日期部分
        LocalDate date2 = dateTime2.toLocalDate();    // 提取日期部分

        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        return daysBetween >= 1;
    }

    public static boolean expired(LocalDateTime targetDateTime){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 提取目标日期
        LocalDate targetDate = targetDateTime.toLocalDate();

        // 判断目标日期是否与当前日期相等
        if (targetDate.equals(currentDate)) {
            return true;
        } else {
            return false;
        }
    }



    // 超过15分钟返回false， 否则返回true
    public static boolean compareTime15(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null) return false;
        Duration duration = Duration.between(time1, time2);
        // 获取时间差的分钟数
        long minutes = Math.abs(duration.toMinutes());

        if (minutes > 15) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean compareTime5(LocalDateTime time1, LocalDateTime time2) {
        Duration duration = Duration.between(time1, time2);
        System.out.println(time1.getHour() + ":" + time1.getMinute());
        System.out.println(time2.getHour() + ":" + time2.getMinute());
        // 获取时间差的分钟数
        long minutes = Math.abs(duration.toMinutes());

        if (minutes > 5) {
            return false;
        } else {
            return true;
        }
    }

    public static LocalDateTime compareTime30Days(LocalDateTime now,int months) {
        // 加上30天
        LocalDateTime futureDate = now.plusDays(30 * months);

        // 时间格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 打印当前时间和加上30天后的时间
        return futureDate;
    }

    public static LocalDateTime minus30Days(LocalDateTime dateTime,Integer months){
        // 创建一个 LocalDateTime 对象表示当前时间
        System.out.println("当前时间：" + dateTime);

        // 将当前时间减去 30 天
        LocalDateTime before30Days = dateTime.minusDays(30 * months);
        System.out.println("30 天前的时间：" + before30Days);
        return before30Days;
    }


    public static String before30Days() {
        LocalDateTime now = now();
        LocalDateTime localDateTime = now.minusDays(30);
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
        return null;
    }
}
