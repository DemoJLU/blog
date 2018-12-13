package com.danxiaochong.blog.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
  static Logger logger = Logger.getLogger(Utils.class);
  public static final String LINE_SEPARATOR = System
      .getProperty("line.separator");
  public static final DecimalFormat df0 = new DecimalFormat("0");
  public static final DecimalFormat df1 = new DecimalFormat("0.0");
  public static final DecimalFormat df2 = new DecimalFormat("0.00");
  public static final DecimalFormat df3 = new DecimalFormat("0.000");

  /**
   * 加载软件需要的图标
   * 
   * @param filename String 纯文件名，路径缺省为%ROOT%/images
   * @return ImageIcon
   */
  public static ImageIcon readImageIcon(String filename) {
    URL url = Utils.class.getClassLoader().getResource("images/" + filename);
    ImageIcon img;
    if (url == null || !(new File(url.getPath()).exists())) {
      // 如果没有找到对应图片，使用缺省图片
      url = Utils.class.getClassLoader().getResource("images/bluedot.png");
      img = new ImageIcon(url);
    }
    else
      img = new ImageIcon(url);
    return img;
  }

  /**
   * 加载软件的声音文件
   * 
   * @param filename String 纯文件名，路径缺省为%ROOT%/wave
   * @return File
   */
  public static File readSoundFile(String filename) {
    URL url = Utils.class.getClassLoader().getResource("wave/" + filename);
    return new File(url.getPath());
  }

  /**
   * 获得软件根目录绝对路径，返回路径包括最后的File.seperator，例如"/"
   * 
   * @return String
   */
  public static String getAppPath() {
    URL url = Utils.class.getClassLoader().getResource(".");
    if (url != null)
      return url.getPath();
    else
      return null;
  }

  /**
   * 将数字转换成为前面以0补齐的字符串 例如：4转换为"0004"
   * 
   * @param id int 需要转换的数字
   * @param length int 转换后的长度或者位数
   * @return String
   */
  public static String makeId(int id, int length) {
    String ids = String.valueOf(id);
    int len = ids.length();
    for (int i = 0; i < length - len; i++) {
      ids = "0" + ids;
    }
    return ids;
  }

  public static final int ONLY_DATE = 1; // YYYY-MM-DD
  public static final int ONLY_TIME = 2; // HH24:MI:SS
  public static final int DATETIME_WITH_SEPARATOR = 3; // YYYY-MM-DD HH24:MI:SS
  public static final int DATETIME_NO_SEPARATOR = 4; // YYYYMMDDHH24MISS
  public static final int DATETIME_FULL = 5; // 包括毫秒无分隔符 YYYYMMDDHH24MISSMS
  public static final int MDHM = 6; // 月日时分 MMDDHH24MI
  public static final int YEAR_MONTH = 7; // 年月 YYYY-MM
  public static final int SHORT_DATETIME = 8; // MM-DD HH24:MI
  public static final int DATETIME_NO_SECOND = 9; // YYYYMMDDHH24MI
  public static final int DATETIME_SHORT_NO_SEPARATOR = 10; // YYYYMMDD
  public static final int DATETIME_FULL_WITH_SEPARATOR = 11; // 包括毫秒无分隔符
                                                             // YYYY-MM-DD
                                                             // HH24:MI:SS.MS

  /**
   * 转换日期时间为字符格式
   * 
   * @param cal Calendar
   * @param type int 1：只返回日期 2：只返回时间 3：返回YYYY-MM-DD HH24:MI:SS
   *          4:返回YYYYMMDDHH24MISS 5:返回YYYYMMDDHH24MISSMS
   * @return String
   */
  public static String calendarToString(Calendar cal, int type) {
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int minute = cal.get(Calendar.MINUTE);
    int second = cal.get(Calendar.SECOND);
    int ms = cal.get(Calendar.MILLISECOND);
    String sMonth, sDay, sHour, sMinute, sSecond, sMs;
    if (month < 10)
      sMonth = "0" + String.valueOf(month);
    else
      sMonth = String.valueOf(month);
    if (day < 10)
      sDay = "0" + String.valueOf(day);
    else
      sDay = String.valueOf(day);
    if (hour < 10)
      sHour = "0" + String.valueOf(hour);
    else
      sHour = String.valueOf(hour);
    if (minute < 10)
      sMinute = "0" + String.valueOf(minute);
    else
      sMinute = String.valueOf(minute);
    if (second < 10)
      sSecond = "0" + String.valueOf(second);
    else
      sSecond = String.valueOf(second);
    if (ms < 10)
      sMs = "00" + String.valueOf(ms);
    else if (ms < 100)
      sMs = "0" + String.valueOf(ms);
    else
      sMs = String.valueOf(ms);
    switch (type) {
    case ONLY_DATE:
      return year + "-" + sMonth + "-" + sDay;
    case ONLY_TIME:
      return sHour + ":" + sMinute + ":" + sSecond;
    case DATETIME_WITH_SEPARATOR:
      return year + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute
          + ":" + sSecond;
    case DATETIME_NO_SEPARATOR:
      return year + sMonth + sDay + sHour + sMinute + sSecond;
    case DATETIME_FULL:
      return year + sMonth + sDay + sHour + sMinute + sSecond + sMs;
    case MDHM:
      return sMonth + sDay + sHour + sMinute;
    case YEAR_MONTH:
      return year + "-" + sMonth;
    case SHORT_DATETIME:
      return sMonth + "-" + sDay + " " + sHour + ":" + sMinute;
    case DATETIME_NO_SECOND:
      return year + sMonth + sDay + sHour + sMinute;
    case DATETIME_SHORT_NO_SEPARATOR:
      return year + sMonth + sDay;
    case DATETIME_FULL_WITH_SEPARATOR:
      return year + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute
          + ":" + sSecond + "." + sMs;
    default:
      return "";
    }
  }

  /**
   * 字符串转换为Date格式
   * 
   * @param s String
   * @param type int
   * @return Date
   */
  public static Date parseDate(String s, int type) {
    SimpleDateFormat df;
    switch (type) {
    case ONLY_DATE:
      df = new SimpleDateFormat("yyyy-MM-dd");
      break;
    case ONLY_TIME:
      df = new SimpleDateFormat("HH:mm:ss");
      break;
    case DATETIME_WITH_SEPARATOR:
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      break;
    case DATETIME_NO_SEPARATOR:
      df = new SimpleDateFormat("yyyyMMddHHmmss");
      break;
    case DATETIME_FULL:
      df = new SimpleDateFormat("yyyyMMddHHmmssSS");
      break;
    case MDHM:
      df = new SimpleDateFormat("MMddmmss");
      break;
    case YEAR_MONTH:
      df = new SimpleDateFormat("yyyy-MM");
      break;
    case SHORT_DATETIME:
      df = new SimpleDateFormat("MM-dd HH:mm");
      break;
    case DATETIME_SHORT_NO_SEPARATOR:
      df = new SimpleDateFormat("yyyyMMdd");
      break;
    default:
      df = new SimpleDateFormat("yyyy-MM-dd");
      break;
    }
    Date dt;
    if (s == null || s.trim().equals(""))
      dt = null;
    else {
      dt = df.parse(s.trim(), new ParsePosition(0));
    }
    return dt;
  }

  public static String dateToString(Date dt, int type) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dt);
    return calendarToString(cal, type);
  }

  public static String now() {
    return calendarToString(Calendar.getInstance(), DATETIME_WITH_SEPARATOR);
  }

  // 20060718192500转换为2006-07-18和19:25:00
  public static String[] separateDateTime(String dt) {
    String[] ret = new String[2];
    if (dt != null && dt.length() == 14) {
      ret[0] = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-"
          + dt.substring(6, 8);
      ret[1] = dt.substring(8, 10) + ":" + dt.substring(10, 12) + ":"
          + dt.substring(12, 14);
    }
    return ret;
  }

  /**
   * 将Calendar的时间部分都设置为0
   * 
   * @param cal Calendar
   */
  public static void clearTime(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
  }

  /**
   * 打印异常堆栈
   * 
   * @param e Exception
   * @return String 返回字符串表示形式
   */
  public static String printExceptionStack(Exception e) {
    StackTraceElement[] stack = e.getStackTrace();
    StringBuilder ret = new StringBuilder();
    ret.append(e);
    ret.append(System.getProperty("line.separator"));
    for (int i = 0; i < stack.length; i++) {
      if (stack[i].getClassName().startsWith("com.cc502")
          || stack[i].getClassName().startsWith("com.kthw")) {
        ret.append(stack[i].getClassName());
        ret.append(",");
        ret.append(stack[i].getMethodName());
        ret.append(",");
        ret.append(stack[i].getLineNumber());
        ret.append(System.getProperty("line.separator"));
      }
    }
    return ret.substring(0);
  }

  /**
   * 打印异常堆栈
   * 
   * @param e Exception
   * @return String 返回字符串表示形式
   */
  public static String printErrorStack(Error e) {
    StackTraceElement[] stack = e.getStackTrace();
    StringBuilder ret = new StringBuilder();
    ret.append(e);
    ret.append(System.getProperty("line.separator"));
    for (int i = 0; i < stack.length; i++) {
      ret.append(stack[i].getClassName());
      ret.append(",");
      ret.append(stack[i].getMethodName());
      ret.append(",");
      ret.append(stack[i].getLineNumber());
      ret.append(System.getProperty("line.separator"));
    }
    return ret.substring(0);
  }

  /**
   * 存储调试文件
   * 
   * @param buffer ByteBuffer 缓冲区
   * @param root String 文件存储目录
   * @param ext String 文件存储的后缀名
   * @return 返回存储的文件名，绝对路径
   */
  public static File saveDebugFile(final ByteBuffer buffer, String root,
      String ext) {
    return saveDebugFile(buffer, root, ext, null);
  }

  /**
   * 存储调试文件
   * 
   * @param buffer ByteBuffer 缓冲区
   * @param root String 文件存储目录
   * @param ext String 文件存储的后缀名
   * @param exName String 附件的文件名 070312[add,ZHYI]附件的文件名
   * @return 返回存储的文件名，绝对路径
   */
  public static File saveDebugFile(final ByteBuffer buffer, String root,
      String ext, String exName) {
    File debugfile = null;
    try {
      Calendar cal = Calendar.getInstance();
      String subdir = calendarToString(cal, ONLY_DATE); // 日期作为子目录，避免一个目录下文件数量太多
      debugfile = new File(root + File.separator + subdir);
      if (!debugfile.exists()) {
        debugfile.mkdirs();
      }
      String filename = root + File.separator + subdir + File.separator
          + calendarToString(cal, DATETIME_FULL);
      if (exName != null)
        filename = filename + exName;
      if (ext != null)
        filename = filename + "." + ext;
      debugfile = new File(filename);
      debugfile.createNewFile();
      FileOutputStream fos = new FileOutputStream(debugfile);
      fos.write(buffer.array(), 0, buffer.limit());
      fos.flush();
      fos.close();
    }
    catch (IOException e) {
      printExceptionStack(e);
    }
    return debugfile;
  }

  public static boolean renameTo(String sourceFilename, String destFilename) {
    File source = new File(sourceFilename);
    File dest = new File(destFilename);
    if (source.exists()) {
      if (dest.exists())
        dest.delete();
      return source.renameTo(dest);
    }
    else {
      return false;
    }
  }

  public static int parseInt(String s) {
    if (s == null || s.trim().length() == 0)
      return 0;
    try {
      return Integer.parseInt(s);
    }
    catch (NumberFormatException e) {
      return 0;
    }
  }

  public static short parseShort(String s) {
    if (s == null || s.trim().length() == 0)
      return 0;
    try {
      return Short.parseShort(s);
    }
    catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * 检验字符串是否是汉字
   * 
   * @param str String
   * @return boolean
   */
  public boolean isGB2312(String str) {
    char[] chars = str.toCharArray();
    boolean isGB2312 = false;
    for (int i = 0; i < chars.length; i++) {
      byte[] bytes = ("" + chars[i]).getBytes();
      if (bytes.length == 2) {
        int[] ints = new int[2];
        ints[0] = bytes[0] & 0xff;
        ints[1] = bytes[1] & 0xff;
        if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
            && ints[1] <= 0xFE) {
          isGB2312 = true;
          break;
        }
      }
    }
    return isGB2312;
  }

  /**
   * 无符号数转换为整型
   * 
   * @param b byte
   * @return int
   */
  public static int B2I(byte b) { // unsignedByteToInt
    return (int) (b & 0xFF);
  }

  /**
   * 无符号数转换为短整型
   * 
   * @param b byte
   * @return int
   */
  public static short B2S(byte b) { // unsignedByteToShort
    return (short) (b & 0xFF);
  }

  public static int SE2I(byte[] buf, int offset) { // smallEndianToUnsignedInt
    return B2I(buf[offset + 3]) * 256 * 256 * 256 + B2I(buf[offset + 2]) * 256
        * 256 + B2I(buf[offset + 1]) * 256 + B2I(buf[offset]);
  }

  public static int SE2S(byte[] buf, int offset) { // smallEndianToUnsignedShort
    return B2I(buf[offset + 1]) * 256 + B2I(buf[offset]);
  }

  public static int BE2I(byte[] buf, int offset) { // bigEndianToUnsignedInt
    return B2I(buf[offset]) * 256 * 256 * 256 + B2I(buf[offset + 1]) * 256
        * 256 + B2I(buf[offset + 2]) * 256 + B2I(buf[offset + 3]);
  }

  public static int BE2S(byte[] buf, int offset) { // bigEndianToUnsignedShort
    return B2I(buf[offset]) * 256 + B2I(buf[offset + 1]);
  }

  /**
   * ComboBox 冒泡法排序列表
   * 
   * @param model DefaultComboBoxModel
   * @return DefaultComboBoxModel 注意model中的Element必须支持compareTo接口
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static DefaultComboBoxModel sortItem(DefaultComboBoxModel model) {
    int size = model.getSize();
    Object item1, item2, item;
    for (int i = 0; i < size - 1; i++) {
      for (int j = size - 1; j > i; j--) {
        item1 = model.getElementAt(j);
        item2 = model.getElementAt(j - 1);
        if (item1 != null && item2 != null) {
          if (((Comparable) item1).compareTo((Comparable) item2) < 0) {
            item = item1;
            model.removeElementAt(j);
            model.insertElementAt(item, j - 1);
          }
        }
      }
    }
    return model;
  }

  /**
   * JTable表格按照指定列排序
   * 
   * @param model DefaultTableModel
   * @param col int 列编号，从0开始
   * @return DefaultTableModel
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static DefaultTableModel sortRow(DefaultTableModel model, int col) {
    int size = model.getRowCount();
    Object item1, item2;
    Vector<?> row;
    for (int i = 0; i < size - 1; i++) {
      for (int j = size - 1; j > i; j--) {
        item1 = model.getValueAt(j, col);
        item2 = model.getValueAt(j - 1, col);
        row = (Vector<?>) model.getDataVector().get(j);
        if (item1 == null && item2 != null) {
          model.removeRow(j);
          model.insertRow(j - 1, row);
        }
        else if (item1 != null && item2 != null) {
          if (((Comparable) item1).compareTo((Comparable) item2) < 0) {
            model.removeRow(j);
            model.insertRow(j - 1, row);
          }
        }
      }
    }
    return model;
  }

  // 字节置位为1，低字节在前，id序号从低位(0)数到高位
  public static void setBit(byte[] bytes, int id) {
    int k = id / 8;
    int r = id - k * 8;
    int m = 1 << r;
    bytes[k] = (byte) (bytes[k] | m);
  }

  // 字节按位清0，低字节在前，id序号从低位(0)数到高位
  public static void clrBit(byte[] bytes, int id) {
    int k = id / 8;
    int r = id - k * 8;
    int m = 1 << r;
    m = ~m;
    bytes[k] = (byte) (bytes[k] & m);
  }

  /**
   * BCD码转换为16进制
   * 
   * @param n int
   * @return int
   */
  public static int bcd2hex(int n) {
    return (((n & 0xf0) >>> 4) * 10) + (n & 0xf);
  }

  /**
   * 数字转换为BCD码
   * 
   * @param n int
   * @return int
   */
  public static int hex2bcd(int n) {
    return ((n / 10) << 4) + (n % 10);
  }

  public static void fillBytes(byte[] dest, int offset, byte[] src) {
    if (src == null)
      return;
    for (int i = 0; i < src.length; i++) {
      dest[offset + i] = src[i];
    }
  }

  public static void fillBytesString(byte[] b, int offset, String s) {
    if (s == null)
      return;
    for (int i = 0; i < s.getBytes().length; i++) {
      b[offset + i] = s.getBytes()[i];
    }
  }

  public static void fillBytesString(byte[] b, int offset, String s, int length) {
    String str = s;
    if (str == null)
      str = "";
    if (str.getBytes().length > length) {
      for (int i = 0; i < length; i++)
        b[offset + i] = str.getBytes()[i];
    }
    else {
      for (int i = 0; i < length; i++) {
        if (i < str.getBytes().length)
          b[offset + i] = str.getBytes()[i];
        else
          b[offset + i] = 0;
      }
    }
  }

  /**
   * 填充byte数组，低字节在前，Little Endien
   * 
   * @param b byte[]
   * @param s short
   * @param offset int
   */
  public static void fillBytesShortLE(byte[] b, short s, int offset) {
    int k = (int) (s & 0xFFFF);
    b[offset] = (byte) (k & 0xFF);
    b[offset + 1] = (byte) ((k & 0xFF00) >>> 8);
  }

  public static void fillBytesShortBE(byte[] b, short s, int offset) {
    int k = (int) (s & 0xFFFF);
    b[offset] = (byte) ((k & 0xFF00) >>> 8);
    b[offset + 1] = (byte) (k & 0xFF);
  }

  /**
   * 填充byte数组，低字节在前，Little Endien
   * 
   * @param b byte[]
   * @param s int
   * @param offset int
   */
  public static void fillBytesIntLE(byte[] b, int s, int offset) {
    b[offset] = (byte) (s & 0xFF);
    b[offset + 1] = (byte) ((s & 0xFF00) >>> 8);
    b[offset + 2] = (byte) ((s & 0xFF0000) >>> 16);
    b[offset + 3] = (byte) ((s & 0xFF000000) >>> 24);
  }

  public static void fillBytesIntBE(byte[] b, int s, int offset) {
    b[offset + 3] = (byte) (s & 0xFF);
    b[offset + 2] = (byte) ((s & 0xFF00) >>> 8);
    b[offset + 1] = (byte) ((s & 0xFF0000) >>> 16);
    b[offset] = (byte) ((s & 0xFF000000) >>> 24);
  }

  /**
   * 使用系统的ping命令获取连接时间
   * 
   * @param host String 主机，可以是IP或者域名
   * @param packetSize int Ping使用的包大小
   * @return int 返回连接的毫秒数
   */
  public static int ping0(String host, int packetSize) {
    String system = (String) (System.getProperty("os.name")).toLowerCase();
    String command;
    if (system.indexOf("win") != -1) {
      // command = "ping -w 5000 -l " + packetSize + " " + host; //设置5000毫秒的超时
      command = "ping -l " + packetSize + " " + host;
    }
    else if (system.indexOf("linux") != -1) {
      command = "ping -c 1 -s " + packetSize + " " + host; // ping 1次
    }
    else {
      command = "ping " + host;
    }
    float minTime = Integer.MAX_VALUE;
    float curTime;
    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader in = new BufferedReader(new InputStreamReader(
          process.getInputStream()));
      String line = null;
      int count = 7, index;
      String millis;
      // 最多只读7行，超过7行就不适用于Linux
      while ((line = in.readLine()) != null && count-- != 0) {
        line = line.toLowerCase();
        if ((index = line.indexOf("time")) != -1) {
          byte[] buf = line.getBytes();
          int start = 0, end = buf.length, i, j;
          for (i = index + 4; i < buf.length; i++) {
            if (Character.isDigit((char) buf[i])) {
              start = i;
              break;
            }
          }
          if (i == buf.length)
            continue;
          for (j = start; j < buf.length; j++) {
            if (Character.isLetter((char) buf[j])) {
              end = j;
              break;
            }
          }
          millis = new String(buf, start, end - start);
          millis = millis.trim();
          curTime = Float.parseFloat(millis);
          if (curTime < minTime) {
            minTime = curTime;
          }
        }
      }
    }
    catch (Exception ex) {
      return Integer.MAX_VALUE;
    }
    return (int) Math.round(minTime);
  }

  public static void print(String info) {
    logger.debug(info);
  }

  public static String rpad(String s, int width) { // 右侧补空格对齐
    StringBuffer ret = new StringBuffer(s);
    int len = s.getBytes().length;
    if (len >= width)
      return s;
    else {
      for (int i = 0; i < width - len; i++) {
        ret.append(" ");
      }
    }
    return ret.substring(0);
  }

  // 省略显示长字符串
  private static final int OMIT_LENGTH = 80;

  public static String omit(String s, int length) {
    if (s != null && s.length() > length) {
      return s.substring(0, length) + "...";
    }
    return s;
  }

  public static String omit(String s) {
    return omit(s, OMIT_LENGTH);
  }

  public static String trim(String s) {
    if (s == null)
      return "";
    else
      return s.trim();
  }

  /**
   * 删除字符串中的空格，包括前后和中间
   * 
   * @param s String 原始字符串
   * @param begin int 起始字符索引，从0计数
   * @return String 删除空格后的字符串
   */
  public static String removeSpace(String s, int begin) {
    if (s != null && s.length() > 0) {
      if (begin < 0 || begin >= s.length())
        return s;
      else {
        StringBuffer s1 = new StringBuffer();
        s1.append(s.substring(0, begin));
        String s2 = s.substring(begin);
        String[] s3 = s2.split(" ");
        for (int i = 0; s3 != null && i < s3.length; i++) {
          s1.append(s3[i].trim());
        }
        return s1.substring(0);
      }
    }
    else
      return "";
  }

  public static String removeSpace(String s) {
    return removeSpace(s, 0);
  }

  public static boolean CopyFile(File in, File out) throws IOException {
    if (!in.exists())
      return false;
    FileInputStream fis = new FileInputStream(in);
    FileOutputStream fos = new FileOutputStream(out);
    byte[] buf = new byte[10240];
    int i = 0;
    while ((i = fis.read(buf)) != -1) {
      fos.write(buf, 0, i);
    }
    fis.close();
    fos.close();
    return true;
  }

  public static String byte2str(byte[] bytes) { // 字节转换为16进制的字符形式,以空格分开字节
    if (bytes == null || bytes.length == 0) {
      return "";
    }
    else {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Byte.toString(bytes[i]));
        sb.append(" ");
      }
      return sb.substring(0, sb.length() - 1);
    }
  }

  public static String byte2ascii(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return "";
    }
    else {
      StringBuffer sb = new StringBuffer();
      byte[] b = new byte[1];
      for (int i = 0; i < bytes.length; i++) {
        if (bytes[i] > 32 && bytes[i] < 127) { // 可见字符
          b[0] = bytes[i];
          sb.append(new String(b));
        }
        else {
          sb.append("/");
          sb.append(Byte.toString(bytes[i]));
          sb.append("/");
        }
      }
      return sb.substring(0);
    }
  }

  /**
   * 公里标转换：如果是纯数字，直接返回，如果是格式DK1234+567转换为数字（米）=1234*1000+567
   * 
   * @param s String
   * @return int
   */
  public static int convertGLB(String s) {
    if (s == null || s.trim().length() == 0) {
      return 0;
    }
    else {
      char[] cc = s.toCharArray();
      boolean digit = true;
      for (int i = 0; i < cc.length; i++) {
        if (!Character.isDigit(cc[i])) {
          digit = false;
          break;
        }
      }
      if (digit) {
        return parseInt(s);
      }
      // 不是纯数字
      String[] ss = s.split("\\+");
      if (ss == null)
        return 0;
      cc = ss[0].toCharArray();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; cc != null && i < cc.length; i++) {
        if (cc[i] == '.') { // 遇到小数点则退出
          break;
        }
        if (Character.isDigit(cc[i])) {
          sb.append(cc[i]);
        }
      }
      int k = 0, m = 0;
      try {
        k = Integer.parseInt(sb.substring(0));
      }
      catch (NumberFormatException e) {
      }
      if (ss.length > 1) {
        cc = ss[1].toCharArray();
        sb.setLength(0);
        for (int i = 0; cc != null && i < cc.length; i++) {
          if (Character.isDigit(cc[i])) {
            sb.append(cc[i]);
          }
        }
        try {
          m = Integer.parseInt(sb.substring(0));
        }
        catch (NumberFormatException e) {
        }
      }
      return k * 1000 + m;
    }
  }

  public static String ostype() {
    return (String) (System.getProperty("os.name")).toLowerCase();
  }

  /**
   * IPv4字符串转换成4个字节
   * 
   * @param ip String
   * @param ips byte[]
   */
  public static void IP2Bytes(String ip, byte[] ips) {
    boolean checkOK = true;
    if (ip == null || ip.trim().length() == 0) {
      checkOK = false;
    }
    else {
      String[] parts = ip.split("\\.");
      if (parts.length == 4) {
        for (int i = 0; i < 4; i++) {
          try {
            int n = Integer.parseInt(parts[i]);
            if (n >= 0 && n <= 255) {
              ips[i] = (byte) n;
            }
            else {
              checkOK = false;
              break;
            }
          }
          catch (NumberFormatException e) {
            checkOK = false;
            break;
          }
        }
      }
      else {
        checkOK = false;
      }
    }
    if (!checkOK) {
      for (int i = 0; i < 4; i++)
        ips[i] = 0;
    }
  }

  public static int IP2Int(String ipv4) {
    if (ipv4 == null || ipv4.length() == 0) {
      return 0;
    }
    else {
      String[] parts = ipv4.split("\\.");
      if (parts.length != 4) {
        return 0;
      }
      else {
        return Utils.parseInt(parts[3]) + Utils.parseInt(parts[2]) * 256
            + Utils.parseInt(parts[1]) * 256 * 256 + Utils.parseInt(parts[0])
            * 256 * 256 * 256;
      }
    }
  }

  /**
   * IPv4转换成格式：0.0.0.0 -> 000.000.000.000
   * 
   * @param ip String
   * @return String
   */
  public static String IPAlign(String ip) {
    if (ip == null) {
      return "";
    }
    String[] parts = ip.split("\\.");
    if (parts.length != 4) {
      return ip;
    }
    else {
      return addZero(parts[0], 3) + "." + addZero(parts[1], 3) + "."
          + addZero(parts[2], 3) + "." + addZero(parts[3], 3);
    }
  }

  /**
   * 前面补0进行字符串对齐
   * 
   * @param s String
   * @param length int
   * @return String
   */
  public static String addZero(String s, int length) {
    if (s.length() >= length) {
      return s;
    }
    else {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < length - s.length(); i++) {
        sb.append("0");
      }
      sb.append(s);
      return sb.substring(0);
    }
  }

  public static String Int2IP(int n) {
    return String.valueOf((n & 0xFF000000) >>> 24) + "."
        + String.valueOf((n & 0xFF0000) >>> 16) + "."
        + String.valueOf((n & 0xFF00) >>> 8) + "." + String.valueOf(n & 0xFF);
  }

  public static int max(int a, int b) {
    return a >= b ? a : b;
  }

  public static int min(int a, int b) {
    return a < b ? a : b;
  }

  public static byte[] ezEncrypt(byte[] buf) {
    if (buf == null)
      return null;
    for (int i = 0; i < buf.length; i++) {
      buf[i] = (byte) (((byte) (buf[i] + 0x77)) ^ 0x78);
    }
    return buf;
  }

  public static byte[] ezDecrypt(byte[] buf) {
    if (buf == null)
      return null;
    for (int i = 0; i < buf.length; i++) {
      buf[i] = (byte) ((buf[i] ^ 0x78) - 0x77);
    }
    return buf;
  }

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }

  /**
   * 将逗号分隔的数字字符串转换为ArrayList
   * 
   * @param s String
   * @return ArrayList
   * @throws NumberFormatException 整型转换异常
   */
  public static ArrayList<Integer> commaToArray(String s)
      throws NumberFormatException {
    if (s == null || s.trim().length() == 0) {
      throw (new NumberFormatException("NULL String"));
    }
    ArrayList<Integer> a = new ArrayList<Integer>();
    String[] parts = s.split(",");
    for (int i = 0; i < parts.length; i++) {
      a.add(new Integer(parts[i]));
    }
    return a;
  }

  /**
   * 整型数组转换成逗号分隔的字符串
   * 
   * @param a ArrayList
   * @return String
   */
  public static String arrayToComma(ArrayList<Integer> a) {
    if (a == null || a.size() == 0) {
      return "";
    }
    if (a.size() == 1) {
      return String.valueOf(a.get(0));
    }
    StringBuilder sb = new StringBuilder();
    sb.append(a.get(0));
    for (int i = 1; i < a.size(); i++) {
      sb.append(",");
      sb.append(a.get(i));
    }
    return sb.substring(0);
  }

  static final char[] PASSWORD_CHAR = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
      'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
      'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
      'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };

  // 口令可以使用的字符a-z,A-Z,0-9,-,_
  public static String enPassword(String s) {
    if (s == null || s.trim().length() == 0) {
      return "";
    }
    String s2 = "";
    int tsize = PASSWORD_CHAR.length;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      boolean match = false;
      for (int j = 0; j < tsize; j++) {
        if (c == PASSWORD_CHAR[j]) {
          int k = j + i * 2 + 3;
          if (k >= tsize) {
            k = k - tsize;
          }
          s2 = s2 + PASSWORD_CHAR[k];
          match = true;
          break;
        }
      }
      if (!match) {
        s2 = s2 + "*";
      }
    }
    return s2;
  }

  public static String dePassword(String s) {
    if (s == null || s.trim().length() == 0) {
      return "";
    }
    String s2 = "";
    int tsize = PASSWORD_CHAR.length;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      boolean match = false;
      for (int j = 0; j < tsize; j++) {
        if (c == PASSWORD_CHAR[j]) {
          int k = j - i * 2 - 3;
          if (k < 0) {
            k = k + tsize;
          }
          s2 = s2 + PASSWORD_CHAR[k];
          match = true;
          break;
        }
      }
      if (!match) {
        s2 = s2 + "*";
      }
    }
    return s2;
  }

  public static boolean checkPassword(String password) { // 校验字符是否合法
    if (password == null || password.trim().length() == 0) {
      return false;
    }
    int tsize = PASSWORD_CHAR.length;
    for (int i = 0; i < password.length(); i++) {
      char c = password.charAt(i);
      boolean match = false;
      for (int j = 0; j < tsize; j++) {
        if (c == PASSWORD_CHAR[j]) {
          match = true;
          break;
        }
      }
      if (!match) {
        return false;
      }
    }
    return true;
  }
  
  static int MAX_KEY_LEN = 8;
  static int MAX_ENCRYPT_LEN = 32;
  static String MYKEY = "IamZhyi";
  public static String encrypt(String in)
  {
    byte[] key = new byte[MAX_KEY_LEN];
    byte[] tmp = new byte[MAX_ENCRYPT_LEN];

    key = MYKEY.getBytes();
    int size = in.length();
    if (size > MAX_ENCRYPT_LEN)
      size = MAX_ENCRYPT_LEN;
    byte[] ins = in.getBytes();

    for (int i = 0; i < size; i++) {
      tmp[i] = ins[i];
      for (int j = 0; j < key.length; j++) {
        tmp[i] = ((byte)(tmp[i] ^ key[j]));
      }
    }
    String stmp2 = "";
    for (int i = 0; i < size; i++) {
      String stmp1 = Integer.toHexString(tmp[i]);
      if (stmp1.length() == 1)
        stmp2 = stmp2 + "0";
      stmp2 = stmp2 + stmp1;
    }
    return stmp2;
  }
  
  
  public static String decrypt(String in)
  {
    byte[] key = new byte[MAX_KEY_LEN];
    byte[] tmp = new byte[MAX_ENCRYPT_LEN];

    key = MYKEY.getBytes();
    int size = in.length() / 2;
    if (size > MAX_ENCRYPT_LEN)
      size = MAX_ENCRYPT_LEN;
    for (int i = 0; i < size; i++) {
      String s = in.substring(i * 2, i * 2 + 2);
      tmp[i] = ((byte)Integer.parseInt(s, 16));
    }

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < key.length; j++)
        tmp[i] = ((byte)(tmp[i] ^ key[j]));
    }
    char[] c = new char[MAX_ENCRYPT_LEN];
    for (int i = 0; i < size; i++)
      c[i] = ((char)tmp[i]);
    String s = "";
    s = String.copyValueOf(c, 0, size);
    c = (char[])null;
    return s;
  }
  
  protected void  testQRCode(){
	  String text = "你好ABCDdisdd_*&%$#@haha";   
      int width = 100;   
      int height = 100;   
      String format = "png";   
      Hashtable hints= new Hashtable();   
      hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
      BitMatrix bitMatrix = null;
	try {
		bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
	} catch (WriterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
      File outputFile = new File("D://new.png");   
      try {
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    
      
  }
  
  public static void main(String[] args){
    System.out.println(enPassword("sclead3"));    
    System.out.println(dePassword("nyoFknsDBI"));
    System.out.println(encrypt("sclead3"));
    System.out.println(decrypt("0a0a1f"));
    long startt = Long.valueOf("1482681600")*1000;
    long endt = Long.valueOf("1486310400")*1000;
    System.out.println("start="+startt);
    System.out.println("end="+endt);
	  Date startdate = new Date(startt);
	  Date enddate = new Date(endt);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    System.out.println("format=start="+format.format(startdate));
    System.out.println("format=end="+format.format(enddate));

    
    
  }
}
