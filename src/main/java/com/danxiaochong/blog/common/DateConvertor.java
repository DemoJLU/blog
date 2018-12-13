package com.danxiaochong.blog.common;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 关于时间处理的工具类
 * 
 * @author not attributable
 * @version 1.0
 */
public class DateConvertor {
  private static Log log = LogFactory.getLog(DateConvertor.class);
  
  
  /**
   * 第一季度
   */
  public final static int[] firstQuarter =
    { Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH };
  /**
   * 第二季度
   */
  public final static int[] secondQuarter =
    { Calendar.APRIL, Calendar.MAY, Calendar.JUNE };
  /**
   * 第三季度
   */
  public final static int[] thirdQuarter =
    { Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER };
  /**
   * 第四季度
   */
  public final static int[] forthQuarter =
    { Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER };

  public static Date parseDate(String origin, String oldestTimeLine, String pattern)
    throws Exception {
    return DateConvertor.parseDate(origin, pattern, oldestTimeLine, pattern);
  }

  public static Date parseDate(String origin, String pattern, String oldestTimeLine,
                               String timeLinePattern) throws NumberFormatException,
    ParseException {
    Date date = DateUtils.parseDate(origin, new String[] { pattern });
    Date timeLine = DateUtils.parseDate(oldestTimeLine, new String[] { timeLinePattern });
    String originBack = DateConvertor.getTimeString(date, pattern);
    if (!originBack.equals(origin) || date.before(timeLine)) {
      String tips = null;
      if (!originBack.equals(origin)) {
        tips = "日期格式错误";
      } else {
        tips = "日期过早";
      }
      throw new NumberFormatException(tips);
    }
    return date;
  }

  /**
   * 检查参数中的两个时间值是否合理。如果thinkCurrent为true，则如果哪个时间超过了当前时间，则置为当前值；如果前后顺序颠倒，则交换两个值。
   * 
   * @param start
   *          开始时间
   * @param end
   *          结束时间
   * @param thinkCurrent
   *          是否考虑参数中的时间超过当前时间
   */
  public static void checkDateSequence(Date start, Date end, boolean thinkCurrent) {
    if (thinkCurrent) {
      Date curr = new Date();
      if (start.after(curr)) {
        start.setTime(curr.getTime());
      }
      if (end.after(curr)) {
        end.setTime(curr.getTime());
      }
    }
    if (start.after(end)) {
      long tmp = start.getTime();
      start.setTime(end.getTime());
      end.setTime(tmp);
    }
  }

  private static boolean contains(int value, int[] quarter) {
    for (int i = 0; i < quarter.length; i++) {
      if (value == quarter[i]) {
        return true;
      }
    }
    return false;
  }

  /**
   * 把2位的年数变成4位
   * 
   * @param year
   *          待处理的年数
   * @param split
   *          分割点。小于该值，转化为"20" + year;大于等于该值，转化为"19" + year
   * @return 4位年数
   */
  public static String convertYearFrom2To4(String year, int split) {
    int yeari = Integer.parseInt(year);
    if (yeari >= split) {
      return "19" + year;
    } else {
      return "20" + year;
    }
  }

  /**
   * 返回当前时间的小时数
   * 
   * @return 当前时间的小时数
   */
  public static int getCurrHour() {
    Calendar c = Calendar.getInstance();
    return c.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * 返回当前时间的分钟数
   * 
   * @return 当前时间的分钟数
   */
  public static int getCurrMinute() {
    Calendar c = Calendar.getInstance();
    return c.get(Calendar.MINUTE);
  }

  /**
   * 返回一个时间区间。
   * 
   * @param startDate
   *          开始日期
   * @param endDate
   *          结束日期
   * @param startHour
   *          开始小时数
   * @param endHour
   *          结束小时数
   * @param startMinute
   *          开始分钟数
   * @param endMinute
   *          结束分钟数
   * @param thinkCurrent
   *          是否考虑参数时间超过当前时间
   * @return Date数组，0位为开始时间，1位为结束时间
   */
  public static Date[] getDateScope(Date startDate, Date endDate, int startHour,
                                    int endHour, int startMinute, int endMinute,
                                    boolean thinkCurrent) {
    Calendar cs = Calendar.getInstance();
    cs.setTime(startDate);
    cs.set(Calendar.HOUR_OF_DAY, startHour);
    cs.set(Calendar.MINUTE, startMinute);
    Date start = cs.getTime();
    cs.setTime(endDate);
    cs.set(Calendar.HOUR_OF_DAY, endHour);
    cs.set(Calendar.MINUTE, endMinute);
    Date end = cs.getTime();
    checkDateSequence(start, end, thinkCurrent);

    Date[] res = new Date[2];
    res[0] = start;
    res[1] = end;
    return res;
  }

  public static Date getSingleDate(Date date, int hour, int minute) {
    Calendar cs = Calendar.getInstance();
    cs.setTime(date);
    cs.set(Calendar.HOUR_OF_DAY, hour);
    cs.set(Calendar.MINUTE, minute);
    return cs.getTime();
  }

  /**
   * 返回两个时间相差的逻辑单位。
   * 
   * @param before
   *          靠前的时间
   * @param after
   *          靠后的时间
   * @param unit
   *          时间单位，可能的值:second,minute,hour。缺省是second
   * @return 两个时间的差距值
   */
  public static int getInterval(Date before, Date after, String unit) {
    long l1 = before.getTime();
    long l2 = after.getTime();
    long interval = l2 - l1;
    long sec = interval / 1000l;
    long min = sec / 60;
    long hour = min / 60;
    if (unit == null || unit.equals("second")) {
      return (int) sec;
    }
    if (unit.equals("minute")) {
      return (int) min;
    }
    if (unit.equals("hour")) {
      return (int) hour;
    }
    return (int) sec; // default
  }

  /**
   * 返回查询时间的描述字符串
   * 
   * @param date
   *          查询时间
   * @return 查询时间的描述字符串
   */
  public static String getQueryTimeString(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月"
           + cal.get(Calendar.DAY_OF_MONTH) + "日" + cal.get(Calendar.HOUR_OF_DAY) + "时"
           + cal.get(Calendar.MINUTE) + "分";
  }

  public static String getSmartTime(int span) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.HOUR_OF_DAY, span);
    return DateConvertor.getTimeString(c.getTime(), "yyyy-MM-dd");
  }

  public static int getSpanHourFromCurr(int span) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.HOUR_OF_DAY, span);
    return c.get(Calendar.HOUR_OF_DAY);
  }

  public static int getSpanMinuteFromCurr(int span) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MINUTE, span);
    return c.get(Calendar.MINUTE);
  }

  /**
   * <p>
   * 基于一个时间点，返回一个时间区间。
   * </p>
   * <p>
   * 从基准点向外扩展的选项有
   * </p>
   * <table border="1">
   * <tr>
   * <td>代表字符串</td>
   * <td>含义</td>
   * </tr>
   * <tr>
   * <td>?day</td>
   * <td>向前扩展1天</td>
   * </tr>
   * <tr>
   * <td>?week</td>
   * <td>向前扩展1周</td>
   * </tr>
   * <tr>
   * <td>?month</td>
   * <td>向前扩展1月</td>
   * </tr>
   * <tr>
   * <td>?year</td>
   * <td>向前扩展1年</td>
   * </tr>
   * <tr>
   * <td>day?</td>
   * <td>向后扩展1天</td>
   * </tr>
   * <tr>
   * <td>week?</td>
   * <td>向后扩展1周</td>
   * </tr>
   * <tr>
   * <td>month?</td>
   * <td>向后扩展1月</td>
   * </tr>
   * <tr>
   * <td>year?</td>
   * <td>向后扩展1年</td>
   * </tr>
   * <tr>
   * <td>?day?</td>
   * <td>向前后扩展1天</td>
   * </tr>
   * <tr>
   * <td>?week?</td>
   * <td>向前后扩展1周</td>
   * </tr>
   * <tr>
   * <td>?month?</td>
   * <td>向前后扩展1月</td>
   * </tr>
   * <tr>
   * <td>?year?</td>
   * <td>向前后扩展1年</td>
   * </tr>
   * </table>
   * 
   * @param period
   *          扩展选项，见表中描述
   * @param date
   *          基准点
   * @param title
   *          标题(目前无用，置null即可)
   * @return Date数组，0位为开始时间，1位为结束时间
   */
  public static Date[] getSpanPeriod(String period, Date date, String[] title) {
    Date[] res = new Date[2];
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (period.equals(PeriodConstants.DAY_INCLUDE_BE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR,
                              PeriodConstants.BEFORE_AFTER, 1);
    }
    if (period.equals(PeriodConstants.DAY_INCLUDE_BE_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR, PeriodConstants.BEFORE, 1);
    }
    if (period.equals(PeriodConstants.DAY_INCLUDE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR, PeriodConstants.AFTER, 1);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_BE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR,
                              PeriodConstants.BEFORE_AFTER, 1);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_BE_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR, PeriodConstants.BEFORE, 1);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR, PeriodConstants.AFTER, 1);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_BE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.BEFORE_AFTER, 1);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_BE_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.BEFORE, 1);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.AFTER, 1);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_BE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.BEFORE_AFTER, 1);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_BE_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.BEFORE, 1);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_AF_1)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.AFTER, 1);
    }
    return res;
  }

  /**
   * <p>
   * 基于一个时间点，返回一个时间区间。
   * </p>
   * <p>
   * 从基准点向外扩展的选项有
   * </p>
   * <table border="1">
   * <tr>
   * <td>代表字符串</td>
   * <td>含义</td>
   * </tr>
   * <tr>
   * <td>*day</td>
   * <td>向前扩展n天</td>
   * </tr>
   * <tr>
   * <td>*week</td>
   * <td>向前扩展n周</td>
   * </tr>
   * <tr>
   * <td>*month</td>
   * <td>向前扩展n月</td>
   * </tr>
   * <tr>
   * <td>*year</td>
   * <td>向前扩展n年</td>
   * </tr>
   * <tr>
   * <td>day*</td>
   * <td>向后扩展n天</td>
   * </tr>
   * <tr>
   * <td>week*</td>
   * <td>向后扩展n周</td>
   * </tr>
   * <tr>
   * <td>month*</td>
   * <td>向后扩展n月</td>
   * </tr>
   * <tr>
   * <td>year*</td>
   * <td>向后扩展n年</td>
   * </tr>
   * <tr>
   * <td>*day*</td>
   * <td>向前后扩展n天</td>
   * </tr>
   * <tr>
   * <td>*week*</td>
   * <td>向前后扩展n周</td>
   * </tr>
   * <tr>
   * <td>*month*</td>
   * <td>向前后扩展n月</td>
   * </tr>
   * <tr>
   * <td>*year*</td>
   * <td>向前后扩展n年</td>
   * </tr>
   * </table>
   * 
   * @param period
   *          扩展选项，见表中描述
   * @param n
   *          扩展值
   * @param date
   *          基准点
   * @param title
   *          标题(目前无用，置null即可)
   * @return Date数组，0位为开始时间，1位为结束时间
   */
  public static Date[] getSpanPeriod(String period, int n, Date date, String[] title) {
    Date[] res = new Date[2];
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (period.equals(PeriodConstants.DAY_INCLUDE_BE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR,
                              PeriodConstants.BEFORE_AFTER, n);
    }
    if (period.equals(PeriodConstants.DAY_INCLUDE_BE_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR, PeriodConstants.BEFORE, n);
    }
    if (period.equals(PeriodConstants.DAY_INCLUDE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.DAY_OF_YEAR, PeriodConstants.AFTER, n);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_BE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR,
                              PeriodConstants.BEFORE_AFTER, n);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_BE_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR, PeriodConstants.BEFORE, n);
    }
    if (period.equals(PeriodConstants.WEEK_INCLUDE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.WEEK_OF_YEAR, PeriodConstants.AFTER, n);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_BE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.BEFORE_AFTER, n);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_BE_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.BEFORE, n);
    }
    if (period.equals(PeriodConstants.MONTH_INCLUDE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.MONTH, PeriodConstants.AFTER, n);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_BE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.BEFORE_AFTER, n);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_BE_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.BEFORE, n);
    }
    if (period.equals(PeriodConstants.YEAR_INCLUDE_AF_N)) {
      DateConvertor.setPeriod(cal, res, Calendar.YEAR, PeriodConstants.AFTER, n);
    }
    return res;
  }

  /**
   * 根据一个时间基准点，返回一个标准时间逻辑单位的开始和结束时间。例如一天，一周，一月，一季度和一年
   * <p>
   * 从基准点向外扩展的选项有
   * </p>
   * <table border="1">
   * <tr>
   * <td>代表字符串</td>
   * <td>含义</td>
   * </tr>
   * <tr>
   * <td>day</td>
   * <td>时间点所在的当天</td>
   * </tr>
   * <tr>
   * <td>week</td>
   * <td>时间点所在的当周</td>
   * </tr>
   * <tr>
   * <td>month</td>
   * <td>时间点所在的当月</td>
   * </tr>
   * <tr>
   * <td>quarter</td>
   * <td>时间点所在的当季度</td>
   * </tr>
   * <tr>
   * <td>year</td>
   * <td>时间点所在的当年</td>
   * </tr>
   * </table>
   * 
   * @param period
   *          扩展选项
   * @param date
   *          基准点
   * @param title
   *          可能的返回标题。1个大小的String数组
   * @return Date数组，0位为开始时间，1位为结束时间
   */
  public static Date[] getStdPeriod(String period, Date date, String[] title) {
    // only string array can change para value
    Date[] res = new Date[2];
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);

    if (period.equals(PeriodConstants.DAY_CURRENT)) {
      if (title != null) {
        title[0] =
          cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月"
            + cal.get(Calendar.DAY_OF_MONTH) + "日";
      }
      Date tmp = cal.getTime();
      res[0] = tmp;
      cal.add(Calendar.DAY_OF_YEAR, 1);
      res[1] = cal.getTime();
    }
    if (period.equals(PeriodConstants.WEEK_CURRENT)) {
      if (title != null) {
        title[0] = cal.get(Calendar.YEAR) + "年第" + cal.get(Calendar.WEEK_OF_YEAR) + "周";
      }
      cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
      Date tmp = cal.getTime();
      res[0] = tmp;
      cal.add(Calendar.WEEK_OF_YEAR, 1);
      res[1] = cal.getTime();
    }
    if (period.equals(PeriodConstants.MONTH_CURRENT)) {
      if (title != null) {
        title[0] = cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月份";
      }
      cal.set(Calendar.DAY_OF_MONTH, 1);
      Date tmp = cal.getTime();
      res[0] = tmp;
      cal.add(Calendar.MONTH, 1);
      res[1] = cal.getTime();
    }
    if (period.equals(PeriodConstants.QUARTER_CURRENT)) {
      if (contains(cal.get(Calendar.MONTH), firstQuarter)) {
        setQuarter(title, res, cal, "一", Calendar.JANUARY);
      } else if (contains(cal.get(Calendar.MONTH), secondQuarter)) {
        setQuarter(title, res, cal, "二", Calendar.APRIL);
      } else if (contains(cal.get(Calendar.MONTH), thirdQuarter)) {
        setQuarter(title, res, cal, "三", Calendar.JULY);
      } else if (contains(cal.get(Calendar.MONTH), forthQuarter)) {
        setQuarter(title, res, cal, "四", Calendar.OCTOBER);
      }
    }
    if (period.equals(PeriodConstants.YEAR_CURRENT)) {
      if (title != null) {
        title[0] = cal.get(Calendar.YEAR) + "年";
      }
      cal.set(Calendar.DAY_OF_YEAR, 1);
      Date tmp = cal.getTime();
      res[0] = tmp;
      cal.add(Calendar.YEAR, 1);
      res[1] = cal.getTime();
    }
    return res;
  }
  /** 
  * 以当前时间为基准，返回24小时的时间区间
  * 
  * @param perDay 几个24小时
  * @param date   基准点
  * @return Date数组，0位为开始时间，1位为结束时间
  */
  public static Date[] getXDayPeriod(int perDay, Date date) {
	    Date[] res = new Date[2];
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, -perDay*12);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    res[0] = cal.getTime();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, perDay*12);
	    res[1] = cal.getTime();
	    
	    return res;
	  }
  
  /**
   * 返回特定的当前时间字符串。输出的时间格式由pattern决定
   * 
   * @return 特定的时间字符串
   */
  public static String getTimeString() {
    return getTimeString(new Date());
  }

  /**
   * 返回特定的时间字符串。输出的时间格式为"yyyy-MM-dd HH:mm:ss"
   * 
   * @param date
   *          时间
   * @return 特定的时间字符串
   */
  public static String getTimeString(Date date) {
    return getTimeString(date, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 返回特定的时间字符串。输出的时间格式由pattern决定
   * 
   * @param date
   *          时间
   * @param pattern
   *          时间的匹配字符串
   * @return 特定的时间字符串
   */
  public static String getTimeString(Date date, String pattern) {
    return DateConvertor.getTimeString(date, pattern, true);
  }

  /**
   * 返回特定的时间字符串。输出的时间格式由pattern决定
   * 
   * @param date
   *          时间
   * @param pattern
   *          时间的匹配字符串
   * @param returnCurrIfNull
   *          如果date为空，是否返回当前时间
   * @return 特定的时间字符串
   */
  public static String getTimeString(Date date, String pattern, boolean returnCurrIfNull) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    if (date == null && !returnCurrIfNull) {
      return "";
    }
    if (date == null) {
      date = new Date();
    }
    return sdf.format(date);
  }

  /**
   * 返回特定的当前时间字符串。输出的时间格式为"yyyy-MM-dd HH:mm:ss"
   * 
   * @param pattern
   *          时间的匹配字符串
   * @return 特定的时间字符串
   */
  public static String getTimeString(String pattern) {
    return getTimeString(new Date(), pattern);
  }

  public static void main(String[] args) {
//    Date[] span = DateConvertor.getSpanPeriod("*day", 3, new Date(), null);
//    log.info(span[0]);
//    log.info(span[1]);
	  String aa = getWeekFirstDay(new Date(),"yyyy-MM-dd",true);
    System.out.println(aa);
//    try {
//      log.info(DateConvertor.parseDate("20090000", "20080101", "yyyyMMdd"));
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//      
//    }
  }

  private static void setPeriod(Calendar std, Date[] res, int item, int beaf, int n) {
    if (beaf == PeriodConstants.BEFORE_AFTER) {
      std.add(item, 0 - n);
      res[0] = std.getTime();
      std.add(item, 2 * n);
      res[1] = std.getTime();
    }
    if (beaf == PeriodConstants.BEFORE) {
      res[1] = std.getTime();
      std.add(item, 0 - n);
      res[0] = std.getTime();
    }
    if (beaf == PeriodConstants.AFTER) {
      res[0] = std.getTime();
      std.add(item, n);
      res[1] = std.getTime();
    }
  }

  private static void setQuarter(String[] title, Date[] res, Calendar cal, String num,
                                 int startMonth) {
    if (title != null) {
      title[0] = cal.get(Calendar.YEAR) + "年第" + num + "季度";
    }
    cal.set(Calendar.MONTH, startMonth);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    res[0] = cal.getTime();
    cal.add(Calendar.MONTH, 3);
    res[1] = cal.getTime();
  }

  public static String toDifferenceString(long diff, boolean showAll) {
    String sign = "";
    if (diff < 0) {
      diff = 0l - diff;
      sign = "-";
    }
    if (diff < 1000) {
      return diff + "毫秒";
    }
    long day = diff / 86400000;
    long hour = (diff - (day * 86400000)) / 3600000;
    long minute = (diff - (hour * 3600000) - (day * 86400000)) / 60000;
    long second = ((diff - (hour * 3600000) - (day * 86400000)) - minute * 60000) / 1000;
    String dayS =
      showAll ? String.valueOf(day) + "天" : (day != 0 ? String.valueOf(day) + "天" : "");
    String hourS =
      showAll ? String.valueOf(hour) + "小时" : (hour != 0 ? String.valueOf(hour) + "小时"
                                                        : "");
    String minuteS =
      showAll ? String.valueOf(minute) + "分" : (minute != 0 ? String.valueOf(minute)
                                                              + "分" : "");
    String secondS =
      showAll ? String.valueOf(second) + "秒" : (second != 0 ? String.valueOf(second)
                                                              + "秒" : "");
    return sign + dayS + hourS + minuteS + secondS;
  }

  /**
   * 返回两个时间相差的逻辑单位，例如*天*小时*分*秒。不够1秒的返回**毫秒。可用于“历时”功能。
   * 
   * @param before
   *          靠前的时间
   * @param after
   *          靠后的时间
   * @param showAll
   *          是否显示全部逻辑单位，如果是，则可能显示0天0小时0分*秒
   * @return 描述差距的字符串
   */
  public static String toDifferenceString(Date before, Date after, boolean showAll) {
    return DateConvertor.toDifferenceString(after.getTime() - before.getTime(), showAll);
  }

  public DateConvertor() {
  }
  
  /**
   * souceDate :要获得某周第一天和最后一天的基准时间
   * fromatstr:返回字符串格式
   * isStart:true-返回第一天；false返回最后一天
   * 说明：获得输入日期所在周的第一天  第一天是星期一
   * */
  public static String getWeekFirstDay(Date sourceDate,String formatstr,boolean isStart){
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(sourceDate);
	  int d = 0;
	  if(cal.get(Calendar.DAY_OF_WEEK)==1){
		  d = -6;
	  }else{
		  d = 2-cal.get(Calendar.DAY_OF_WEEK);
	  }
	  cal.add(Calendar.DAY_OF_WEEK, d);
	//所在周开始日期
	  if(isStart){
		  return new SimpleDateFormat(formatstr).format(cal.getTime());
		 }else{
		  cal.add(Calendar.DAY_OF_WEEK, 6);
			//所在周结束日期
		  return new SimpleDateFormat(formatstr).format(cal.getTime());
	  }

  }
  
}
