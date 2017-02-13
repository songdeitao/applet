package cn.xingfire.applet.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
	public static String SEREVER_NAME = null;

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	final public static String DIR_FS = "FS";

	final public static String DIR_RAM = "RAM";

	final public static String DIR_MMAP = "MMAP";

	final public static String PARAM_HEADER = "param2header_";

	public static String formatTime(long timeStamp) {
		return DATE_FORMAT.format(new Date(timeStamp));
	}

	public static long getTime(String dateStr) throws ParseException {
		return DATE_FORMAT.parse(normalizeDate(dateStr)).getTime();
	}

	public static String normalizeDate(String dateStr) {
		int num = 3 - (dateStr.length() - 1 - dateStr.indexOf('.'));
		for (int i = 0; i < num; i++) {
			dateStr += '0';
		}
		return dateStr;
	}

	public static final String toString(Throwable t) {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		PrintWriter p = new PrintWriter(ba);
		t.printStackTrace(p);
		p.flush();
		return ba.toString();
	}

	public static final String toString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/*
	 * 转换为INT
	 */
	public static int toInt(String str) {
		int intRet = 0;
		try {
			if (str == null || str.trim().equals("") || !isInteger(str))
				return 0;
			intRet = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return intRet;
	}
	
	public static int toInt(String str, int defaultValue) {
		int intRet = defaultValue;
		try {
			if (str == null || str.trim().equals("") || !isInteger(str))
				return intRet;
			intRet = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return intRet;
	}

	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString());
	}

	public static List<Integer> toIntArray(String str) {
		List<Integer> list = new ArrayList<Integer>();

		if (str == null || str.trim().isEmpty()) {
			return null;
		}

		List<String> array = toStringArray(str);
		for (String item : array) {
			int value = CommonUtil.toInt(item);
			if (value > 0)
				list.add(value);
		}

		return list;
	}

	/**
	 * 当传递一个逗号，集合中会有一个为空的1个元素，使用时候注意
	 * 
	 * @param lineStr
	 * @return
	 */
	public static List<String> toStringArray(String lineStr) {
		List<String> entrys = new ArrayList<String>();
		if (lineStr == null || (lineStr = lineStr.trim()).isEmpty()) {
			return entrys;
		}
		int index = 0;
		int start = 0;
		while (index < lineStr.length()) {
			char ch = lineStr.charAt(index);
			if (ch == ',') {
				entrys.add(lineStr.substring(start, index));
				start = ++index;
			} else if (ch == '(') {
				int count = 1;
				++index;
				do {
					if (index >= lineStr.length()) {
						throw new IllegalArgumentException(
								"Unclosed parentheses with string:" + lineStr);
					}
					ch = lineStr.charAt(index);
					if (ch == '\'') {
						while (++index < lineStr.length()) {
							if (lineStr.charAt(index) == '\''
									&& lineStr.charAt(index - 1) != '\\') {
								++index;
								break;
							}
						}
					} else if (ch == ')') {
						++index;
						--count;
					} else if (ch == '(') {
						++index;
						++count;
					} else {
						++index;
					}
				} while (count > 0);
			} else {
				++index;
			}
		}
		if (start < index) {// the last
			entrys.add(lineStr.substring(start, index));
		}
		return entrys;
	}

	public static List<String> toStringArray(String lineStr, char character) {
		List<String> entrys = new ArrayList<String>();
		if (lineStr == null || (lineStr = lineStr.trim()).isEmpty()) {
			return entrys;
		}
		int index = 0;
		int start = 0;
		while (index < lineStr.length()) {
			char ch = lineStr.charAt(index);
			if (ch == character) {
				entrys.add(lineStr.substring(start, index));
				start = ++index;
			} else if (ch == '(') {
				int count = 1;
				++index;
				do {
					if (index >= lineStr.length()) {
						throw new IllegalArgumentException(
								"Unclosed parentheses with string:" + lineStr);
					}
					ch = lineStr.charAt(index);
					if (ch == '\'') {
						while (++index < lineStr.length()) {
							if (lineStr.charAt(index) == '\''
									&& lineStr.charAt(index - 1) != '\\') {
								++index;
								break;
							}
						}
					} else if (ch == ')') {
						++index;
						--count;
					} else if (ch == '(') {
						++index;
						++count;
					} else {
						++index;
					}
				} while (count > 0);
			} else {
				++index;
			}
		}
		if (start < index) {// the last
			entrys.add(lineStr.substring(start, index));
		}
		return entrys;
	}

	/*
	 * TOTO 待验证
	 */
	public static float toFloat(String str) {
		if (StringUtils.isBlank(str)) {
			return 0f;
		}
		float retFolat = 0f;
		try {
			retFolat = Float.parseFloat(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return retFolat;
	}

	public static float toFloat(Object obj) {
		if (obj == null) {
			return 0f;
		}
		float retFolat = 0f;
		try {
			retFolat = toFloat(obj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return retFolat;
	}

	public static String reverseString(String str) {
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = str.length() - 1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	public static boolean booleanValue(String val) {
		if (val == null) {
			return false;
		}
		if (val.equalsIgnoreCase("true")) {
			return true;
		}
		if (val.equalsIgnoreCase("false")) {
			return false;
		}
		if (val.equalsIgnoreCase("t")) {
			return true;
		}
		if (val.equalsIgnoreCase("f")) {
			return false;
		}
		if (val.equalsIgnoreCase("yes")) {
			return true;
		}
		if (val.equalsIgnoreCase("no")) {
			return false;
		}
		if (val.equalsIgnoreCase("y")) {
			return true;
		}
		if (val.equalsIgnoreCase("n")) {
			return false;
		}
		if (val.equals("1")) {
			return true;
		}
		if (val.equals("0")) {
			return false;
		}

		return false;
	}

	public static int[] genFromTo(String val) {
		int from = 1;
		int to = Integer.MAX_VALUE;
		String regex = "^(\\d*)([^0-9]+)(\\d*)$";
		Pattern p = Pattern.compile(regex);
		if (val != null) {
			Matcher m = p.matcher(val);
			if (m.find()) {
				if (m.group(1) != null && !m.group(1).isEmpty()) {
					from = Integer.parseInt(m.group(1));
				}
				if (m.group(3) != null && !m.group(3).isEmpty()) {
					to = Integer.parseInt(m.group(3));
				}
			} else if (!val.isEmpty()) {
				to = Integer.parseInt(val);
			}
		}
		return new int[] { from, to };
	}

	public static String getByParam(String uri, String param, String encoding)
			throws UnsupportedEncodingException {

		String url = null;
		int pos = uri.indexOf("?");
		if (pos == -1) {
			url = uri;
		} else {
			url = uri.substring(pos + 1);
		}

		String[] str = url.split("&");
		for (String s : str) {
			String[] cips = s.split("=");
			if (cips[0].equals(param)) {
				return URLDecoder.decode(cips[1], encoding);
			}
		}

		return null;
	}

	public static boolean addressEqual(String addr1, String addr2) {
		int pos = addr1.indexOf(':');
		if (pos < 0) {
			addr1 += ":80";
		}

		pos = addr2.indexOf(':');
		if (pos < 0) {
			addr2 += ":80";
		}

		return addr1.equalsIgnoreCase(addr2);
	}

	public static int[] sequence(int arrLen) {
		int[] arr = new int[arrLen];
		for (int i = 0; i < arrLen; i++) {
			arr[i] = i;
		}
		Random rand = new Random();
		for (int i = 0; i < arrLen; i++) {
			int r = rand.nextInt(arrLen);
			int tmp = arr[i];
			arr[i] = arr[r];
			arr[r] = tmp;
		}
		return arr;
	}

	public static Map<String, String> wrapMap(String prefix,
			Map<String, String> subMap) {
		return wrapMap(prefix, null, subMap);
	}

	public static Map<String, String> wrapMap(String prefix,
			Map<String, String> map, Map<String, String> subMap) {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		if (subMap != null) {
			for (Map.Entry<String, String> entry : subMap.entrySet()) {
				map.put(prefix + entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

	/**
	 * @param fieldable
	 * @param seperator
	 * @return
	 */
	public static String[] split(String str, char seperator) {
		if (str == null) {
			return null;
		}
		List<String> strs = new ArrayList<String>();
		int start = -1;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == seperator) {
				if (start != -1) {
					strs.add(str.substring(start, i));
					start = -1;
				}
			} else {
				if (start == -1) {
					start = i;
				}
			}
		}
		if (start != -1) {
			strs.add(str.substring(start, str.length()));
		}
		return strs.toArray(new String[strs.size()]);
	}

	public static String normalizeNumber(int len, char digit) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(digit);
		}
		return sb.toString();
	}

	public static String normalizeNumber(int len, String numStr) {
		if (numStr == null) {
			numStr = "";
		}
		if (len < numStr.length()) {
			throw new IllegalArgumentException("length of numStr:" + numStr
					+ " > subLen:" + len);
			// numStr = numStr.substring(numStr.length() - len);
		} else if (len > numStr.length()) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < len - numStr.length(); i++) {
				sb.append('0');
			}
			sb.append(numStr);
			numStr = sb.toString();
		}
		return numStr;
	}

	public static int getDaysDiff(String format, String first, String second)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long from = df.parse(first).getTime();
		long to = df.parse(second).getTime();
		return (int) ((to - from) / (1000 * 60 * 60 * 24));
	}

	public static int getDaysDiff(String first, String second)
			throws ParseException {
		return getDaysDiff("yyyy-MM-dd", first, second);
	}

	public static String getThreadPoolInfo(ThreadPoolExecutor e) {
		return "ActiveCount:" + e.getActiveCount() + ",CompletedTaskCount:"
				+ e.getCompletedTaskCount() + ",TaskCount:" + e.getTaskCount()
				+ ",QueueSize:" + e.getQueue().size();
	}

	public static String inputStream2String(InputStream is) throws IOException {
		OutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	public static boolean hasScore(String str) {
		return str == null ? false : (" " + str.toLowerCase() + " ")
				.matches(".*[^a-zA-Z0-9_]score[^a-zA-Z0-9_].*");
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd-HH:mm:ss.SSS").format(new Date());
	}
}
