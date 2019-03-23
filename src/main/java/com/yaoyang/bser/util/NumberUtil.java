package com.yaoyang.bser.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字码工具类
 *
 * @author yaoyang
 * @date 2018-01-24
 */

public class NumberUtil {

    private static byte[] lock = new byte[0];
    private final static long w = 100000000;

    public static long parseLong(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            long returnValue = 0;
            try {
                returnValue = Long.parseLong(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnValue;
        }
    }

    public static float parseFloat(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            float returnvalue = 0;
            try {
                returnvalue = Float.parseFloat(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnvalue;
        }
    }

    public static int parseInt(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            int returnvalue = 0;
            try {
                returnvalue = Integer.parseInt(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnvalue;
        }
    }

    public static boolean isLong(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            try {
                Long.parseLong(value);
            } catch (final Exception e) {
                return false;
            }
            return true;
        }
    }

    public static String generateFourNumber(final int i) {
        final int id = i + 1;
        final StringBuilder buffer = new StringBuilder();
        if (id < 10) {
            buffer.append("000").append(id);
        } else if (id < 100) {
            buffer.append("00").append(id);
        } else if (id < 1000) {
            buffer.append("0").append(id);
        } else if (id < 10000) {
            buffer.append(id);
        }
        return buffer.toString();
    }

//	public static void main(final String[] args) {
//
//		System.out.println(genRandomNum(8));
//	}

    public static String generateNumber(final String start, final int count, final long id) {
        if (id > Math.pow(10, count - 1)) {
            return start + id;
        }
        final StringBuilder buffer = new StringBuilder(start);

        for (int i = 1; i < count + 1; i++) {
            if (id < Math.pow(10, i)) {
                buffer.append(StringUtils.repeat("0", count - i - 1)).append(id);
                break;
            }
        }

        return buffer.toString();
    }

    public static String generateNumber(final int count, final long id) {
        if (id >= Math.pow(10, count)) {
            return "" + id;
        }
        final StringBuilder buffer = new StringBuilder();

        for (int i = 1; i < count + 1; i++) {
            if (id < Math.pow(10, i)) {
                buffer.append(StringUtils.repeat("0", count - i)).append(id);
                break;
            }
        }

        return buffer.toString();
    }

    public static boolean isInt(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            try {
                Integer.parseInt(value);
            } catch (final Exception e) {
                return false;
            }
            return true;
        }
    }

    public static String generateDouble(double value) {
        return generateDouble(value, 0);
    }

    public static String generateDouble(double value, int digit) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("#0");
        switch (digit) {
            case 1:
                pattern.append(".0");
                break;

            case 2:
                pattern.append(".00");
                break;

            case 3:
                pattern.append(".000");
                break;

            default:
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());// 格式化设置
        return decimalFormat.format(value);
    }

    public static String generateFloat(float value) {
        return generateFloat(value, 0);
    }

    public static String generateFloat(float value, int digit) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("#0");
        switch (digit) {
            case 1:
                pattern.append(".0");
                break;

            case 2:
                pattern.append(".00");
                break;

            case 3:
                pattern.append(".000");
                break;

            default:
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());// 格式化设置
        return decimalFormat.format(value);
    }

    public static String genRandomStr(int n) {
        int maxNum = 62;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < n) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static String genRandomNum(int n) {
        int i;
        int count = 0;
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < n) {
            i = Math.abs(r.nextInt(10));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
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

    private static final int NUMBER_MAX_LENGTH = String.valueOf(Long.MAX_VALUE)
            .length();

    public static boolean isNumber(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        if (string.length() >= NUMBER_MAX_LENGTH) {
            try {
                Long.parseLong(string);
            } catch (final Exception e) {
                return false;
            }
        } else {
            int i = 0;
            if (string.charAt(0) == '-') {
                if (string.length() > 1) {
                    i++;
                } else {
                    return false;
                }
            }
            for (; i < string.length(); i++) {
                if (!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPositiveInteger(final String orginal) {// 判断是否为正整数
        return StringUtil.isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isPositiveInDecimal(final String orginal) {// 判断是否为数值
        return StringUtil.isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*|\\+{0,1}[0-9]\\d*", orginal);
    }

    public static boolean isPlus(final String orginal) {// 判断是否为数值
        final String reg = "(-)(.*)";
        System.out.println("A:" + reg);
        final Pattern pattern = Pattern.compile(reg);
        final Matcher matcher = pattern.matcher(orginal);
        if (matcher.find()) {
            System.out.println("a2:" + matcher.group(2));
            final String str0 = matcher.group(1);
            System.out.println("a:" + str0);
            return true;
        } else {
            return false;
        }

    }

    public static boolean isMinus(final String orginal) {// 判断是否为数值
        final String reg = "(/+)(.*)";
        System.out.println("B:" + reg);
        final Pattern pattern = Pattern.compile(reg);
        final Matcher matcher = pattern.matcher(orginal);
        if (matcher.find()) {
            System.out.println("b1:" + matcher.group(2));
            final String str0 = matcher.group(1);
            System.out.println("b2:" + str0);
            return true;
        } else {
            return false;
        }
    }

    //是否是整数倍
    public static boolean isRemainder(BigDecimal divisor, BigDecimal dividend) {
        BigDecimal[] results = divisor.divideAndRemainder(dividend);
        if (results[1].compareTo(BigDecimal.ZERO) != 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String generateNumber(String type) {
        long r = 0;
        synchronized (lock) {
            r = (long) ((Math.random() + 1) * w);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return type + dateFormat.format(new Date()) + System.currentTimeMillis() + String.valueOf(r).substring(1);
    }

}
