package com.anthonyzero.seed.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * @author pingjin create 2018年7月11日
 *
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期增加天数
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 判断时间是否符合时间格式
	 */
	public static boolean isLegalDateString(String date, String dateFormat) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			format.setLenient(false);
			try {
				format.format(format.parse(date));
			} catch (ParseException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	/**
	 * SQL DATE_ADD函数
	 * 
	 * @param end
	 * @param interval
	 *            月份间隔
	 * @return
	 */
	public static String subDataInterval(Date end, int interval) {
		if (end == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(end);
		calendar.add(Calendar.MONTH, interval);
		return format(calendar.getTime(), DATE_TIME_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 格式化字符串为日期
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parseDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ---获取年月日时分秒----------------------------------------------------
	/**
	 * 获取年份
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取星期
	 *
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取小时
	 *
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分种
	 *
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒
	 *
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	// --------------获取星期几---------------------------------------------------
	/**
	 * 获取星期几
	 *
	 * @param strDate
	 * @return
	 */
	public static String getWeekDayName(Date date) {
		String[] mName = { "日", "一", "二", "三", "四", "五", "六" };
		int week = getWeek(date);
		return "星期" + mName[week];
	}

	/**
	 * 一年中的星期几
	 * 
	 * @return
	 */
	public static int getWeekNumOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取本周星期一的日期
	 * 
	 * @param yearNum
	 * @param weekNum
	 * @return
	 * @throws ParseException
	 */
	public static String getYearWeekFirstDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH) - 1);
		return tempYear + "-" + tempMonth + "-" + tempDay;
	}

	/**
	 * 获取本周星期天的日期
	 * 
	 * @param yearNum
	 * @param weekNum
	 * @return
	 * @throws ParseException
	 */
	public static String getYearWeekEndDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH) - 1);
		return tempYear + "-" + tempMonth + "-" + tempDay;
	}

	// --------------获取天数---------------------------------------------------
	/**
	 * 获取某年某月的第一天
	 * 
	 * @param yearNum
	 * @param monthNum
	 * @return
	 */
	public static Date getYearMonthFirstDay(int yearNum, int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, monthNum - 1, 1, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取某年下个月的第一天
	 * 
	 * @param yearNum
	 * @param monthNum
	 * @return
	 */
	public static Date getNextYearMonthFirstDay(int yearNum, int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, monthNum, 1, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param yearNum
	 * @param monthNum
	 * @return
	 */
	public static Date getYearMonthEndDay(int yearNum, int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, monthNum, 0, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取某月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取下一年的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextYearMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(2, 1);
		cal.set(5, 1);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearMonthEndDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, cal.getActualMaximum(5));
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param yearNum
	 * @return
	 */
	public static Date getYearFirstDay(int yearNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, 0, 1, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取下一年的第一天
	 * 
	 * @param yearNum
	 * @return
	 */
	public static Date getNextYearFirstDay(int yearNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, 12, 1, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param yearNum
	 * @return
	 */
	public static Date getYearEndDay(int yearNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearNum, 12, 0, 0, 0, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前星期
	 * 
	 * @param strDate
	 * @param weekNum
	 * @return
	 */
	public static Date getWeek(Date date, int weekNum) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (weekNum == 1)
			c.set(7, 2);
		else if (weekNum == 2)
			c.set(7, 3);
		else if (weekNum == 3)
			c.set(7, 4);
		else if (weekNum == 4)
			c.set(7, 5);
		else if (weekNum == 5)
			c.set(7, 6);
		else if (weekNum == 6)
			c.set(7, 7);
		else if (weekNum == 0)
			c.set(7, 1);
		return c.getTime();
	}

	/**
	 * 下个月日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		do
			c.add(Calendar.DAY_OF_MONTH, 1);
		while (c.get(Calendar.DAY_OF_WEEK) != 2);
		return c.getTime();
	}

	/**
	 * 获得某一日期的前一天
	 *
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得某年某月最后一天的日期
	 *
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获取一个月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysInMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// Java月份才0开始算
		return cal.getActualMaximum(Calendar.DATE);
	}
}
