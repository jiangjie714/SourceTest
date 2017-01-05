
package com.fufang.bi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 数据转化的工具类
 *
 * @author suwen
 *
 */
public class Converter {

	public static String formatTime(double timeInSeconds) {

		long seconds = (long) Math.floor(timeInSeconds);
		long minutes = (long) Math.floor(timeInSeconds / 60.0);
		long hours = (long) Math.floor(minutes / 60.0);

		if (hours != 0) {
			return hours + "小时 " + (minutes % 60) + "分钟 " + (seconds % 60)
					+ "秒";
		} else if (minutes % 60 != 0) {
			return (minutes % 60) + "分钟 " + (seconds % 60) + "秒";
		} else {
			return (seconds % 60) + " 秒";
		}
	}


	/**
	 *
	 * 利用反射机制复制一个对象
	 * 
	 */
	public static Object copy(Object object) {

		try {
			Class<?> classType = object.getClass();
			Object objectCopy = classType.getConstructor(new Class[] {})
					.newInstance(new Object[] {});
			Field fields[] = classType.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {

				Field field = fields[i];
				if (!field.getName().equals("serialVersionUID")) {
					String fieldName = field.getName();
					String firstLetter = fieldName.substring(0, 1)
							.toUpperCase();
					String getMethodName = "get" + firstLetter
							+ fieldName.substring(1);
					String setMethodName = "set" + firstLetter
							+ fieldName.substring(1);
					Method getMethod = classType.getMethod(getMethodName,
							new Class[] {});
					Method setMethod = classType.getMethod(setMethodName,
							new Class[] { field.getType() });
					Object value = getMethod.invoke(object, new Object[] {});
					setMethod.invoke(objectCopy, new Object[] { value });
				}

			}
			return objectCopy;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	/***
	 * 转化为Long型
	 *
	 * @param value
	 * @return
	 */
	public static Long toLong(String value) {

		Long d;
		if (value == null || value.trim().length() == 0) {
			return 0l;
		}
		try {
			d = Long.valueOf(value);
			return d;
		} catch (Exception e) {
		}
		return 0l;
	}

	/***
	 * 转化为Double型
	 *
	 * @param value
	 * @return
	 */
	public static Double toDouble(String value) {

		Double d;
		if (value == null || value.trim().length() == 0) {
			return 0d;
		}
		try {
			d = Double.valueOf(value);
			return d;
		} catch (Exception e) {
		}
		return 0d;
	}

	/***
	 * 转化为Integer型
	 *
	 * @param value
	 * @return
	 */
	public static Integer toInteger(String value) {

		Integer d;
		if (value == null || value.trim().length() == 0) {
			return 0;
		}
		try {
			d = Integer.valueOf(value);
			return d;
		} catch (Exception e) {
		}
		return 0;
	}

	/***
	 * 转化为Integer型，如果为空则返回-1
	 *
	 * @param value
	 * @return
	 */
	public static Integer toIntegerF(String value) {

		Integer d;
		if (value == null || value.trim().length() == 0) {
			return -1;
		}
		try {
			d = Integer.valueOf(value);
			return d;
		} catch (Exception e) {
		}
		return -1;
	}

	/***
	 * 解决显示null的问题，对数据进行toString操作
	 *
	 * @param obj
	 * @return
	 */
	public static String toBlank(Object obj) {

		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	public static String toStorageCond(String value) {

		String reValue = "";
		if (value != null && value.trim().length() > 0) {
			if (value.equals("0")) {
				reValue = "常温";
			} else if (value.equals("1")) {
				reValue = "阴凉";
			} else if (value.equals("2")) {
				reValue = "冷藏";
			}
		}
		return reValue;
	}

	/**
	 * 0：否 1：是
	 *
	 * @param value
	 * @return
	 */
	public static String toIsOrNo(String value) {

		String reValue = "";
		if (value != null && value.trim().length() > 0) {
			if (value.equals("0")) {
				reValue = "否";
			} else {
				reValue = "是";
			}
		}
		return reValue;
	}

	/**
	 * 0：否 1：是
	 *
	 * @param value
	 * @return
	 */
	public static String toCompOrNo(String value) {

		String reValue = "";
		if (value != null && value.trim().length() > 0) {
			if (value.equals("0")) {
				reValue = "未完成";
			} else {
				reValue = "已完成";
			}
		}
		return reValue;
	}

	/**
	 * 0：否 1：是
	 *
	 * @param value
	 * @return
	 */
	public static String toVertify(String value) {

		String reValue = "";
		if (value != null && value.trim().length() > 0) {
			if (value.equals("0")) {
				reValue = "未审核";
			} else if (value.equals("1")) {
				reValue = "审核通过";
			} else if (value.equals("2")) {
				reValue = "初始化";
			} else if (value.equals("3")) {
				reValue = "正常模式";
			} else if (value.equals("5")) {
				reValue = "审核未通过";
			}
		}
		return reValue;
	}

	/***
	 * 生成六位码，不足六位的补0，如6，得到000006
	 *
	 * @param num
	 * @return
	 */
	public static String serialNumber(Integer num) {

		return String.format("%06d", num);
	}

	/***
	 * 生成五位码，不足五位的补0，如6，得到000006
	 *
	 * @param num
	 * @return
	 */
	public static String serialNumber5(Integer num) {

		return String.format("%05d", num);
	}

	/***
	 * 生成两位码，不足两位的补0，如2，得到02
	 *
	 * @param num
	 * @return
	 */
	public static String serialNumber2(Integer num) {

		return String.format("%02d", num);
	}

	/***
	 * 生成四位码，不足四位的补0，如4，得到0004
	 *
	 * @param num
	 * @return
	 */
	public static String serialNumber3(Integer num) {

		return String.format("%04d", num);
	}

	/***
	 * 日期转化方法
	 *
	 * @param dateString
	 * @return
	 */
	public static Date toDate(String dateString) {

		dateString = Converter.toBlank(dateString);
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
		}
		return null;
	}

	private static final String[] parsePatterns = new String[] {
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd", "yyyyMMdd", "yyyy年MM月dd日"
			// 这里可以增加更多的日期格式，用得多的放在前面
	};

	/***
	 * 日期转化方法
	 *
	 * @param dateString
	 * @return
	 */
	public static Date toDate2(String dateString) {

		try {
			return DateUtils.parseDateStrictly(dateString, parsePatterns);
		} catch (ParseException e) {
			return null;
		}

	}

	/***
	 * 日期转化方法
	 *
	 * @param dateString
	 * @return
	 */
	public static Date toDate3(String dateString) {

		try {
			return DateUtils.parseDateStrictly(dateString, parsePatterns[0]);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 将日期转化为yyyy-mm-dd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString(Date date) {

		if (date == null) {
			return "";
		} else {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").format(date);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 将日期转化为yyyymmdd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString2(Date date) {

		if (date == null) {
			return "";
		} else {
			try {
				return new SimpleDateFormat("yyyyMMdd").format(date);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 将日期转化为yyyymmdd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString3(Date date) {

		if (date == null) {
			return "";
		} else {
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 将日期转化为年月日时分格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {

		if (date == null) {
			return "";
		} else {
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 将日期转化为yyyymmdd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate5(String date) {

		if (date == null) {
			return null;
		} else {
			try {
				// 将字符串转换为日期和时间
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				// 生成的日期和时间
				return dateformat.parse(date);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 将Object类型转化为yyyy-mm-dd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString3(Object date) {

		if (date == null) {
			return "";
		} else {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").format(date);
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 转换HQL或SQL语句，避免出现WHERE 1=1现象
	 *
	 * @param sql
	 * @return
	 */
	public static String toConvertSQL(String hql) {

		if (hql.indexOf("and") == -1) {
			return hql.replaceFirst("where", " ");

		} else {
			return hql.replaceFirst("and", " ");
		}

	}

	public static String getValue(HSSFCell cell) {

		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					value = Converter.toDateString(HSSFDateUtil
							.getJavaDate(cell.getNumericCellValue()));
				} else {// 纯数字
					value = String.valueOf(cell.getNumericCellValue());
					for (int i = 0; i < value.length(); i++) {
						if (value.charAt(i) == 'E' || value.charAt(i) == 'e') {
							BigDecimal dd = new BigDecimal(value);
							value = dd.longValue() + "";
						}
						if (value.substring(value.length() - 2).equals(".0")) {
							value = value.substring(0, value.length() - 2);
						}
					}
				}
				break;
			/* 此行表示单元格的内容为string类型 */
			case HSSFCell.CELL_TYPE_STRING: // 字符串型
				value = cell.getStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:// 公式型
				// 读公式计算值
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
					value = cell.getStringCellValue().toString();
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
				value = " " + cell.getBooleanCellValue();
				break;
			/* 此行表示该单元格值为空 */
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "";
				break;
			default:
			}
		}

		return value;
	}

	/***
	 * 对日期增加 天、月、年
	 *
	 * @param dateString
	 *            日期字符串 yyyy-MM-dd
	 * @param n
	 *            数量
	 * @param type
	 *            类型 0：月 1：天 2：年
	 * @return
	 */
	public static String addDay(String dateString, int n, int type) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(dateString));
			switch (type) {

			case 0:
				cd.add(Calendar.MONTH, n);// 增加一月
				break;

			case 1:
				cd.add(Calendar.DATE, n);// 增加一个天
				break;
			case 2:
				cd.add(Calendar.YEAR, n);// 增加一个年
				break;
			}

			return sdf.format(cd.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到几天前的时间
	 */

	public static Date getDateBefore(Date d, int day) {

		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}
	
	public static int daysOfTwo2(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 得到几天后的时间
	 */

	public static Date getDateAfter(Date d, int day) {

		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static void setCellText(HSSFRow row, HSSFCell cell, int index,
			String text, HSSFCellStyle cellStyle) {

		try {
			cell = row.createCell(index, HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/***
	 * 复制文件
	 *
	 * @param sFile
	 *            源文件
	 * @param oFile
	 *            目标文件
	 */
	public static void fileCopy(String sFile, String oFile) {

		File file = new File(sFile);
		if (!file.exists()) {
			return;
		}
		File fileb = new File(oFile);
		if (file.isFile()) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(fileb);
				byte[] bb = new byte[(int) file.length()];
				fis.read(bb);
				fos.write(bb);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (file.isDirectory()) {
			if (!fileb.exists()) {
				fileb.mkdir();
			}
			String[] fileList;
			fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				fileCopy(sFile + "/" + fileList[i], oFile + "/" + fileList[i]);
			}
		}
	}

	public static void setRowColor(HSSFWorkbook workbook, HSSFRow row,
			Short color, String comments) {

		try {

			HSSFCell cellforColor = row.getCell(0);

			HSSFCellStyle cellStyle = workbook.createCellStyle();

			cellStyle.cloneStyleFrom(cellforColor.getCellStyle());
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFillBackgroundColor(color);// 设置背景色
			cellStyle.setFillForegroundColor(color);// 设置前景色
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
			cellStyle.setLocked(false);

			cellforColor.setCellStyle(cellStyle);

			HSSFPatriarch patriarch = workbook.getSheetAt(0)
					.createDrawingPatriarch();
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
					0, 0, 0, 0, (short) 3, 3, (short) 10, 12));
			comment.setString(new HSSFRichTextString(comments));
			comment.setAuthor("HltSystem");
			cellforColor.removeCellComment();
			cellforColor.setCellComment(comment);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean isArrayContains(Integer[] integerArray, Integer number) {

		int result = Arrays.binarySearch(integerArray, number);
		if (result >= 0) {
			return true;
		} else {
			return false;
		}

	}

	public static <T> T[] removeArrayItem(T[] arrs, int index) {

		int len = arrs.length;
		if (index < 0 || index >= len) {
			throw new IllegalArgumentException("索引出界");
		}
		List<T> list = new LinkedList<T>();
		for (int i = 0; i < len; i++) {
			if (i != index) {
				list.add(arrs[i]);
			}
		} // 这里将改变传入的数组参数 arrs 的值
		arrs = list.toArray(arrs);
		return java.util.Arrays.copyOf(arrs, arrs.length - 1);
	}

	/**
	 * 专业
	 *
	 * @param profession
	 * @return
	 */
	public static String toXueLiName(Integer xueli) {

		if (xueli == null || xueli == 0) {
			return "";
		} else if (xueli == 1) {
			return "大学本科及以上";
		} else if (xueli == 2) {
			return "大学专科";
		} else if (xueli == 3) {
			return "中专";
		} else if (xueli == 4) {
			return "高中";
		} else {
			return "其他";
		}

	}

	// Arrays工具提供了工具方法，之所以又写一个是为了去除空格
	public static List<String> arrayTolist(String string) {

		String[] array = string.split(",");
		List<String> list = new ArrayList<String>();
		if (array != null && array.length > 0) {

			for (String s : array) {
				list.add(s.trim());
			}
		}
		return list;
	}

	public static List<Integer> arrayToList(String string) {

		String[] array = string.split(",");
		List<Integer> list = new ArrayList<Integer>();
		if (array != null && array.length > 0) {

			for (String s : array) {
				list.add(Integer.parseInt(s.trim()));
			}
		}
		return list;
	}

	public static boolean isContainsEach(String warnRoles,
			List<String> userRoles) {

		boolean falg = false;
		if (warnRoles != null && warnRoles.trim().length() > 0) {
			String[] array = warnRoles.split(",");
			List<String> list = new ArrayList<String>();
			if (array != null && array.length > 0) {
				for (String s : array) {
					if (userRoles.contains(s.trim())) {
						falg = true;
						break;
					}
				}
			}
		} else {
			falg = false;
		}
		return falg;
	}

	public static String toProfessionName(Integer profession) {

		if (profession == null || profession == 0) {
			return "";
		} else if (profession == 1) {
			return "药学";
		} else if (profession == 2) {
			return "医学";
		} else if (profession == 3) {
			return "生物";
		} else if (profession == 4) {
			return "化学";
		} else if (profession == 6) {
			return "中药学";
		} else if (profession == 7) {
			return "预防医学";
		} else if (profession == 8) {
			return "微生物学";
		} else {
			return "其他 ";
		}
	}

	public static String httpRequest(String requestUrl) {

		InputStream inputStream = null;
		String htmlString = null;
		StringBuffer buffer = new StringBuffer();

		try {

			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setRequestProperty("accept", "*/*");
			httpUrlConn.setRequestProperty("connection", "Keep-Alive");
			httpUrlConn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 获得返回的输入流
			System.out.println(httpUrlConn.getPermission());
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			htmlString = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlString;
	}

	/**
	 * 将字符串转换成double
	 * 
	 * @param value
	 * @return
	 */
	public static Double toDouble2(String value) {

		Double d;
		if (value == null || value.trim().length() == 0) {
			return 0d;
		}
		try {
			d = Double.valueOf(value);
			return d;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {

		String x = "0002";
		System.out.println(Integer.valueOf(x));
	}

}
