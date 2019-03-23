package com.yaoyang.bser.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public class ValidateImageCodeUtils {

    /**
     * 安全等级枚举
     */
    public enum SecurityCodeLevel {
        Simple, Medium, Hard
    }

    /**
     * 获取默认安全码
     *
     * @return
     */
    public static String getSecurityCode() {
        return getSecurityCode(4, SecurityCodeLevel.Hard, false);
    }

    /**
     * 获取安全码
     *
     * @param length
     * @param level
     * @param isCanRepeat
     * @return
     */
    public static String getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
        int len = length;
        char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
                'Z'};
        if (level == SecurityCodeLevel.Simple) {
            codes = Arrays.copyOfRange(codes, 0, 10);
        } else if (level == SecurityCodeLevel.Medium) {
            codes = Arrays.copyOfRange(codes, 0, 36);
        }
        int n = codes.length;
        if (len > n && isCanRepeat == false) {
            throw new RuntimeException(String.format(
                    "调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
                    level, isCanRepeat, n));
        }
        char[] result = new char[len];
        if (isCanRepeat) {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);
                result[i] = codes[r];
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);
                result[i] = codes[r];
                codes[r] = codes[n - 1];
                n--;
            }
        }
        return String.valueOf(result);
    }

    public static String getImageBase64Code(String code) {
        ByteArrayInputStream inputStream = null;
        ImageOutputStream imOut = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            BufferedImage image = createImage(code);
            imOut = ImageIO.createImageOutputStream(outStream);
            ImageIO.write(image, "png", imOut);
            byte[] b = outStream.toByteArray();
            inputStream = new ByteArrayInputStream(b);
            byte[] data = null;
            // 读取图片字节数组
            try {
                data = new byte[inputStream.available()];
                inputStream.read(data);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                inputStream.close();
            }
            // 对字节数组Base64编码
            return BASE64Encoder.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                imOut.close();
                inputStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void getImage(String code, HttpServletResponse response) throws IOException {
        ImageOutputStream imOut = null;
        OutputStream outStream = response.getOutputStream();
        try {
            BufferedImage image = createImage(code);
            imOut = ImageIO.createImageOutputStream(outStream);
            ImageIO.write(image, "png", imOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                imOut.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*---以下未整理---*/

    /**
     * 鐢熸垚楠岃瘉鐮佸浘鐗�
     *
     * @param securityCode
     * @return
     */
    public static BufferedImage createImage(String securityCode) {

        int codeLength = securityCode.length();// 楠岃瘉鐮侀暱搴�

        int fontSize = 18;// 瀛椾綋澶у皬

        int fontWidth = fontSize + 1;

        // 鍥剧墖瀹介珮

        int width = codeLength * fontWidth + 6;
        int height = fontSize * 2 + 1;
        // 鍥剧墖

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);// 璁剧疆鑳屾櫙鑹�

        g.fillRect(0, 0, width, height);// 濉厖鑳屾櫙

        // g.setColor(Color.LIGHT_GRAY);// 璁剧疆杈规棰滆壊
        g.setColor(new Color(106, 101, 202));
        // g.setColor(Color.GREEN);

        g.setFont(new Font("Arial", Font.BOLD, height - 2));// 杈规瀛椾綋鏍峰紡

        g.drawRect(0, 0, width - 1, height - 1);// 缁樺埗杈规

        // 缁樺埗鍣偣

        Random rand = new Random();

        g.setColor(Color.LIGHT_GRAY);

        for (int i = 0; i < codeLength * 6; i++) {

            int x = rand.nextInt(width);

            int y = rand.nextInt(height);

            g.drawRect(x, y, 1, 1);// 缁樺埗1*1澶у皬鐨勭煩褰�

        }

        // 缁樺埗楠岃瘉鐮�

        int codeY = height - 10;

        // g.setColor(new Color(19, 148, 246));
        //g.setColor(new Color(0, 195, 69));
        g.setColor(new Color(106, 101, 202));

        g.setFont(new Font("Georgia", Font.BOLD, fontSize));
        for (int i = 0; i < codeLength; i++) {
            double deg = new Random().nextDouble() * 20;
            g.rotate(Math.toRadians(deg), i * 16 + 13, codeY - 7.5);
            g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5, codeY);
            g.rotate(Math.toRadians(-deg), i * 16 + 13, codeY - 7.5);
        }

        g.dispose();// 鍏抽棴璧勬簮

        return image;

    }

    /**
     * 将BufferedImage转换成ByteArrayinputStream
     *
     * @param image
     * @return
     */

    public static ByteArrayInputStream convertImageToStream(BufferedImage image) {

        ByteArrayInputStream inputStream = null;

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        ImageOutputStream imOut = null;
        try {
            imOut = ImageIO.createImageOutputStream(outStream);
            ImageIO.write(image, "png", imOut);
            byte[] b = outStream.toByteArray();
            inputStream = new ByteArrayInputStream(b);

        } catch (Exception e) {

        }

        return inputStream;

    }


}
