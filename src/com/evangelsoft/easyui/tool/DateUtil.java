package com.evangelsoft.easyui.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * ʱ����ع�����.
 *
 */
public class DateUtil {

	// ���ڸ�ʽ����ʽ
	public static String FORMAT_DATE_PATTERN = "yyyy-MM-dd";
	// ����ʱ���ʽ����ʽ
	public static String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// ʱ���ʽ����ʽ
	public static String FORMAT_TIME_PATTERN = "HH:mm:ss";

	/** ע�������������������ʱ�����ڲ�� */
	private static int gregorianCutoverYear = 1582;

	/** ������ÿ������ */
	private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31,
		31, 30, 31, 30, 31 };
	/** ƽ����ÿ������ */
	private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31,
		31, 30, 31, 30, 31 };

	/**
	 * ��鴫��Ĳ������������Ƿ�Ϊ����
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
	 * ȡ��Ӧ�·ݵ�����
	 *
	 * @param month
	 *            �·�
	 * @param isLeapYear
	 *            �Ƿ�����
	 * @return
	 */
	public static int getDaysByMonth(int month, boolean isLeapYear) {
		if (isLeapYear)
			return DAYS_P_MONTH_LY[month - 1];
		else
			return DAYS_P_MONTH_CY[month - 1];
	}

	/**
	 * ��鴫������ڲ����ܷ����ɺϷ�������<br/> �磺2008-02-30�Ͳ��ǺϷ�������,����false
	 *
	 * @param year
	 *            ��ݣ���ʽyyyy
	 * @param month
	 *            �·ݣ���ʽMM
	 * @param day
	 *            �գ���ʽdd
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
	 * ����ת��Ϊָ�����ַ���
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

	// �ж�ʱ��date1�Ƿ���ʱ��date2֮ǰ
	// ʱ���ʽ 2007-12-27 18:26:00
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	// �жϵ�ǰʱ���Ƿ���ʱ��date2֮ǰ
	// ʱ���ʽ 2007-12-27 18:26:00
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
	 * ���ַ���ת��Ϊ���ڣ�java.util.Date�� �ַ�����ʽҪ��Ϊ yyyy-mm-dd hh:mm:ss
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
	 * ��һ���ַ���ת�����������͵Ķ��� ͨ��ʹ���������������һ���µ�Date������Ϊ���ǲ���ֱ�ӷ���
	 * ��ʹ��Date��Ĺ��캯��������һ������ָ�����ڵĶ���
	 *
	 * ������ style����SimpleDateFormat���е�time pattern stringһ�¡�
	 * dateString,���ĸ�ʽӦ�ø�styleһ�¡�
	 *
	 * �÷������� Date date = Converter.stringToDate("yyyy/MM/dd","2001/06/01") Date
	 * date = Converter.stringToDate("yyyy'��'MM'��'dd'��'","2001��05��04��"); Date
	 * date = Converter.stringToDate("yyyyMMddhhmm","200105040514");
	 */
	public static Date str2Date(String style, String dateString)
	throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Date date = sdf.parse(dateString);
		return date;
	}

	/***************************************************************************
	 * �����ڣ�java.util.Date, java.sql.Date, java.sql.Time,
	 * java.sql.TimeStamp��ת��Ϊ�ַ��� ��ʽΪ�� yyyy-mm-dd hh:mm:ss
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
	 * @see Ϊָ��������������
	 * @param Date
	 *            date ָ��������
	 * @param int
	 *            count ����
	 * @return �������Ӻ�� Date
	 */
	public static Date dateAdd(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ count);// �����ڼ�count
		return calendar.getTime();
	}

	/**
	 * @see Ϊָ����������Сʱ
	 * @param Date
	 *            date ָ��������
	 * @param int
	 *            count Сʱ��
	 * @return �������Ӻ�� Date
	 */
	public static Date timeAdd(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.HOUR_OF_DAY, count);
		return calendar.getTime();
	}

	/**
	 * @see Ϊָ�����ڼ�������
	 * @param Date
	 *            date ָ��������
	 * @param int
	 *            count ����
	 * @return ���ؼ��ٺ�� Date
	 */
	public static Date dateReduce(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));// ָ��������
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- count);// �����ڼ�count
//		System.out.println(calendar.getTime());// �µ�����
		return calendar.getTime();
	}

	/**
	 * @see ����������������
	 * @param Date
	 *            fDate ָ��������1
	 * @param Date
	 *            oDate ָ��������2
	 * @return �������
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
			System.out.println("���ڸ�ʽ����Ϊ��yyyy-MM-dd���磺2010-4-4.");
		}
		return days;
	}


	/**
	 * �ж�2��ʱ�������ٷ���<br>
	 * <br>
	 * @param pBeginTime <br>
	 * @param pEndTime <br>
	 * @return String ������<br>
	 * @Exception �����쳣<br>
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
