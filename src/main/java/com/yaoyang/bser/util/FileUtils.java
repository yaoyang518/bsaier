package com.yaoyang.bser.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Collection;

/**
 * 文件工具类
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public class FileUtils {

    public static void copyFile(final File in, final File out)
            throws IOException {
        if (!in.exists()) {
            return;
        }
        final FileChannel inChannel = new FileInputStream(in).getChannel();
        final FileChannel outChannel = new FileOutputStream(out).getChannel();

        try {
            // inChannel.transferTo(0, inChannel.size(), outChannel); //
            // original -- apparently has trouble copying large files on Windows
            // magic number for Windows, (64Mb - 32Kb)
            final int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            final long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position += inChannel
                        .transferTo(position, maxCount, outChannel);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public static void download(final String url, final File file)
            throws Exception {
        final URL urlObj = new URL(url);

        final InputStream inputStream = urlObj.openStream();
        final FileOutputStream outputStream = new FileOutputStream(file);
        final byte[] read = new byte[49152];
        int count;
        while ((count = inputStream.read(read)) != -1) {
            outputStream.write(read, 0, count);
            if (count % 1000 == 0) {
            }
        }
        inputStream.close();
        outputStream.close();
    }

    public static InputStream getFileInputStream(String imagePath)
            throws IOException {
        InputStream netFileInputStream;
        final URL url = new URL(imagePath);
        final URLConnection urlConn = url.openConnection();
        netFileInputStream = urlConn.getInputStream();
        return netFileInputStream;
    }

    public static boolean isImageExists(final String imagePath) {
        InputStream netFileInputStream = null;
        try {
            netFileInputStream = getFileInputStream(imagePath);
            if (null != netFileInputStream) {
                return true;
            } else {
                return false;
            }
        } catch (final IOException e) {
            return false;
        } finally {
            try {
                if (netFileInputStream != null) {
                    netFileInputStream.close();
                }
            } catch (final IOException e) {
            }
        }
    }

    public static boolean isEmptyFile(final File file) {
        return file == null || !file.exists() || file.length() < 100;
    }

    private static File createFile(final String filePath) {
        final File file = new File(filePath);
        try {
            org.apache.commons.io.FileUtils.forceMkdir(file.getParentFile());
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getFile(final String filePath) {
        final File file = createFile(filePath);
        return file;
    }

	public static void main(final String[] args) {
        String prefix = "MN-";
        int count = 1;
		Collection<File> files = org.apache.commons.io.FileUtils.listFiles(new File("/Volumes/FinalCut/MG图片/美女壁纸/"),null, false);
        for (File file : files) {

            String path = file.getAbsolutePath();
            String targetPath = path.substring(0, path.lastIndexOf("/")+1);

            String name = file.getName();
            String type = name.substring(name.indexOf("."));
            if (count >=10000){
                name = prefix + count + type;
            }else  if (count >= 1000){
                name = prefix + "0"+count+type;
            }else  if (count >= 100){
                name = prefix + "00"+count+type;
            }else  if (count >= 10){
                name = prefix + "000"+count+type;
            }else{
                name = prefix + "0000"+count+type;
            }
            file.renameTo(new File(targetPath+name));
            count++;
            System.out.println(count);
        }
	}

}
