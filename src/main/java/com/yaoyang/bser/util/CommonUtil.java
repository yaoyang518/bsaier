package com.yaoyang.bser.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class CommonUtil {

	public static final String JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final static int ENCODE_XORMASK = 0x5A;

	private final static char ENCODE_DELIMETER = '\002';

	private final static char ENCODE_CHAR_OFFSET1 = 'A';

	private final static char ENCODE_CHAR_OFFSET2 = 'h';

	public static String getIpAddr(final HttpServletRequest httpRequest) {
		String ip = httpRequest.getRemoteAddr();
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "127.0.0.1".equalsIgnoreCase(ip))
			ip = httpRequest.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "127.0.0.1".equalsIgnoreCase(ip))
			ip = httpRequest.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "127.0.0.1".equalsIgnoreCase(ip))
			ip = httpRequest.getHeader("WL-Proxy-Client-IP");

		return ip;
	}

	public static String getUtf8Str(final String s) {
		String ret = null;
		try {
			ret = java.net.URLEncoder.encode(s, "utf-8");
		} catch (final UnsupportedEncodingException ex) {
		}
		return ret;
	}

	public static String getStrUtf8(final String s) {
		String ret = null;
		try {
			ret = java.net.URLDecoder.decode(s, "utf-8");
		} catch (final UnsupportedEncodingException ex) {
		}
		return ret;
	}

	public static boolean isRightPhone(final String number) {
		if (number == null) {
			return false;
		}
		return number.matches("^(13|15|18|17)\\d{9}$");
	}

	public static boolean isRightEmail(final String email) {
		return email
				.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	public static float formatNumberToFloat(final Number value) {
		final DecimalFormat df = new DecimalFormat("########");
		return Float.parseFloat(df.format(value));
	}

	public static float formatNumber(final Number value) {
		final DecimalFormat df = new DecimalFormat("########");
		return Float.parseFloat(df.format(value));
	}

	public static String formatNumberToString(final Number value) {
		final DecimalFormat df = new DecimalFormat("####");
		return df.format(value);
	}

	public static int parseInt(final float value) {
		return (int) Math.rint(value - 0.01);
	}

	public static String formatDate(final Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				JSON_DATE_FORMAT);
		return dateFormat.format(date);
	}

	public static Date toDate(final String date) {
		if (date == null || date.trim().isEmpty()) {
			return null;
		}

		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				JSON_DATE_FORMAT);
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date toDateFromJSON(final String date) {
		if (date == null || date.trim().isEmpty()) {
			return null;
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss");
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String abbreviate(final String value, final int count) {
		return StringUtils.abbreviate(value, count);
	}

	/**
	 * Builds a cookie string containing a username and password.
	 * <p>
	 * 
	 * Note: with open source this is not really secure, but it prevents users
	 * from snooping the cookie file of others and by changing the XOR mask and
	 * character offsets, you can easily tweak results.
	 * 
	 * @param username
	 *            The username.
	 * @param password
	 *            The password.
	 * @return String encoding the input parameters, an empty string if one of
	 *         the arguments equals <code>null</code>.
	 */
	public static String encodePasswordCookie(final String username,
			final String password) {
		final StringBuffer buf = new StringBuffer();
		if (username != null && password != null) {
			final byte[] bytes = (username + ENCODE_DELIMETER + password)
					.getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}
		return buf.toString();
	}

	public static String[] decodePasswordCookie(String cookieVal) {

		// check that the cookie value isn't null or zero-length
		if (cookieVal == null || cookieVal.length() <= 0) {
			return null;
		}

		// unrafel the cookie value
		final char[] chars = cookieVal.toCharArray();
		final byte[] bytes = new byte[chars.length / 2];
		int b;
		for (int n = 0, m = 0; n < bytes.length; n++) {
			b = chars[m++] - ENCODE_CHAR_OFFSET1;
			b |= (chars[m++] - ENCODE_CHAR_OFFSET2) << 4;
			bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
		}
		cookieVal = new String(bytes);
		final int pos = cookieVal.indexOf(ENCODE_DELIMETER);
		final String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
		final String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

		return new String[] { username, password };
	}

	public static boolean isEmpty(final Collection collection) {
		return collection == null ? true : collection.isEmpty();
	}

	public static boolean isEmpty(final String value) {
		return value == null ? true : value.trim().length() == 0;
	}

	public static List removeDuplicate(final List list) {
		return new ArrayList(new HashSet(list));
	}

	public static String retrieveUser(final String email) {
		final String[] parts = email.split("@");
		String user = null;
		if (parts != null && parts.length > 0) {
			user = parts[0];
		}
		if (user == null || user.trim().length() == 0) {
			user = email;
		}
		return user;
	}

	public static <T> T getFirstRecord(final List<T> values) {
		if (values == null || values.isEmpty()) {
			return null;
		} else {
			return values.get(0);
		}
	}

	/**
	 * Returns consecutive {@linkplain List#subList(int, int) sublists} of a
	 * list, each of the same size (the final list may be smaller). For example,
	 * partitioning a list containing {@code [a, b, c, d, e]} with a partition
	 * size of 3 yields {@code [[a, b, c], [d, e]]} -- an outer list containing
	 * two inner lists of three and two elements, all in the original order.
	 * <p/>
	 * Adapted from http://code.google.com/p/google-collections/
	 * 
	 * @param list
	 *            the list to return consecutive sublists of
	 * @param size
	 *            the desired size of each sublist (the last may be smaller)
	 * @return a list of consecutive sublists
	 * @throws IllegalArgumentException
	 *             if {@code size} is non-positive
	 */
	public static <T> List<List<T>> partition(final List<T> list, final int size) {

		if (list == null)
			throw new NullPointerException("'list' must not be null");
		if (!(size > 0))
			throw new IllegalArgumentException("'size' must be greater than 0");

		return new Partition<T>(list, size);
	}

	private static class Partition<T> extends AbstractList<List<T>> {

		final List<T> list;
		final int size;

		Partition(final List<T> list, final int size) {
			this.list = list;
			this.size = size;
		}

		@Override
		public List<T> get(final int index) {
			final int listSize = size();
			if (listSize < 0)
				throw new IllegalArgumentException("negative size: " + listSize);
			if (index < 0)
				throw new IndexOutOfBoundsException("index " + index
						+ " must not be negative");
			if (index >= listSize)
				throw new IndexOutOfBoundsException("index " + index
						+ " must be less than size " + listSize);
			final int start = index * this.size;
			final int end = Math.min(start + this.size, this.list.size());
			return this.list.subList(start, end);
		}

		@Override
		public int size() {
			return (this.list.size() + this.size - 1) / this.size;
		}

		@Override
		public boolean isEmpty() {
			return this.list.isEmpty();
		}
	}

	public static String encodeToUTF8(final String valueStr) {
		try {
			return new String(valueStr.getBytes(), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String utf8Togb2312(final String str) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							str.substring(i + 1, i + 3), 16));
				} catch (final NumberFormatException e) {
					throw new IllegalArgumentException();
				}
				i += 2;
				break;
			default:
				sb.append(c);
				break;
			}
		}
		// Undo conversion to external encoding
		final String result = sb.toString();
		String res = null;
		try {
			final byte[] inputBytes = result.getBytes("8859_1");
			res = new String(inputBytes, "UTF-8");
		} catch (final Exception e) {
		}
		return res;
	}

	public static boolean hasFullSize(final String inStr) {
		if (inStr.getBytes().length != inStr.length()) {
			return true;
		}
		return false;
	}

	public static void main(final String[] args) {
//		System.out.println(isRightEmail("jimlaren@gmail.com"));
//		System.out.println(isRightPhone("18858285562"));
		String string = null + ","+null;
		String test = string.substring(0, string.indexOf(","));
		System.out.println(test == null);
		System.out.println(test);
		System.out.println(test.isEmpty());
	}

	public static boolean containChinese(final String pValue) {
		for (final char value : pValue.toCharArray()) {
			if (value > 256) {
				return true;
			}
		}
		return false;

	}

	public static int convertToIntOrElse(final String searchValue, final int i) {
		if (searchValue == null || searchValue.trim().isEmpty()) {
			return i;
		}
		int returnValue = i;
		try {
			returnValue = Integer.parseInt(searchValue);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static String processJsonObjectValueToString(
			final JSONObject jsonObject, final String key) {
		if (jsonObject != null && key != null) {
			if (jsonObject.get(key) != null) {
				return jsonObject.get(key).toString();
			}
		}
		return "";
	}

	public static void deploy() {
		InputStream in = null;
		try {
			final Process pro = Runtime.getRuntime().exec(
					new String[] { "sh", "/opt/bin/deploy" });
			pro.waitFor();
			in = pro.getInputStream();
			final BufferedReader read = new BufferedReader(
					new InputStreamReader(in));
			final String result = read.readLine();
			System.out.println("INFO:" + result);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] convertFromInputStream(final InputStream input) {
		byte[] bytes = null;
		try {
			bytes = new byte[input.available()];
			input.read(bytes, 0, bytes.length);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static String getDiscountValue(final float discount) {
		if (discount > 1) {
			return "异常折扣" + discount;
		}

		final String discountStr = "" + discount;

		if (discount == 0) {
			return "无折扣";
		} else {
			final int length = discountStr.length();
			final StringBuilder sb = new StringBuilder();
			if (length == 3) {
				sb.append(discountStr.substring(2, 3));
			}
			if (length > 3) {
				sb.append(discountStr.substring(2, 3));
				sb.append(".");
				sb.append(discountStr.substring(3));
			}
			return sb.append("折").toString();
		}
	}

	public static String getAddressFromIp(final String ip) {
		try {
			final HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://ip.taobao.com/service/getIpInfo2.php?ip=" + ip)
					.header("accept", "application/json").asJson();
			final org.json.JSONObject jsonObject = jsonResponse.getBody()
					.getObject();
			// System.out.println(jsonObject);
			// Unirest.shutdown();

			final org.json.JSONObject addr = jsonObject.getJSONObject("data");
			final String isp = addr.getString("isp");
			final String countryAndCity = addr.getString("country")
					+ addr.getString("city");
			return StringUtils.isEmpty(isp) ? countryAndCity : isp + "-"
					+ countryAndCity;
		} catch (final UnirestException e) {
			e.printStackTrace();
		} catch (final JSONException e) {

			e.printStackTrace();
		}
		return "未知";
	}

	public static <T> Set<T> reverse(final Set<T> values) {
		final List<T> list = new ArrayList<T>(values);
		Collections.reverse(list);
		return new HashSet<T>(list);
	}

	public static boolean isRightURL(final URL url) {

		try {
			final InputStream in = url.openStream();
			return true;
		} catch (final Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	public static boolean isRightURL(final String url) {

		try {
			final InputStream in = new URL(url).openStream();
			return true;
		} catch (final Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * 按照概率集合参数随机对象
	 */
	public static String probability(final Map<String, Float> map) {
		Float total = 0f;
		final Map<Float, String> tempMap = new LinkedHashMap<Float, String>(); // 使用有序的map集合以保证key值是递增的
		final Iterator<Entry<String, Float>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, Float> entry = it.next();
			total += entry.getValue();
			tempMap.put(total, entry.getKey());
		}
		final float index = new Random().nextFloat() * total;
		final Iterator<Entry<Float, String>> tempIt = tempMap.entrySet()
				.iterator();
		while (tempIt.hasNext()) {
			final Entry<Float, String> next = tempIt.next();
			if (index < next.getKey()) {
				return next.getValue();
			}
		}
		return null;
	}

	public static String getSimpleName(Object entity) {
		Class classWithoutInitializingProxy = HibernateProxyHelper
				.getClassWithoutInitializingProxy(entity);
		if (classWithoutInitializingProxy == null) {
			classWithoutInitializingProxy = entity.getClass();
		}
		return classWithoutInitializingProxy.getSimpleName().toLowerCase();

	}

	public static String getDiscountName(final float discount) {
		if (discount > 1) {
			return "异常折扣" + discount;
		}

		final String discountStr = "" + discount;

		if (discount == 0) {
			return "无折扣";
		} else {
			final int length = discountStr.length();
			final StringBuilder sb = new StringBuilder();
			if (length == 3) {
				sb.append(discountStr.substring(2, 3)).append("0");
			}
			if (length > 3) {
				sb.append(discountStr.substring(2, 4));
			}
			return sb.append("折").toString();
		}
	}

	public static String convertFileSize(final long size) {
		final long kb = 1024;
		final long mb = kb * 1024;
		final long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			final float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			final float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static boolean isNumber(String value){
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
