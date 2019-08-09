
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static Log log = LogFactory.getLog(DateUtils.class);

    public static final String FORMATER_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATER_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:permission";
    public static final String FORMATERYYYYMMDD = "yyyyMMdd";
    //    public static final String FORMATER_YYYYMMDD = "yyyy/MM/dd";
    public static final String FORMATER_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";
    private static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:permission";
    private static final String FORMAT_YMDHMS5 = "yyyy-MM-dd HH:mm:permission.SSS";
    public static final String FORMAT_YMDHMS_STR = "yyyy年MM月dd日";
    public static final String FORMAT_YMDHMS = "yyyyMMddHHmmss";

    /**
     * 返回java.sql.Date类型的当前时间
     *
     * @return
     */
    public static java.sql.Date getSqlDate() {
        return getSqlDate(new Date());
    }

    /**
     * 返回java.sql.Date类型的时间
     *
     * @param date
     * @return
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 以"yyyy-MM-dd"格式来格式化日期
     *
     * @param date
     * @return
     */
    public static String formatFromDate(Date date) {
        return formatFromDate(FORMATER_YYYY_MM_DD, date);
    }

    public static String formatyyyyMMddDate(Date date) {
        return formatFromDate(FORMATERYYYYMMDD, date);
    }

    /**
     * 将日期转成  yyyy/MM/dd
     *
     * @author shisan
     * @date 2017/10/11 下午5:48
     */
    public static String getYMD(Date date) {
        return formatFromDate(FORMATER_YYYYMMDD, date);
    }

    public static Date getCurrentDate() {
        Date now = getCurrentDateTime();
        return strToDate(dateToStr(now));
    }

    public static String dateToStr(Date date) {
        String s = "";
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            s = sdf.format(date);
        } catch (Exception e) {

        }

        return s;
    }

    public static String dateToYMDHMS(Date date) {
        return dateToStr(date, FORMATER_YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateToStr(Date date, String f) {
        String s = "";
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(f);
        try {
            s = sdf.format(date);
        } catch (Exception e) {

        }

        return s;
    }

    public static Date strToDate(String s) {
        Date d = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = sdf.parse(s);
        } catch (Exception e) {

        }

        return d;
    }


    public static Date strToDate(String s, String f) {
        Date d = null;

        SimpleDateFormat sdf = new SimpleDateFormat(f);
        try {
            d = sdf.parse(s);
        } catch (Exception e) {

        }

        return d;
    }

    /**
     * @return
     */
    public static Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 按照给定的格式，格式化日期
     *
     * @param formater 需要的格式，常用的例如"yyyy-MM-dd HH:mm:permission"
     * @param date     日期
     * @return
     */
    public static String formatFromDate(String formater, Date date) {
        DateFormat df = new SimpleDateFormat(formater);
        return df.format(date);
    }

    /**
     * 按照给定的格式，格式化日期
     *
     * @param formater 需要的格式，常用的例如"yyyy-MM-dd HH:mm:permission"
     * @param s        可格式化为日期的字符串
     * @return
     */
    public static String formatFromString(String formater, String s) {
        DateFormat df = new SimpleDateFormat(formater);
        return df.format(s);
    }

    /**
     * 获取当前时间 （yyyy-MM-dd HH:mm:permission）
     *
     * @return 当前时间 （yyyy-MM-dd HH:mm:permission）
     */
    public static String current() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:permission");
        return dateFormat.format(date);

    }

    /**
     * 获取当前时间 （yyyy年MM月dd日）
     *
     * @return 当前时间 （yyyy年MM月dd日）
     */
    public static String currentYMD() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(date);

    }

    /**
     * 字符串转化为日期</br>
     *
     * @param str    需要被转换为日期的字符串
     * @param format 格式，常用的为 yyyy-MM-dd HH:mm:permission
     * @return java.util.Date，如果出错会返回null
     */
    public static Date StringToDate(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("将字符串转换成日期时出错", e);
            }
        }
        return date;
    }

    /**
     * 字符串转化为日期</br>
     *
     * @param str 需要被转换为日期的字符串
     * @return java.util.Date，如果出错会返回null
     */
    public static Date StringToDate(String str) {
        return StringToDate(str, "yyyy-MM-dd HH:mm:permission");
    }

    /**
     * 计算两个日期之间的天数</br>
     * 任何一个参数传空都会返回-1</br>
     * 返回两个日期的时间差，不关心两个日期的先后</br>
     *
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static long getDaysBetweenTwoDate(Date dateStart, Date dateEnd) {
        if (null == dateStart || null == dateEnd) {
            return -1;
        }
        long l = Math.abs(dateStart.getTime() - dateEnd.getTime());
        l = l / (1000 * 60 * 60 * 24l);
        return l;
    }

    /**
     * 计算两个日期之间的分钟数</br>
     * 任何一个参数传空都会返回-1</br>
     * 返回两个日期的时间差，不关心两个日期的先后</br>
     *
     * @param dateStart
     * @param dateEnd
     * @return
     * @author Saber
     * Date 2017/4/18 14:07
     */
    public static long getMinutesBetweenTwoDate(Date dateStart, Date dateEnd) {
        if (null == dateStart || null == dateEnd) {
            return -1;
        }
        long l = Math.abs(dateStart.getTime() - dateEnd.getTime());
        l = l / (1000 * 60);
        return l;
    }

    /**
     * 判断某字符串是否是日期类型
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前日期是周几<br>
     *
     * @param date
     * @return 当前日期是周几
     */
    public static String getWeekOfDate2(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前日期是周几<br>
     *
     * @param date
     * @return 当前日期是星期 几
     */
    public static String getWeekOfDate3(Date date) {

        if (date == null) return null;

        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }

    /**
     * 获取 当前年月日 周几
     *
     * @return 当前年月日 周几
     */
    public static String getDateAndWeek() {
        return currentYMD() + " " + getWeekOfDate2(new Date());
    }

    public static long getUnixDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:permission");
        Date d = new Date();
        String t = df.format(d);
        long epoch = 0;
        try {
            epoch = df.parse(t).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("t is ：" + t + ",unix stamp is " + epoch);
        return epoch;
    }

    /**
     * 获取给定日期的年月日 周几
     *
     * @param yyyyMMdd 20160509
     * @return xxxx年xx月xx日 周x
     */
    public static String getYyyyMMddAndWeeekDay(Integer yyyyMMdd) {

        if (yyyyMMdd == null && yyyyMMdd.toString().length() != 8 && yyyyMMdd.toString().length() != 6) {
            return null;
        }
        String _date = yyyyMMdd.toString();

        String yyyy = _date.substring(0, 4);
        String MM = _date.substring(4, 6);
        String dd = _date.substring(6, 8);

        String currentDate = yyyy + "月" + MM + "月" + dd + "日";
        Date date = StringToDate(yyyyMMdd.toString(), "yyyyMMdd");
        return currentDate + " " + getWeekOfDate2(date);
    }

    /**
     * 获取昨天的日期
     *
     * @return
     */
    public static String getYestDate() {
        SimpleDateFormat df = new SimpleDateFormat(FORMATER_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return df.format(calendar.getTime());
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Long getStartTimeLong() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }


    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Long getEndTimeLong() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }


    /**
     * 获取昨天开始时间
     *
     * @return
     */
    public static Long getYesterdayStartTimeLong() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime().getTime();
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date getStartTime() {
        return new Date(getStartTimeLong());
    }

    /**
     * 获取当天的开始时间（UTC）
     *
     * @return UTC时间
     */
    public static Date getStartTime2UTC() {
        return GMT82UTC(new Date(getStartTimeLong()));
    }

    /**
     * 获取昨天开始时间
     *
     * @return
     */
    public static Date getYesterdayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取昨天的开始时间（UTC）
     *
     * @return UTC
     */
    public static Date getYesterdayStartTime2UTC() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date GMT8date = calendar.getTime();
        return GMT82UTC(GMT8date);
    }

    /**
     * 获取昨天的结束时间
     *
     * @return UTC
     */
    public static Date getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Date getEndTime() {
        return new Date(getEndTimeLong());
    }

    /**
     * 两个时区转换
     *
     * @param sourceDate     源时间
     * @param formatter      转换的时间类型
     * @param sourceTimeZone 源时区
     * @param targetTimeZone 要转换的时区
     * @return 新时区的时间
     */
    public static String dateTransformBetweenTimeZone(Date sourceDate, DateFormat formatter,
                                                      TimeZone sourceTimeZone, TimeZone targetTimeZone) {
        Long targetTime = sourceDate.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();
        return getTime(new Date(targetTime), formatter);
    }


    public static String getTime(Date date, DateFormat formatter) {
        return formatter.format(date);
    }

    /**
     * UTC--->GMT8
     *
     * @param sourceTime long
     * @return
     */
    public static Date UTC2GMT8(long sourceTime) {
        DateFormat formatter = new SimpleDateFormat(FORMATER_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date(sourceTime);
        //Date date = Calendar.getInstance().getTime();
        TimeZone srcTimeZone = TimeZone.getTimeZone("UTC");
        //TimeZone srcTimeZone = TimeZone.getTimeZone("CST");
        TimeZone destTimeZone = TimeZone.getTimeZone("GMT+8");
        return StringToDate(dateTransformBetweenTimeZone(date, formatter, srcTimeZone, destTimeZone));
    }

    /**
     * UTC--->GMT8
     *
     * @param sourceTime Date
     * @return
     */
    public static Date UTC2GMT8(Date sourceTime) {
        if (sourceTime == null) return null;
        DateFormat formatter = new SimpleDateFormat(FORMATER_YYYY_MM_DD_HH_MM_SS);
        //Date date = Calendar.getInstance().getTime();
        TimeZone srcTimeZone = TimeZone.getTimeZone("UTC");
        //TimeZone srcTimeZone = TimeZone.getTimeZone("CST");
        TimeZone destTimeZone = TimeZone.getTimeZone("GMT+8");
        return StringToDate(dateTransformBetweenTimeZone(sourceTime, formatter, srcTimeZone, destTimeZone));
    }

    /**
     * GMT8--->UTC
     *
     * @param sourceTime Date
     * @return
     */
    public static Date GMT82UTC(Date sourceTime) {
        if (sourceTime == null) return null;
        DateFormat formatter = new SimpleDateFormat(FORMATER_YYYY_MM_DD_HH_MM_SS);
        //Date date = Calendar.getInstance().getTime();
        TimeZone srcTimeZone = TimeZone.getTimeZone("GMT+8");
        //TimeZone srcTimeZone = TimeZone.getTimeZone("CST");
        TimeZone destTimeZone = TimeZone.getTimeZone("UTC");
        return StringToDate(dateTransformBetweenTimeZone(sourceTime, formatter, srcTimeZone, destTimeZone));
    }

    /**
     * 获取月份
     *
     * @return 四月
     */
    public static String getMonth() {
        String month[] = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        return month[new Date().getMonth()];
    }

    /**
     * 获取指定日期所在月份的天数集合
     *
     * @param date 2016-04-10
     * @return
     */
    public static int[] getMonthDays(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int[] days = new int[maxDay];
        for (int i = 1; i <= maxDay; i++) {
            days[i - 1] = i;
        }
        return days;
    }

    /**
     * 获取指定日期所在月份的天数集合
     *
     * @param date 20160430 201604
     * @return
     */
    public static int[] getMonthDays(Integer date) {
        Calendar calendar = Calendar.getInstance();

        if (date == null && date.toString().length() != 8 && date.toString().length() != 6) {
            return null;
        }
        String _date = date.toString();

        Integer yyyy = Integer.parseInt(_date.substring(0, 4));
        Integer MM = Integer.parseInt(_date.substring(4, 6));
        //System.out.println("当前年份："+_date.substring(0, 4));
        //System.out.println("当前月份："+Integer.parseInt(_date.substring(4, 6)));

        calendar.set(Calendar.YEAR, yyyy);
        calendar.set(Calendar.MONTH, MM);
        int maxDay = getMonthMaxDay(yyyy, MM);
        //int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //计算错误
        int[] days = new int[maxDay];
        for (int i = 1; i <= maxDay; i++) {
            days[i - 1] = i;
        }
        return days;
    }

    /**
     * 获取指定年月的最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthMaxDay(int year, int month) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int last = cal.getActualMaximum(Calendar.DATE);
        return last;
    }

    /**
     * yyyyMMdd --> yyyy_MM_dd
     *
     * @param yyyyMMdd
     * @return
     */
    public static String yyyyMMdd2yyyy_MM_dd(Integer yyyyMMdd) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(yyyyMMdd.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sfstr;
    }

    /**
     * 获取当前时间的UTC时间
     *
     * @return
     */
    public static Date getCurrentUTCTime() {
        return DateUtils.GMT82UTC(new Timestamp(System.currentTimeMillis()));
    }


    /**
     * 获取已经过去的本月的日期时间
     *
     * @return
     */
    public static List<Date> getMonthPastDate(String paramYm) {

        List<Date> result = new ArrayList<>();

        if (org.apache.commons.lang3.StringUtils.isBlank(paramYm)) return result;

        Date today = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String todayStr = sdf.format(today);

        Calendar c = Calendar.getInstance();

        int pastDays = 0;

        try {
            if (paramYm.trim().equals(todayStr)) {
                c.setTime(today);
                pastDays = c.get(Calendar.DAY_OF_MONTH);
            } else {
                c.setTime(sdf.parse(paramYm));
                pastDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= pastDays; i++) {
            c.set(Calendar.DAY_OF_MONTH, i);
            result.add(c.getTime());
        }
        return result;
    }

    public static String getFirstDayOfMonth(Integer year, Integer month) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        month = month != null ? month - 1 : calendar.get(Calendar.MONTH);
        calendar.set(year, month, 1);
        String day_first = format.format(calendar.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    public static String getLastDayOfMonth(Integer year, Integer month) {
        Date cur_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:permission");
        if (cur_date.getMonth() == Integer.valueOf(month) - 1) {
            return sdf.format(cur_date);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(cal.getTime()) + " 23:59:59";
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smalldate 较小的时间
     * @param bigdate   较大的时间
     * @return 相差天数
     * @throws ParseException
     * @author pine
     * @Date 2017/03/15 下午3:15
     */
    public static int daysBetween(Date smalldate, Date bigdate) {
        int az = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smalldate = sdf.parse(sdf.format(smalldate));
            bigdate = sdf.parse(sdf.format(bigdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smalldate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bigdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            az = Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            log.error("解析异常,", e);
        }

        return az;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smalldate 较小的时间
     * @param bigdate   较大的时间
     * @return 相差天数
     * @throws ParseException
     * @author pine
     * @Date 2017/03/15 下午3:15
     */
    public static int daysBetween(String smalldate, String bigdate) {
        int az;
        try {
            Date date1 = strToDate(smalldate, FORMATER_YYYYMMDD);
            Date date2 = strToDate(bigdate, FORMATER_YYYYMMDD);

            Calendar d1 = new GregorianCalendar();
            d1.setTime(date1);
            Calendar d2 = new GregorianCalendar();
            d2.setTime(date2);
            az = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
            int y2 = d2.get(Calendar.YEAR);
            if (d1.get(Calendar.YEAR) != y2) {
                do {
                    az += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                    d1.add(Calendar.YEAR, 1);
                } while (d1.get(Calendar.YEAR) != y2);
            }
            return az;
        } catch (Exception e) {
            log.error("解析异常,", e);
            az = 0;
        }
        return az;
    }

    /**
     * 根据日期获取该日期的开始日期时间点
     *
     * @return 日期的开始日期时间点(示例 : 2016 - 9 - 26 00 : 00 : 00)
     * @author ZKT
     * @createTime 2012-9-26
     */
    public static Date getDateStartDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * @param date1 时间1
     * @param date2 时间2
     * @return 比较两个时间的大小
     */
    public static boolean compareDate(Date date1, Date date2) {

        return date1.getTime() - date2.getTime() > 0 ? true : false;
    }

    /**
     * @param date1 时间1
     * @param date2 时间2
     * @return 返回小的时间
     */
    public static Date compareMinDate(Date date1, Date date2) {
        if (date1.getTime() < date2.getTime()) {
            return date1;
        } else if (date1.getTime() > date2.getTime()) {
            return date2;
        }
        return date1;
    }

    /**
     * 根据日期获取该日期的结束日期时间点
     *
     * @return 日期的结束日期时间点(示例 : 2016 - 9 - 26 23 : 59 : 59)
     * @author ZKT
     * @createTime 2012-9-26
     */
    public static Date getDateEndDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmssSSS为格式的当前系统时间
     */
    public static String getOrderNum() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(FORMAT_YMDHMSS);
        return df.format(date);
    }

    /**
     * 获取当前服务器时间的几天后的时间
     *
     * @return 几天后的时间
     */
    public static Date getSeveralDaysLaterDate(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + days);
        return c.getTime();
    }

    /**
     * 获取指定日期几天后的时间
     *
     * @param date 指定日期(为空则为当前服务器时间)
     * @param days 天数
     * @return 几天后的时间
     */
    public static Date getSeveralDaysLaterDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date == null ? new Date() : date);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + days);
        return c.getTime();
    }

    public static String getSeveralDaysLaterString(String date, int days) {
        Date d = getSeveralDaysLaterDate(strToDate(date, FORMATER_YYYYMMDD), days);
        return dateToStr(d, FORMATER_YYYYMMDD);
    }

    /**
     * 获得date时间
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:permission
     */
    public static String getDisplayYMDHMS(Date date) {
        return new SimpleDateFormat(FORMAT_YMD_HMS).format(date);
    }

    /**
     * 根据指定的时间来获取指定的秒数之后的时间
     *
     * @return 指定的秒数之后的时间
     */
    public static Date getSecondLaterDate(Date date, Integer second) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + second);
        return c.getTime();
    }

    /**
     * 时间戳转换为yyyy-MM-dd HH:mm:permission:SSS 格式日期
     *
     * @param timestamp 时间戳
     * @return 日期
     * @Author pine
     * @CreateDate 2015年8月5日
     */
    public static Date getDateFromTimestamp5(Long timestamp) {
        try {
            return new SimpleDateFormat(FORMAT_YMDHMS5).parse(new SimpleDateFormat(FORMAT_YMDHMS5).format(timestamp));
        } catch (ParseException e) {
            return null;
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_YMD_HMS);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取当前时间戳
     */

    public static Long getCurrentTimeMillis() {
        return (System.currentTimeMillis() / 1000);
    }

    /**
     * 根据日期获取该日期的开始日期时间点
     *
     * @return 日期的开始日期时间点(示例 : 2016 - 9 - 26 00 : 00 : 00)
     * @author ZKT
     * @createTime 2012-9-26
     */
    public static Date getDateStartDateTime(String date) {
        return getDateStartDateTime(strToDate(date, FORMATER_YYYYMMDD));
    }

    /**
     * 根据日期获取该日期的结束日期时间点
     *
     * @return 日期的结束日期时间点(示例 : 2016 - 9 - 26 23 : 59 : 59)
     * @author ZKT
     * @createTime 2012-9-26
     */
    public static Date getDateEndDateTime(String date) {
        return getDateEndDateTime(strToDate(date, FORMATER_YYYYMMDD));
    }

    public static Date getYesterdayCurrentTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return time;
    }


    /**
     * 获得两个日期直接的字符串数组[2018-08-01,2018-08-02]
     *
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<String> findDates(String dBegin, String dEnd) {
        List lDate = Lists.newArrayList();

        Date beginDate = strToDate(dBegin, FORMATER_YYYYMMDD);

        Date endDate = strToDate(dEnd, FORMATER_YYYYMMDD);


        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(beginDate);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);

        while (endDate.after(calBegin.getTime())) {
            lDate.add(DateUtils.dateToStr(calBegin.getTime(), FORMATER_YYYYMMDD));
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        lDate.add(DateUtils.dateToStr(calBegin.getTime(), FORMATER_YYYYMMDD));

        return lDate;
    }

    /**
     * 获取当天凌晨0点到当前时间的时间字符串[00:00, 01:00]
     *
     * @return
     */
    public static List<String> getTodayTimeList() {
        Date date = DateUtils.getStartTime();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Date currentDateTime = DateUtils.getCurrentDateTime();
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentDateTime);

        List<String> list = Lists.newArrayList();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        while (c.get(Calendar.HOUR_OF_DAY) <= ca.get(Calendar.HOUR_OF_DAY)) {
            list.add(sdf.format(c.getTime()));
            c.add(Calendar.HOUR_OF_DAY, 1);
        }
        return list;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   比较时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 0:在之间/1:比较时间在开始时间之前/-1比较时间在开始时间之后
     * @author pine
     */
    public static Integer isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return 0;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return 0;
        } else {
            if (nowTime.before(startTime)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * 返回当前时间到下一整点秒数
     * eg:当前时间14:20:00,距离15:00:00,返回40*60
     *
     * @return
     */
    public static long getNextIntegralPointSeconds() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        long ss = (c.getTime().getTime() - System.currentTimeMillis()) / 1000;
        return ss;
    }

    public static void main(String[] args) {
        Date d1 = DateUtils.StringToDate("2018-08-01 00:00:00");
        Date d2 = DateUtils.StringToDate("2018-08-05 00:00:00");
        Date dd = DateUtils.StringToDate("2018-07-02 00:00:00");


        System.out.println("isEffectiveDate(dd,d1,d2) = " + isEffectiveDate(dd, d1, d2));

        System.out.println(dd.before(d1));
        System.out.println(stampToDate("1522632011"));
    }

}
