package com.anthonyzero.seed.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.anthonyzero.seed.common.exception.BaseException;

/**
 * 时间处理工具类
 * @author pingjin create 2018年7月11日
 *
 */
public class TimeUtil {
	public static final String DATE_FORMAT_EXPR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_TIME_EXPR = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_MILLI_TIME_EXPR = "yyyyMMddHHmmssSSS";
		
	/**
	 * 以 yyyy-MM-dd HH:mm:ss 格式返回当前时间字符串
	 * @return
	 */
	public static String getCurrentTime() {				
		return getTimeStr(new Date());
	}
	
	/**
     * 获取当前的日期时间 yyyyMMddHHmmss
     * 
     * @return
     */
    public static String getDateCurrentTime() {
        return getTimeStr(new Date(), DATE_FORMAT_TIME_EXPR);
    }
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date getDate() {
	    return new Date();
	}
	
	/**
	 * 以 yyyy-MM-dd HH:mm:ss 格式返回当前时间字符串
	 * @return
	 */
	public static String getTimeStr(Date date) {
		if (date == null) {
			return null;
		}
				
		return new SimpleDateFormat(DATE_FORMAT_EXPR).format(date);
	}
	
	/**
	 * 以 yyyy-MM-dd HH:mm:ss 格式返回当前毫秒表示的时间字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr(long time) {		
		return getTimeStr(new Date(time));
	}
	
	/**
	 * 从源字符串中获取指定格式的时间字符串
	 * 
	 * @param fromStr
	 * @param formatExpr
	 * @return
	 */
	public static String getTimeStrFromStr(String fromStr, String formatExpr) {
		Date date = getDate(fromStr, formatExpr);
		
		return getTimeStr(date, formatExpr);
	}
	
	/**
	 * 从fromFormatExpr格式的字符串fromStr中，格式化出toFormatExpr格式的时间字符串
	 * 
	 * @param fromStr
	 * @param fromFormatExpr
	 * @param toFormatExpr
	 * @return
	 */
	public static String getTimeStrFromStr(String fromStr, String fromFormatExpr, String toFormatExpr) {
		Date date = getDate(fromStr, fromFormatExpr);
		
		return getTimeStr(date, toFormatExpr);
	}
	
	/**
	 * 以指定表达式的格式返回指定日期的字符串形式
	 * @param date
	 * @param formatExpr
	 * @return
	 */
	public static String getTimeStr(Date date, String formatExpr) {
		if (date == null) {
			return null;
		}
				
		return new SimpleDateFormat(formatExpr).format(date);
	}
	
	/**
	 * 以指定表达式的格式返回当前日期的字符串形式
	 * @param formatExpr
	 * @return
	 */
	public static String getCurrentTimeStr(String formatExpr) {
		return getTimeStr(new Date(), formatExpr);
	}
	
	/**
	 * 获取时间戳 (年月日时分秒毫秒 - yyyyMMddHHmmssSSS)
	 * @return
	 */
	public static String getTimeStamp() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		return formater.format(new Date());
	}
	
	/**
	 * 将字符型 yyyy-MM-dd HH:mm:ss 格式表示的时间字符串转换为毫秒.
	 * 与协调世界时 1970 年 1 月 1 日午夜之间的时间差;
	 * 
	 * 如果格式错误, 返回 -1
	 * @param timeStr 
	 * 
	 * @return
	 */
	public static long getTime(String timeStr) {
		if (timeStr == null) {
			return -1;
		}
		
		timeStr = timeStr.trim();
		
		long milliseconds = -1;
				
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT_EXPR).parse(timeStr);
			milliseconds = date.getTime();
		} catch (Exception e) {				
		}
		
		return milliseconds;
	}	
	
	/**
     * 时间大小比较
     * 
     * @param timeStr
     * @param compareTimeStr
     * @return
     */
    public static long compare(Date time, Date compareTime) {
        return time.getTime() - compareTime.getTime();
    }
    
    /**
     * 计算时间间隔天数
     * 
     * @param  st  起始时间
     * @param  et  截止时间
     * @return
     */
    public static double intervalDay(Date st, Date et) {
    	long t0 = st.getTime();
		long t1 = et.getTime();
		double t= (t1-t0)/(24*60*60*1000);//日期间隔天数
    	return t;
    }
    
	/**
	 * 计算间隔小时，保留一位小数（四舍五入）
	 */
	public static BigDecimal getHour(Date st, Date et) {
		double interval =  et.getTime() - st.getTime();
		if (interval < 0) {
			throw new BaseException("起始时间，应该在结束时间之前");
		}
		BigDecimal val = new BigDecimal(interval);
		BigDecimal hours = val.divide(BigDecimal.valueOf(1000*60*60),1,BigDecimal.ROUND_HALF_UP);
		return hours;
	}
	
    /**
     * 转换为java.util.Date
     * 
     * @param date
     * @return
     */
    public static Date toDate(Date date) {
        if (date instanceof java.sql.Date) {
            return new Date(date.getTime());
        }
        
        return date;
    }
	
	/**
	 * 时间大小比较
	 * 
	 * @param timeStr
	 * @param compareTimeStr
	 * @return
	 */
	public static long compare(String timeStr, String compareTimeStr) {
		long timeStrLong = getTime(timeStr);
		long compareTimeStrLong = getTime(compareTimeStr);
		
		return timeStrLong - compareTimeStrLong;
	}
	
	/**
	 * 将字符串形式的时间格式，安装指定的表达式转换成日期格式
	 * @param timeStr
	 * @param formatExpr
	 * @return
	 */
	public static Date getDate(String timeStr, String formatExpr) {
		if (timeStr == null) {
			return null;
		}
		
		timeStr = timeStr.trim();
		
		Date date = null;
		
		try {
			date = new SimpleDateFormat(formatExpr).parse(timeStr);			
		} catch (Exception e) {				
		}
		
		return date;
	}
	
	/**
	 * 格式化标准字符串表示时间 yyyy-MM-dd HH:mm:ss 为日期格式
	 * 
	 * @param timeStr
	 * @return
	 */
	public static Date getDefaultDate(String timeStr) {
		return getDate(timeStr, DATE_FORMAT_EXPR);
	}
	
	/**
	 * 格式化标准字符串时间
	 * 
	 * @param timeStr
	 * @return
	 */
	public static Date parse(String timeStr) {
	    return getDefaultDate(timeStr);
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static long getTime() {
		return getTime(getCurrentTime());
	}
	
	/**
     * 在日期上增加数个整年
     */
    public static Date addYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);
        return cal.getTime();
    }
	
	/**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上加多少天
     * 
     * @param date
     * @param n
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数个整分钟
     */
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数个整秒
     */
    public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }
    
    public static void main(String[] args) {
    	System.out.println(getHour(new Date(1525342126000L), new Date(1525352926000L)));
	}
}
