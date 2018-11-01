package com.astgo.fenxiao.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2015/2/5.
 * 文件读取工具
 */
public class FileUtil {
    private static final String TAG = "文件读取工具";
    /**
     * 向sdcard中写入文件
     * @param filename 文件名
     * @param content 文件内容
     */
    public static void saveLogToSDCard(String filename, String content){
        File file=new File(Environment.getExternalStorageDirectory(), filename);
        OutputStream out= null;
        try {
            out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 保存文件方法
     *
     * @param context  context
     * @param result   要写入文件的字符串内容
     * @param fileName 保存的文件名
     */
    public synchronized static void write(Context context, String result, String fileName) {
//        System.out.println("File IO Test ==>" + result);

        if (result != null) {
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//            outputStream.write(XMLUtils.formatXml(result).getBytes());
                outputStream.write(result.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件方法
     *
     * @param context  context
     * @param fileName 要读取文件的文件名
     * @return 字符串格式文件内容
     */
    public static String read(Context context, String fileName) {
        String result = null;

        FileInputStream inputStream;
        try {
            inputStream = context.openFileInput(fileName);

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (inputStream.read(buffer) != -1) {
                baos.write(buffer, 0, buffer.length);
            }
            inputStream.close();
            baos.close();
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //递归删除文件及文件夹
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (File childFile : childFiles) {
                delete(childFile);
            }
            file.delete();
        }
    }

    //检查文件
    public static boolean exists(File file) {
        return file.exists();
    }

    /**
     * 读取某路径下所有wav格式文件
     * @param path 文件搜索路径
     *  @param num 通话人号码 用于筛选通话录音
     */
    public static List<File> searchWav(String path, String num) {
        try {
            File f = new File(path);
            List<File> list = new ArrayList<>();
            if (f.exists()) {
                File[] files = f.listFiles();
                if (files != null) {// 先判断目录是否为空，否则会报空指针
                    for (File file : files) {
//                        Log.e(TAG, files.length + "");
                        String fileName = file.getName();
//                        Log.e(TAG, fileName);
                        String tempNum = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));//截取通话人的号码
//                        Log.e(TAG, tempNum);
                        if(num.equals(tempNum)){//只有当号码匹配才显示
                            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);//获取文件后缀名
                            if ("wav".equals(prefix)) {//如果为wav后缀文件
                                list.add(file);
                            }
                        }
                    }
                }
            }
            return  list;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 播放wav录音
     */
    public static void playWav(MediaPlayer mp, final Handler mHandler, final Runnable runnable) {
        try {
            mp.prepare();
            mp.start();
            mHandler.post(runnable);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 停止wav录音
     */
    public static void stopWav(MediaPlayer mp, final Handler mHandler, final Runnable runnable){
        mp.release();
        mHandler.removeCallbacks(runnable);
    }

    public static int getFileDuration(File file){
        int time = 0;
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(file.getAbsolutePath());
            mp.prepare();
           time =  mp.getDuration();
            mp.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 将Bitmap 图片保存到本地路径，并返回路径
     *
     * @param c
     * @param fileName 文件名称
     * @param bitmap   图片
     * @return
     */
    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/JiaXT/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }
}
