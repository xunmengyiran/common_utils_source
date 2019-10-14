package com.xunmengyiran.utils;


import com.xunmengyiran.Constants;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 爽见项目工具包
 */
public class SJUtils {

    /**
     * 根据生日计算年龄，只计算到年份
     *
     * @param birthday
     */
    public static Integer calculatingAge(String birthday) {
        int birthday_year = Integer.parseInt(birthday.substring(0, 4));
        int now_year = Integer.parseInt(Constants.Date_Format.sdf0.format(new Date()));
        return now_year - birthday_year;
    }

    /**
     * 根据生日计算年龄，只计算到年份
     *
     * @param birthday
     */
    public static Integer calculatingAge(Date birthday) {
        int birthday_year = Integer.parseInt(Constants.Date_Format.sdf0.format(birthday));
        int now_year = Integer.parseInt(Constants.Date_Format.sdf0.format(new Date()));
        return now_year - birthday_year;
    }

    /**
     * 从1970-01-01加上天数 转为日期类型
     *
     * @param day
     * @return
     */
    public static Date dayToDate(long day) {
        Date date = null;
        try {
            date = Constants.Date_Format.sdf4.parse("1970-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate = null;
        try {
            newDate = addDate(date, day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 日期加天数
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * 根据经度纬度计算距离 返回 M
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static Double getDistanceByLongitudeAndLatitude(double lng1, double lat1, double lng2, double lat2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return Constants.EARTH_RADIUS * acos; // 最终结果
    }

    public static void main(String[] args) {
        double distance = 22900;
        DecimalFormat df = new DecimalFormat("#0.0");
        System.out.println(df.format(distance / 1000)+"km");
    }
}
