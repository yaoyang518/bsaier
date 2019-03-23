package com.yaoyang.bser.util;

import java.util.Random;
import java.util.UUID;

/**
 * UUID 工具类
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public class UUIDGenerator {

    public synchronized static String get() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 最后又重复两个0和1，因为需要凑足数组长度为64
    private static final char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1'};

    private static final Random random = new Random();

    // 生成指定长度的随机字符串
    public static String createRandomString(final int length) {
        if (length > 0) {
            int index = 0;
            final char[] temp = new char[length];
            int num = random.nextInt();
            for (int i = 0; i < length % 5; i++) {
                // 取后面六位，记得对应的二进制是以补码形式存在的。
                temp[index++] = ch[num & 63];
                // 63的二进制为:111111
                num >>= 6;
                // 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
            }
            for (int i = 0; i < length / 5; i++) {
                num = random.nextInt();
                for (int j = 0; j < 5; j++) {
                    temp[index++] = ch[num & 63];
                    num >>= 6;
                }
            }
            return new String(temp, 0, length).toLowerCase();
        } else if (length == 0) {
            return "";
        } else {
            throw new IllegalArgumentException();
        }
    }

    // 根据指定个数，测试随机字符串函数的重复率
    public static double rateOfRepeat(final int number) {
        int repeat = 0;
        final String[] str = new String[number];
        // 生成指定个数的字符串
        for (int i = 0; i < number; i++) {
            str[i] = createRandomString(6);
            System.out.println(str[i]);
        }
        // 查找是否有相同的字符串
        for (int i = 0; i < number; i++) {
            for (int j = i + 1; j < number - 1; j++) {
                if (str[i].equals(str[j])) {
                    repeat++;
                }
            }
        }
        return ((double) repeat) / number;
    }

//	public static void main(final String[] args) {
//		System.out.println(createRandomString(6));
//		final double rate = rateOfRepeat(10000);// 测试10000次的重复率
//		System.out.println("重复率:" + rate);
//	}
}
