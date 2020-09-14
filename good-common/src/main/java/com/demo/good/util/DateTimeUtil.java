package com.demo.good.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * Create By yaoping.ni
 */
public class DateTimeUtil {

    /**
     * 获取yyyy-MM-dd HH:mm:ss格式的时间
     */
    public static String getFormatDateTime() {
        return getFormatTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取yyyy-MM-dd格式的时间
     */
    public static String getFormatDate() {
        return getFormatTime(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取yyyy-MM-dd格式
     */
    public static String getFormat() {
        return "yyyy-MM-dd";
    }

    /**
     * 获取yyyy-MM-dd格式
     */
    public static String getFormat2() {
        return "yyyy-MM-dd HH:mm";
    }

    /**
     * 获取HH:mm格式的时间
     */
    public static String getFormatTime2() {
        return "HH:mm";
    }

    /**
     * 根据传入时间格式，获取当前时间
     */
    public static String getFormatNowTime(String format) {
        return getFormatTime(new Date(), format);
    }

    /**
     * 格式化Date为字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String formatDate2String(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 格式化Date为字符串，自定义格式类型
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate2String(Date date, String format) {
        if (date == null) return null;
        if (format == null || ("").equals(format)) return null;
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将Date转换所需要格式的日期字符串
     */
    public static String getFormatTime(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 将日期格式的字符串转换为日期
     *
     * @param date   源日期字符串
     * @param format 源日期字符串格式
     */
    public static Date formatStringToDate(String date, String format) {
        if (null == date || ("").equals(date)) return null;
        if (null == format || ("").equals(format)) return null;
        SimpleDateFormat format2 = new SimpleDateFormat(format);
        try {
            Date newDate = format2.parse(date);
            return newDate;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 获取前后日期 n为正数 向后推迟n天，负数时向前提前n天
     *
     * @param n
     * @param format
     * @return
     */
    public static String getAboutDay(int n, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        return sf.format(calendar.getTime());
    }

    /**
     * 获取指定日期前后n天日期 n为正数 向后推迟n天，负数时向前提前n天
     *
     * @param n
     * @param format
     * @return
     */
    public static String getAboutDay(String dataStr, int n, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = formatStringToDate(dataStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); //需要将date数据转移到Calender对象中操作
        calendar.add(calendar.DATE, n);//把日期往后增加n天.正数往后推,负数往前移动
        return sf.format(calendar.getTime());
    }

    /**
     * 获取前后月份 n为正数 向后推迟n月，负数时向前提前n月
     *
     * @param n
     * @param format
     * @return
     */
    public static String getAboutMonth(int n, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, n);
        return sf.format(calendar.getTime());
    }

    /**
     * 获取指定日期前后n月日期 n为正数向后推迟n月，负数时向前提前n月
     * @param n
     * @param format
     * @return
     */
    public static String getAboutMonth(String dataStr, int n, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = formatStringToDate(dataStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); //需要将date数据转移到Calender对象中操作
        calendar.add(calendar.MONTH, n);//把日期往后增加n天.正数往后推,负数往前移动
        return sf.format(calendar.getTime());
    }

    /**
     * 获取前后年 n为正数 向后推迟n年，负数时向前提前n年
     * @param n
     * @param format
     * @return
     */
    public static String getAboutYear(int n, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, n);
        return sf.format(calendar.getTime());
    }

    /**
     * 计算两个日期差月份
     *
     * @return
     */
    public static int getMonths(String time1, String time2) {
        Date date1 = formatStringToDate(time1, "yyyy-MM-dd");
        Date date2 = formatStringToDate(time2, "yyyy-MM-dd");

        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
                flag = 1;

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))
                        * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH)
                        - objCalendarDate1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 格式化时间戳为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatTimestamp2String(Timestamp date, String format) {
        if (date == null) return null;
        if (format == null || ("").equals(format)) return null;
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式化字符串为时间戳
     *
     * @param date
     * @param format
     * @return
     */
    public static Timestamp formatString2Timestamp(String date, String format) {
        return new Timestamp(formatStringToDate(date, format).getTime());
    }

    /**
     * 获取当前时间的时间戳（yyyy-MM-dd HH:mm:ss）
     *
     * @throws ParseException
     */
    public static String getTimestamp() {
        String res = null;
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String formatDate = formatDate2String(new Date(), format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(formatDate);
            long ts = date.getTime();
            res = String.valueOf(ts);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 获取指定时间的时间戳（ yyyy-MM-dd HH:mm:ss）
     *
     * @param dateStr
     * @throws ParseException
     */
    public static String getTimestamp(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dateStr);
        long ts = date.getTime();
        String res = String.valueOf(ts);
        return res;
    }

    /**
     * 计算2个日期之间的年数
     *
     * @param beginTime
     * @param endTime
     * @return 相差的年数
     */
    public static int getIntervalYears(String beginTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar from = Calendar.getInstance();
        from.setTime(sdf.parse(beginTime));
        Calendar to = Calendar.getInstance();
        to.setTime(sdf.parse(endTime));
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        return toYear - fromYear;
    }


    /**
     * 计算2个日期之间的天数
     * @param beginTime
     * @param endTime
     * @return 相差的天数
     * @throws ParseException
     */
    public static float getIntervalDays(String beginTime, String endTime) {
        float time = 0f;
        try {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            Date begin = s.parse(beginTime);
            Date end = s.parse(endTime);
            time = (end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /***
     * 计算2个日期之间相差的小时
     * @param beginTime
     * @param endTime
     * @param format
     * @return 相差的小时
     * @throws ParseException
     */
    public static long getIntervalHours(String beginTime, String endTime, String format) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Date begin = s.parse(beginTime);
        Date end = s.parse(endTime);
        long time = (end.getTime() - begin.getTime()) / 1000 / 60 / 60;
        return time;
    }

    /**
     * 计算2个日期之间相差的小时(自定义格式)
     *
     * @param beginTime
     * @param endTime
     * @param format
     * @return 相差的小时
     * @throws ParseException
     */
    public static BigDecimal getIntervalHours2(String beginTime, String endTime, String format) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Date begin = s.parse(beginTime);
        Date end = s.parse(endTime);
        BigDecimal d = BigDecimal.valueOf((end.getTime() - begin.getTime()) / 1000 / 60 / 60.0);
        return d;
    }

    /**
     * 计算2个日期之间相差的天数(自定义格式)
     *
     * @param beginTime
     * @param endTime
     * @param format
     * @return 相差的小时
     * @throws ParseException
     */
    public static long getIntervalDay(String beginTime, String endTime, String format) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Date begin = s.parse(beginTime);
        Date end = s.parse(endTime);
        long time = (end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24;
        return time;
    }

    /**
     * 计算2个日期之间相差的分钟(自定义格式)
     * @return 相差的分钟
     * @throws ParseException
     */
    public static long getIntervalMinutes(String beginTime, String endTime, String format) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Date begin = s.parse(beginTime);
        Date end = s.parse(endTime);
        long time = (end.getTime() - begin.getTime()) / 1000 / 60;
        return time;
    }

    /**
     * 计算2个日期之间相差的秒(自定义格式)
     * @return 相差的秒
     * @throws ParseException
     */
    public static long getIntervalSeconds(String beginTime, String endTime, String format) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat(format);
        Date begin = s.parse(beginTime);
        Date end = s.parse(endTime);
        long time = (end.getTime() - begin.getTime()) / 1000;
        return time;
    }

    /**
     * 比较时间大小（yyyy-MM-dd）
     */
    public static int compare_date(String startDateStr, String endDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = df.parse(startDateStr);
            Date endDate = df.parse(endDateStr);
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            if (startTime < endTime) {
                System.out.println("开始时间 < 结束时间");
                return 1;
            } else if (startTime > endTime) {
                System.out.println("开始时间 > 结束时间");
                return -1;
            } else {
                System.out.println("开始时间 = 结束时间");
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较时间大小（自定义时间类型）
     */
    public static int compare_date(String startDateStr, String endDateStr, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date startDate = df.parse(startDateStr);
            Date endDate = df.parse(endDateStr);
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            if (startTime < endTime) {
                System.out.println("开始时间 < 结束时间");
                return 1;
            } else if (startTime > endTime) {
                System.out.println("开始时间 > 结束时间");
                return -1;
            } else {
                System.out.println("开始时间 = 结束时间");
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 将long转成日期,再减去8个小时
     */
    public static String formatLongToString(long dateLong) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date(dateLong));
        ca.add(Calendar.HOUR_OF_DAY, -8);
        return f.format(ca.getTime());
    }

    /**
     * 将long转成日期
     */
    public static String formatLongToString2(long dateLong) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateLong));
    }

    /**
     * 获取某年第一天日期
     *
     * @param year
     * @return Date
     */
    public static Date getYearFirstDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year
     * @return Date
     */
    public static Date getYearLastDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取某段时间内的所有日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> findDates(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startDate);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endDate);
        // 测试此日期是否在指定日期之后
        while (endDate.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    /**
     * 获取周几（中文）
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static String getWeekDayChinese(String date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }

    /**
     * 获取周几：1－6代表周一到周六，0代表周日
     *
     * @param dataStr
     * @return
     */
    public static int getWeekDay(String dataStr) {
        Date date = formatStringToDate(dataStr, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }

    public static void main(String[] args) throws ParseException {
        String date = "2019-09-09".substring(0, 7);
        System.out.println(date);
    }

}
