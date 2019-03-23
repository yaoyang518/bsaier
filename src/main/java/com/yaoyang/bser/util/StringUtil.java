package com.yaoyang.bser.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    public static boolean isMatch(final String regex, final String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        final Pattern pattern = Pattern.compile(regex);
        final Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static Boolean isTrueOrFalse(final String param) {
        if (param.equals("true") || param.equals("false")) {
            return true;
        } else {
            return false;
        }
    }

    // public static void main(final String[] args) {
    // System.out.println(isPositiveInDecimal(""));
    // }
    //
    public static String subStringImageExtFromUrlString(String urlString) {
        if (isNullOrEmpty(urlString)) {
            return "";
        }
        final int index = urlString.indexOf("?");
        if (index > 0) {
            urlString = urlString.substring(0, index);
        }
        final String ext = FilenameUtils.getExtension(urlString);
        final String formatExt = isNullOrEmpty(ext) ? "" : ("." + ext);
        return formatExt;
    }

    public static void writeErroeMessage(final String message, final Exception e) {
        PrintStream stream = null;
        try {
            final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            final String logPath = "/wallet/lknDownImagelog/" + sf.format(new Date()) + ".log";
            final File file = new File(logPath);
            if (!file.exists()) {
                FileUtils.getFile(logPath);
            }
            // 创建文件的输出流
            stream = new PrintStream(new FileOutputStream(file, true));
            stream.append(new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()) + "\r\n");
            stream.append(message + "\r\n");
            if (e != null) {
                e.printStackTrace(stream);
                stream.append("\r\n");
            }
        } catch (final FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (final Exception e2) {

        } finally {
            stream.flush();
            stream.close();
        }

    }


    public static boolean isEmail(String email) {
        // 正则表达式
        /*
         * String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
		 * return email.matches(regex);
		 */

        /** 不适用正则*/
        if (email == null || "".equals(email)) {
            return false;
        }

        if (!containsOneWord('@', email) || !email.contains(".")) {
            return false;
        }
        if (email.indexOf("@") + 1 == email.length()) {
            return false;
        }
        if (email.indexOf(".") + 1 == email.length()) {
            return false;
        }
        /*
         * String prefix = email.substring(0, email.indexOf("@")); String middle
		 * = email.substring(email.indexOf("@") + 1, email.indexOf(".")); String
		 * subfix = email.substring(email.indexOf(".") + 1);
		 * System.out.println("prefix=" + prefix + "  middle=" + middle +
		 * "  subfix=" + subfix);
		 * 
		 * if (prefix == null || prefix.length() > 40 || prefix.length() == 0) {
		 * return false; }
		 * 
		 * 
		 * if (!isAllWords(prefix)) { return false; }
		 * 
		 * 
		 * if (middle == null || middle.length() > 40 || middle.length() == 0) {
		 * return false; } if (!isAllWordsAndNo(middle)) { return false; }
		 * 
		 * if (subfix == null || subfix.length() > 3 || subfix.length() < 2) {
		 * return false; }
		 * 
		 * if (!isAllWords(subfix)) { return false; }
		 */

        return true;
    }

    public static boolean checkParams(String pageIndex, String pageSize, String orderByField, String pageNoLimit) {

        try {
            if (!StringUtil.isTrueOrFalse(pageNoLimit)) {
                return false;
            }
            if (orderByField != null) {
                String subString = orderByField.substring(0, 1);
                if (subString.equals("-") || subString.equals("+")) {
                } else {
                    return false;
                }
            }
            if (!Boolean.parseBoolean(pageNoLimit)) {
                if (!NumberUtil.isPositiveInteger(pageIndex) || !NumberUtil.isPositiveInteger(pageSize)) {
                    if (!("0").equals(pageIndex)) {
                        return false;
                    }
                } else {
                    if (orderByField != null) {
                        if (orderByField.substring(0, 1).equals("-") || orderByField.substring(0, 1).equals("+")) {
                        } else {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 判断字符串只包含指定的一个字符c
    private static boolean containsOneWord(char c, String word) {
        char[] array = word.toCharArray();
        int count = 0;
        for (Character ch : array) {
            if (c == ch) {
                count++;
            }
        }
        return count == 1;
    }

    // 检查一个字符串是否全部是字母
    private static boolean isAllWords(String prefix) {
        char[] array = prefix.toCharArray();
        for (Character ch : array) {
            if (ch < 'A' || ch > 'z' || (ch < 'a' && ch > 'Z')) {
                return false;
            }
        }
        return true;
    }

    // 检查一个字符串是否包含字母和数字
    private static boolean isAllWordsAndNo(String middle) {
        char[] array = middle.toCharArray();
        for (Character ch : array) {
            if (ch < '0' || ch > 'z') {
                return false;
            } else if (ch > '9' && ch < 'A') {
                return false;
            } else if (ch > 'Z' && ch < 'a') {
                return false;
            }
        }
        return true;
    }

    public static boolean isLegalParam(String param) {// 只能包含 数字 字母 "." "_"
        // 且长度为5到20字符
        String regex = "^[A-Za-z0-9._]{5,20}+$";// ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$

        if (isNullOrEmpty(param)) {
            return false;
        }
        return param.matches(regex);
    }

    public static boolean isMatcherPassword(String param) {// 只能包含 数字 字母 "." "_"
        // 且长度为5到20字符
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

        if (isNullOrEmpty(param)) {
            return false;
        }
        return param.matches(regex);
    }



    public static boolean compareFields(String stringNew, String stringOld) {
        if (isEmpty(stringNew) && !isNullOrEmpty(stringOld)) {
            return true;
        }
        if (!isEmpty(stringNew) && isNullOrEmpty(stringOld)) {
            return true;
        }
        if (!isEmpty(stringNew) && !isNullOrEmpty(stringOld) && !stringNew.equals(stringOld)) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(final String... value) {
        for (String string : value) {
            if (isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(final String value) {
        return value == null || value.equalsIgnoreCase("null") ? true : value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }


    public static String randNumber(int length) {
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    public static boolean objectIsEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String)) {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

}
