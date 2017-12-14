package com.evangelsoft.easyui.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * 时间相关工具类.
 *
 */
public class DateUtil {

	// 日期格式化样式
	public static String FORMAT_DATE_PATTERN = "yyyy-MM-dd";
	// 日期时间格式化样式
	public static String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// 时间格式化样式
	public static String FORMAT_TIME_PATTERN = "HH:mm:ss";

	/** 注意格里历和儒略历交接时的日期差别 */
	private static int gregorianCutoverYear = 1582;

	/** 闰年中每月天数 */
	private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31,
		31, 30, 31, 30, 31 };
	/** 平年中每月天数 */
	private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31,
		31, 30, 31, 30, 31 };

	/**
	 * 检查传入的参数代表的年份是否为闰年
	 *
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return year >= gregorianCutoverYear ? ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))
				: // Gregorian
					(year % 4 == 0); // Julian
	}

	/**
	 * 取相应月份的天数
	 *
	 * @param month
	 *            月份
	 * @param isLeapYear
	 *            是否闰年
	 * @return
	 */
	public static int getDaysByMonth(int month, boolean isLeapYear) {
		if (isLeapYear)
			return DAYS_P_MONTH_LY[month - 1];
		else
			return DAYS_P_MONTH_CY[month - 1];
	}

	/**
	 * 检查传入的日期参数能否生成合法的日期<br/> 如：2008-02-30就不是合法的日期,返回false
	 *
	 * @param year
	 *            年份，格式yyyy
	 * @param month
	 *            月份，格式MM
	 * @param day
	 *            日，格式dd
	 * @return
	 */
	public static boolean isValidDate(String year, String month, String day) {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		int d = Integer.parseInt(day);
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		try {
			formater.parse(year + month + day);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		if (d <= getDaysByMonth(m, isLeapYear(y)))
			return true;
		else
			return false;
	}

	/**
	 * 日期转化为指定的字符串
	 *
	 * @param obj
	 * @param pattern
	 * @return
	 */
	public static String date2Str(Object obj, String pattern) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(pattern);
		java.util.Date date = new java.util.Date();
		if (obj != null) {
			if (obj instanceof java.util.Date) {
				java.util.Date dateTemp = (java.util.Date) obj;
				c.setTime(dateTemp);
			} else if (obj instanceof java.sql.Date) {
				java.sql.Date dateTemp = (java.sql.Date) obj;
				c.setTime(dateTemp);
			} else if (obj instanceof java.sql.Time) {
				java.sql.Date dateTemp = (java.sql.Date) obj;
				c.setTime(dateTemp);
			} else if (obj instanceof java.sql.Timestamp) {
				java.sql.Timestamp dateTemp = (java.sql.Timestamp) obj;
				c.setTime(dateTemp);
			} else {
				c.setTime(date);
			}
		} else {
			c.setTime(date);
		}
		return df.format(c.getTime());
	}

	// 判断时间date1是否在时间date2之前
	// 时间格式 2007-12-27 18:26:00
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2007-12-27 18:26:00
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	/**
	 * 将字符串转化为日期（java.util.Date） 字符串格式要求为 yyyy-mm-dd hh:mm:ss
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date str2Date(String str) throws Exception {

		int year, month, day, hour, minute, second;
		String s1 = str.substring(0, str.indexOf(" "));
		String s2 = str.substring(str.indexOf(" ") + 1);

		StringTokenizer st = new StringTokenizer(s1, "-");
		year = Integer.parseInt(st.nextToken());
		month = Integer.parseInt(st.nextToken());
		day = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(s2, ":");
		hour = Integer.parseInt(st.nextToken());
		minute = Integer.parseInt(st.nextToken());
		second = Integer.parseInt(st.nextToken());

		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour, minute, second);

		return c.getTime();
	}

	/**
	 * 把一个字符串转化成日期类型的对象。 通常使用这个函数来生成一个新的Date对象，因为我们不能直接方便
	 * 的使用Date类的构造函数来生成一个我们指定日期的对象。
	 *
	 * 参数： style，跟SimpleDateFormat类中的time pattern string一致。
	 * dateString,它的格式应该跟style一致。
	 *
	 * 用法举例： Date date = Converter.stringToDate("yyyy/MM/dd","2001/06/01") Date
	 * date = Converter.stringToDate("yyyy'年'MM'月'dd'日'","2001年05月04日"); Date
	 * date = Converter.stringToDate("yyyyMMddhhmm","200105040514");
	 */
	public static Date str2Date(String style, String dateString)
	throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Date date = sdf.parse(dateString);
		return date;
	}

	/***************************************************************************
	 * 将日期（java.util.Date, java.sql.Date, java.sql.Time,
	 * java.sql.TimeStamp）转换为字符串 格式为： yyyy-mm-dd hh:mm:ss
	 **************************************************************************/
	public static String date2Str(Object obj) {

		try {
			if (obj != null) {
				Calendar c = Calendar.getInstance();

				if (obj instanceof java.util.Date) {
					java.util.Date dateTemp = (java.util.Date) obj;
					c.setTime(dateTemp);
				} else if (obj instanceof java.sql.Date) {
					java.sql.Date dateTemp = (java.sql.Date) obj;
					c.setTime(dateTemp);
				} else if (obj instanceof java.sql.Time) {
					java.sql.Date dateTemp = (java.sql.Date) obj;
					c.setTime(dateTemp);
				} else if (obj instanceof java.sql.Timestamp) {
					java.sql.Timestamp dateTemp = (java.sql.Timestamp) obj;
					c.setTime(dateTemp);
				} else {
					return "Bad date object";
				}

				return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
				+ "-" + c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			} else {
				return "Null date object";
			}
		} catch (Exception e) {
			return e.toString();
		}
	}

	/**
	 * @see 为指定日期增加天数
	 * @param Date
	 *            date 指定的日期
	 * @param int
	 *            count 天数
	 * @return 返回增加后的 Date
	 */
	public static Date dateAdd(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ count);// 让日期加count
		return calendar.getTime();
	}

	/**
	 * @see 为指定日期增加小时
	 * @param Date
	 *            date 指定的日期
	 * @param int
	 *            count 小时数
	 * @return 返回增加后的 Date
	 */
	public static Date timeAdd(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.HOUR_OF_DAY, count);
		return calendar.getTime();
	}

	/**
	 * @see 为指定日期减少天数
	 * @param Date
	 *            date 指定的日期
	 * @param int
	 *            count 天数
	 * @return 返回减少后的 Date
	 */
	public static Date dateReduce(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));// 指定的日期
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- count);// 让日期加count
//		System.out.println(calendar.getTime());// 新的日期
		return calendar.getTime();
	}

	/**
	 * @see 两个日期相差的天数
	 * @param Date
	 *            fDate 指定的日期1
	 * @param Date
	 *            oDate 指定的日期2
	 * @return 相差天数
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		int quot = 0;
		long time =(oDate.getTime() - fDate.getTime());
//		System.out.println(time);
		quot =(int)(time /( 1000 *60 * 60 * 24));
		return quot;
	}

	public static int countDays(String begin, String end) {
		int days = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c_b = Calendar.getInstance();
		Calendar c_e = Calendar.getInstance();
		try {
			c_b.setTime(df.parse(begin));
			c_e.setTime(df.parse(end));
			while (c_b.before(c_e)) {
				days++;
				c_b.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException pe) {
			System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
		}
		return days;
	}


	/**
	 * 判断2个时间相差多少分钟<br>
	 * <br>
	 * @param pBeginTime <br>
	 * @param pEndTime <br>
	 * @return String 计算结果<br>
	 * @Exception 发生异常<br>
	 */
	public static long TimeDiff(String pBeginTime, String pEndTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long beginL = format.parse(pBeginTime).getTime();
		long endL = format.parse(pEndTime).getTime();
		long min = (endL - beginL)/60000;
		return min  ;
	}


	public static void main(String args[]) {
		long hour=0;
		try {
			hour = DateUtil.TimeDiff("2013-01-07 10:46:32","2013-01-07 11:59:47");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(hour%60);

	}

}
