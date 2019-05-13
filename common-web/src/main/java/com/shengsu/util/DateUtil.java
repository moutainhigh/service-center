package com.shengsu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DateUtil
{

    private static Logger logger = Logger.getLogger(DateUtil.class);

    private static final long MILLISECONDS_A_DAY = 24 * 3600 * 1000;

    private static final long MILLISECONDS_A_HOUR = 3600 * 1000;
    
    private static final long MILLISECONDS_A_MINUTE = 60 * 1000;

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATEFULLTIME_FORMAT = "yyyyMMddHHmmss";

    public static final String DEFAULT_FULLDATETIME_FORMAT = "yyyyMMddHHmmssSSS";

    public static final String DEFAULT_DATEFULLDATE_FORMAT = "yyyyMMdd";

    public static final String DEFAULT_YEAR_FORMAT = "yyyy";

    public static final String DEFAULT_MONTH_FORMAT = "MM";

    private static final Pattern pattern = Pattern.compile("(?:(?:19|20)\\d{2})-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|[12][0-9]|3[01])");
    /**
     * 获取某些天后的时间戳
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
    /**
     * 获取某些分钟后的时间戳
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinutes(Date date, int minute)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }
    /**
     * 获取某些秒后的时间戳
     * @param date
     * @param second
     * @return
     */
    public static Date addSeconds(Date date, int second)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }
    /**
     * 将字符串转化为日期格式:yyyy-MM-dd
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String s) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(s);
    }
    /**
     * 将字符串转化为日期格式:自定义格式
     * @param s
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String s,String format) throws ParseException
    {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    	return simpleDateFormat.parse(s);
    }
    /**
     * 将字符串转化为日期格式:yyyy-MM-dd hh:mm:ss
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date parseDate2hms(String s) throws ParseException
    {	
    	if (StringUtils.isEmpty(s)) {
    		return	null;
    	}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.parse(s);
    }
    /**
     * 获取某些月份后的时间戳
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
    /**
     * 获取 最大日期
     * cal.getActualMaximum(Calendar.DATE)获取一个指定日期的当月总天数
     * @return
     */
    public static Date getMaxDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }
    /**
     * 获取当前年份
     * @return
     */
    public static int getYear()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }
    /**
     * 获取当前月份
     * @return
     */
    public static int getMonth()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }
    /**
     * 获取最小时间戳
     * @return
     */
    public static Date getMinDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }
    /**
     * 根据日期获取最小时间戳
     * @param date
     * @return
     */
    public static Date getMinDateByMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }
    /**
     * 根据日期获取最大时间戳
     * @param date
     * @return
     */
    public static Date getMaxDateByMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }
    /**
     * 根据年月 获取最近的月份(上一个月份)的最后一天的日期
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfLastMonth(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        // cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    /**
     * 获取某些年后的日期
     * @param date
     * @param years
     * @return
     */
    public static Date addYears(Date date, int years)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }
    /**
     * 将当前时间格式化成字符串
     * @param format
     * @return
     */
    public static String datetime(String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }
    /**
     * 将某个日期格式化成字符串
     * @param date
     * @param format
     * @return
     */
    public static String datetime(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    /**
     * 将字符串格式化成日期字符串
     * @param date
     * @param format
     * @return
     */
    public static String datetime(String date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    /**
     * 将某个日期格式化成字符串
     * @param date
     * @param format
     * @return
     */
    public static String date(Date date, String format)
    {
        if (date == null)
        {
            return "";
        }

        return (new SimpleDateFormat(format)).format(date);
    }
    /**
     * 将字符串格式化成日期字符串
     * @param dateStr
     * @param format
     * @return
     */
    public static String date(String dateStr, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(dateStr);
    }
    /**
     * 获取当前日期字符串:yyyy-MM-dd
     * @return
     */
    public static String getNowDateStr()
    {
        return getNowDatetimeStr(DEFAULT_DATE_FORMAT);
    }
    /**
     * 获取当前日期字符串:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowDatetimeStr()
    {
        return getNowDatetimeStr(DEFAULT_DATETIME_FORMAT);
    }
    /**
     * 获取当前日期字符串:yyyyMMddHHmmss
     * @return
     */
    public static String getNowDateminStr()
    {
        return getNowDatetimeStr(DEFAULT_DATEFULLTIME_FORMAT);
    }
    /**
     * 获取当前日期字符串:自定义格式
     * @param format
     * @return
     */
    public static String getNowDatetimeStr(String format)
    {
        Calendar cal = Calendar.getInstance();
        return datetime(cal.getTime(), format);
    }
    
    public static Date dateOnly(Date date)
    {
        return new Date(date.getTime() / MILLISECONDS_A_DAY);
    }

    public static Date dateOnlyExt(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date dateMinTime(Date date)
    {
        return dateOnlyExt(date);
    }

    public static String getStandDateTimeStr(String dateTimeStr)
    {
        if (dateTimeStr == null || "".equals(dateTimeStr))
        {
            return "";
        }

        dateTimeStr = dateTimeStr.replaceAll("\\s+", "|");
        String[] a = dateTimeStr.split("\\|");
        List<String> list = Arrays.asList(a);
        String datetime = "";
        int count = 1;
        for (int i = 0; i < list.size(); i++)
        {
            String temp = (String) list.get(i);
            StringTokenizer st;
            if (i == 0)
                st = new StringTokenizer(temp, "-");
            else
                st = new StringTokenizer(temp, ":");
            int k = st.countTokens();
            for (int j = 0; j < k; j++)
            {
                String sttemp = (String) st.nextElement();
                if (count == 1)
                {
                    datetime = sttemp;
                } else
                {
                    if ((sttemp.equals("0")) || (sttemp.equals("00")))
                        sttemp = "0";
                    else if ((Integer.valueOf(sttemp).intValue()) < 10)
                        sttemp = sttemp.replaceAll("0", "");
                    if (count < 4)
                    {
                        if ((Integer.valueOf(sttemp).intValue()) < 10)
                            datetime = datetime + "-0" + sttemp;
                        else
                            datetime = datetime + "-" + sttemp;
                    }
                    if (count == 4)
                    {
                        if ((Integer.valueOf(sttemp).intValue()) < 10)
                            datetime = datetime + " 0" + sttemp;
                        else
                            datetime = datetime + " " + sttemp;
                    }
                    if (count > 4)
                    {
                        if ((Integer.valueOf(sttemp).intValue()) < 10)
                            datetime = datetime + ":0" + sttemp;
                        else
                            datetime = datetime + ":" + sttemp;
                    }
                }
                count++;
            }
        }

        try
        {
            @SuppressWarnings("unused")
            Date test = getDateFromStr(datetime);
            return datetime;
        } catch (Exception e)
        {
            return "";
        }
    }

    @SuppressWarnings("deprecation")
    public static Date getDateFromStr(String datetime)
    {
        if (datetime == null || "".equals(datetime))
        {
            return new Date();
        }

        String nyr = datetime.trim();

        if (nyr.indexOf(" ") > 0)
        {
            nyr = nyr.substring(0, nyr.indexOf(" "));
        } else
        {
            nyr = nyr.substring(0, nyr.length());
        }

        StringTokenizer st = new StringTokenizer(nyr, "-");
        Date date = new Date();
        String temp = "";
        int count = st.countTokens();
        for (int i = 0; i < count; i++)
        {
            temp = (String) st.nextElement();
            // if(!(temp.equals("0")))
            // temp.replaceAll("0", "");
            if (i == 0)
                date.setYear(Integer.parseInt(temp) - 1900);
            if (i == 1)
                date.setMonth(Integer.parseInt(temp) - 1);
            if (i == 2)
                date.setDate(Integer.parseInt(temp));
        }

        if (datetime.length() > 10)
        {
            String sfm = datetime.substring(11, 19);
            StringTokenizer st2 = new StringTokenizer(sfm, ":");
            count = st2.countTokens();
            for (int i = 0; i < count; i++)
            {
                temp = (String) st2.nextElement();
                // if(!(temp.equals("0")))
                // temp.replaceAll("0", "");
                if (i == 0)
                    date.setHours(Integer.parseInt(temp));
                if (i == 1)
                    date.setMinutes(Integer.parseInt(temp));
                if (i == 2)
                    date.setSeconds(Integer.parseInt(temp));
            }
        }
        return date;
    }

    public static int getQuot(Date startDate, Date endDate)
    {
        long quot = 0;
        quot = endDate.getTime() - startDate.getTime();
        quot = quot / MILLISECONDS_A_DAY;
        return (int) quot;
    }

    public static int getQuot(String startDateStr, String endDateStr, String format)
    {
        long quot = 0;
        format = (format != null && format.length() > 0) ? format : DEFAULT_DATE_FORMAT;
        SimpleDateFormat ft = new SimpleDateFormat(format);
        try
        {
            Date date1 = ft.parse(endDateStr);
            Date date2 = ft.parse(startDateStr);
            quot = date1.getTime() - date2.getTime();
            quot = quot / MILLISECONDS_A_DAY;
        } catch (ParseException e)
        {
            logger.error("", e);
        }
        return (int) quot;
    }

    public static final String getDateTime(Date date)
    {
        if (date == null)
            return "";
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return ymdhmsFormat.format(date);
    }
    
    public static final String getMdDateTime(Date date)
    {
        if (date == null)
            return "";
        DateFormat ymdhmsFormat = new SimpleDateFormat("MM-dd HH:mm");
        return ymdhmsFormat.format(date);
    }
    
    public static final String getDate(Date date)
    {
        if (date == null)
            return "";
        DateFormat ymdhFormat = new SimpleDateFormat("yyyy-MM-dd");
        return ymdhFormat.format(date);
    }
    
    public static String getMonthDay(Date date){
    	if (date == null)
            return "";
        DateFormat ymdhFormat = new SimpleDateFormat("M月d号");
        return ymdhFormat.format(date);
    }
    public static String getHourMinute(Date date){
    	if (date == null)
            return "";
        DateFormat ymdhFormat = new SimpleDateFormat("HH:mm");
        return ymdhFormat.format(date);
    }
    
    public static final String getDateTime(Date date, String pattern)
    {
        if (date == null)
            return "";
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern);
        return ymdhmsFormat.format(date);
    }

    public static int getQuotHours(Date startDate, Date endDate)
    {
        long quot = 0;
        quot = endDate.getTime() - startDate.getTime();
        quot = quot / MILLISECONDS_A_HOUR;
        return (int) quot;
    }
    public static long getQuotMinute(Date startDate, Date endDate)
    {
    	long quot = 0;
    	if (startDate!=null && endDate!=null) {
    		quot = endDate.getTime() - startDate.getTime();
    		quot = quot / MILLISECONDS_A_MINUTE;
		}
        return  quot;
    }

    public static Date getDateTime(String dateTime)
    {
        return getDateTime(dateTime, "yyyy-MM-dd");
    }

    public static Date getDateTime(String dateTime, String formatPattern)
    {
        try
        {
            if (StringUtils.isNotBlank(dateTime) && StringUtils.isNotBlank(formatPattern))
            {
                SimpleDateFormat format = new SimpleDateFormat(formatPattern);
                return format.parse(dateTime);
            }
        } catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }

    public static Date getDateDetailTime(String dateTime)
    {
        try
        {
            if (StringUtils.isNotBlank(dateTime))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                return format.parse(dateTime);
            }
        } catch (ParseException e)
        {
            logger.error(e);
        }

        return null;
    }

    public static long getDtSeq()
    {
        return System.currentTimeMillis();
    }

    public static boolean isBetween(Date min, Date compare)
    {
        Boolean ret = false;
        Date minDate = DateUtil.dateOnlyExt(min);
        Date maxDate = DateUtil.dateOnlyExt(DateUtil.addDays(min, 1));
        if (compare.after(minDate) && compare.before(maxDate))
        {
            ret = true;
        }
        return ret;
    }

    public static Date getDate(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }

    public static Map<String, String> getFLDayMap(String monthRange)
    {
        return getFLDayMap(monthRange, DEFAULT_DATE_FORMAT);
    }

    public static Map<String, String> getFLDayMap(String monthRange, String pattern)
    {
        Map<String, String> rs = new LinkedHashMap<String, String>();

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        if (StringUtils.isBlank(monthRange))
        {
            monthRange = "cm";
        }

        if (!"sm".equals(monthRange))
        {
            if ("pm".equals(monthRange))
            {
                calendar.add(Calendar.MONTH, -1);
            }

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            rs.put("firstDay", df.format(calendar.getTime()));

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            rs.put("lastDay", df.format(calendar.getTime()));

            return rs;
        }

        int[][] seasons = { { 2, 4 }, { 5, 7 }, { 8, 10 }, { 11, 1 } };
        int cm = calendar.get(Calendar.MONTH) + 1;

        for (int[] im : seasons)
        {
            if (cm >= im[0] && cm <= im[1])
            {
                calendar.set(Calendar.MONTH, im[0] - 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                rs.put("firstDay", df.format(calendar.getTime()));

                calendar.set(Calendar.MONTH, im[1] - 1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar
                        .getActualMaximum(Calendar.DAY_OF_MONTH));
                rs.put("lastDay", df.format(calendar.getTime()));

                break;
            }
        }

        return rs;
    }

    public static String getYearString(Date date)
    {
        return DateUtil.date(date, DEFAULT_YEAR_FORMAT);
    }

    public static int getYearInteger(Date date)
    {
        return Integer.parseInt(DateUtil.date(date, DEFAULT_YEAR_FORMAT));
    }

    public static String getMonthString(Date date)
    {
        return DateUtil.date(date, DEFAULT_MONTH_FORMAT);
    }

    public static int getMonthInteger(Date date)
    {
        return Integer.parseInt(DateUtil.date(date, DEFAULT_MONTH_FORMAT));
    }

    public static Date getLastDayOfCurMonth(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 1);
        // cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date getFirstDayOfCurMonth(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        // cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    public static Date getFirstDayOfWeek(Date date)
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static Date getLastDayOfWeek(Date date)
    {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    public static boolean isValidDate(String ds)
    {
        if (StringUtils.isBlank(ds))
            return false;
        return pattern.matcher(ds).matches();
    }

    public static boolean isValidDate(Date d)
    {
        if (d == null)
            return false;
        return pattern.matcher(date(d, DEFAULT_DATE_FORMAT)).matches();
    }
    
    /**
     * 
     * @return
     */
    public static Date getToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.add(Calendar.SECOND, -1);
        return today;
    }
    
    /**
     * 根据生日计算出年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirthDay(Date birthday)
    {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday))
        {
            throw new IllegalArgumentException("The birthDay is before Now. It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth)
        {
            if (monthNow == monthBirth)
            {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth)
                {
                    age--;
                }
            } else
            {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }
    /**
     * 日期转星期
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
    	if (datetime.isEmpty()) {
			return "";
		}
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
     * 根据时间段获取日期
     * @param args
     * @return 
     * @throws ParseException 
     * @throws Exception
     */
    public static List<String> getDate(String beginDate,String endDate) throws ParseException  {		
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
    	Calendar cal = Calendar.getInstance();		
    	cal.setTime(sdf.parse(beginDate));
    	List<String> dateStrings = new ArrayList<>();
    	for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plaus_1(cal)) {	
    		dateStrings.add(sdf.format(d));
    		}
    	return dateStrings;
    }
 
    public static long get_D_Plaus_1(Calendar c) {		
    	c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);		
    	return c.getTimeInMillis();	
    }
    /**
     * 根据年月获取所有日期集合
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthFullDay(int year , int month){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }
    public void isOverDay() {// 指定时间判断是否是双休日
    	String dates = "2013-05-13"; // 这里可以自定义
		DateFormat df = new SimpleDateFormat("yy-MM-dd");
		Date d;
		try {
			d = df.parse("");
			if (d.getDay() == 0 || d.getDay() == 6) {
				System.out.println("日期:[" + dates + "] 是双休日");
			}else{
				System.out.println("日期:[" + dates + "] 不是双休日");
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static void isHodliDays() { // 判断是否滴节假日，是否有节假日加班
    	String isHoliday = "01-01,01-02,01-03,02-09,02-10,02-11,02-12,02-13,02-14,"		
    						+ "02-15,04-04,04-05,04-06,04-29,04-30,05-01,06-10,06-11,"		
    						+ "06-12,09-19,09-20,09-21,10-01,10-02,10-03,10-04,10-05,10-06,10-07";
    	// 节假前后加班日期
    	String overDay = "01-05,01-06,02-16,02-17,04-07,04-17,04-28,06-08,06-09,09-22,09-19,10-12";
    	// 指定的 月份和日期
    	String date = "10-12"; // 这里可以自定义
    	if (isHoliday.contains(date)) {
    		System.out.println("是节假日...");
    	}
    	if (overDay.contains(date)) {
    		System.out.println("节假前后加班日期...");
    	}
    }
}
