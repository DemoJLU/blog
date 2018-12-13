package com.danxiaochong.blog.common;

public class PeriodConstants {
  public PeriodConstants() {
  }

  public final static int NAN = 0xFFFF;

  public final static String UNKNOWN_S = "未知";
  
  public final static String QUERY_CURRENT = "current";

  //standard period include current date_point
  public final static String DAY_CURRENT = "day";
  public final static String WEEK_CURRENT = "week";
  public final static String MONTH_CURRENT = "month";
  public final static String QUARTER_CURRENT = "quarter";
  public final static String YEAR_CURRENT = "year";

  //period with one before and after include current date_point
  public final static String DAY_INCLUDE_BE_AF_1 = "?day?";
  public final static String WEEK_INCLUDE_BE_AF_1 = "?week?";
  public final static String MONTH_INCLUDE_BE_AF_1 = "?month?";
  public final static String YEAR_INCLUDE_BE_AF_1 = "?year?";

  //period with some before and after include current date_point
  public final static String DAY_INCLUDE_BE_AF_N = "*day*";
  public final static String WEEK_INCLUDE_BE_AF_N = "*week*";
  public final static String MONTH_INCLUDE_BE_AF_N = "*month*";
  public final static String YEAR_INCLUDE_BE_AF_N = "*year*";

  //period with one before  include current date_point
  public final static String DAY_INCLUDE_BE_1 = "?day";
  public final static String WEEK_INCLUDE_BE_1 = "?week";
  public final static String MONTH_INCLUDE_BE_1 = "?month";
  public final static String YEAR_INCLUDE_BE_1 = "?year";

  //period with one after include current date_point
  public final static String DAY_INCLUDE_AF_1 = "day?";
  public final static String WEEK_INCLUDE_AF_1 = "week?";
  public final static String MONTH_INCLUDE_AF_1 = "month?";
  public final static String YEAR_INCLUDE_AF_1 = "year?";

  //period with some before include current date_point
  public final static String DAY_INCLUDE_BE_N = "*day";
  public final static String WEEK_INCLUDE_BE_N = "*week";
  public final static String MONTH_INCLUDE_BE_N = "*month";
  public final static String YEAR_INCLUDE_BE_N = "*year";

  //period with some after include current date_point
  public final static String DAY_INCLUDE_AF_N = "day*";
  public final static String WEEK_INCLUDE_AF_N = "week*";
  public final static String MONTH_INCLUDE_AF_N = "month*";
  public final static String YEAR_INCLUDE_AF_N = "year*";

  //relation
  public static final int BEFORE = -1;
  public static final int AFTER = 1;
  public static final int BEFORE_AFTER = 0;

  public static String toString(String expression) {
    if (expression.equals(PeriodConstants.QUERY_CURRENT)) {
      return "查询时段";
    }
    if (expression.equals(PeriodConstants.DAY_CURRENT)) {
      return "当日";
    }
    if (expression.equals(PeriodConstants.MONTH_CURRENT)) {
      return "当月";
    }
    if (expression.equals(PeriodConstants.DAY_INCLUDE_BE_1)) {
      return "向前一日";
    }
    if (expression.equals(PeriodConstants.WEEK_INCLUDE_BE_1)) {
      return "向前一周";
    }

    return UNKNOWN_S;
  }
}
