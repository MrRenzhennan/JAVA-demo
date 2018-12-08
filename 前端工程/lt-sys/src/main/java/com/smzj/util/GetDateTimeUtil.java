package com.smzj.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * 
 * @author
 * 
 */
public class GetDateTimeUtil {

	public static String getNowDate() {
		Date date = new Date();
		FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}

	/**
	 * 获取当前时间的年、月、日
	 * 
	 * @return
	 */
	public static String getNowsDate() {
		Date date = new Date();
		FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	public static String getOneDate() {
		FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(c.getTime());
		return firstDay;
	}

	/**
	 * 当月第一天取年、月、日
	 * 
	 * @return
	 */
	public static String getOnesDate() {
		FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(c.getTime());
		return firstDay;
	}

	/**
	 * 当月最后
	 * 
	 * @return
	 */
	public static String getLastDate() {
		FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = format.format(ca.getTime());
		return lastDay;
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(Integer year, Integer month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(Integer year, Integer month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/**
	 * 把开始时间的时分秒改为 00 00 00
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getOneDay(String time) {
		if (time.length() < 12) {
			time = time.substring(0, 10);
			return time + " 00:00:00";
		} else {
			time = time.substring(0, 11);
			return time + "00:00:00";
		}
	}

	/**
	 * 把结束时间的时分秒改为 00 00 00
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDay(String time) {
		if (time.length() < 12) {
			time = time.substring(0, 10);
			return time + " 23:59:59";
		} else {
			time = time.substring(0, 11);
			return time + "23:59:59";
		}

	}

	/**
	 * 当默认月是1时在前面追加0
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String apendMonth(String month) {
		if (month.length() == 1)
			return "0" + month;
		return null;
	}

}
