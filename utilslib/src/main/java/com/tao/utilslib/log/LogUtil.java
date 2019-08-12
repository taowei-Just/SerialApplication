package com.tao.utilslib.log;

/*
 * 文件名:     LogUtil
 * 创建者:     
 * 创建时间:   2016/10/22 20:21
 * 描述:       输出Log的工具类
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class LogUtil {

    //Log开关
    private static boolean isOpen = true;
    private static boolean isSave = false;
    public static String LogPath = Environment.getExternalStorageDirectory() + "/log/";

    public static String getDateTime() {
        return new SimpleDateFormat("yyyy_MM_dd").format(new Date(System.currentTimeMillis())) + "/";
    }

    public static String getLogPath() {
        return LogPath + getDateTime();
    }

    /**
     * 设置是否输出Log
     *
     * @param b
     */


    public static void setSwitch(boolean b) {
        isOpen = b;
    }

    //**********************Debug***********//
    public static void d(String tag, String msg) {
        if (isOpen) Log.d(tag, msg);
    }

    public static void d(String tag, int msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, float msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, long msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, double msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, boolean msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    //**********************Info***********//
    public static void i(String tag, String msg) {
        if (isOpen) Log.i(tag, msg);
    }

    public static void i(String tag, int msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, float msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }


    public static void i(String tag, long msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, double msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    //**********************Error***********//
    public static void e(String tag, String msg) {
        if (tag == null)
            return;
        if (isOpen) {
            Log.e(tag, msg);
            if (isSave  )
                saveLogToFile(getLogPath() + tag + "/Log_E.txt", msg);
        }
    }

    public static void e(String tag, String msg, boolean isSave) {
        if (isOpen) Log.e(tag, msg);
        if (isSave)
            saveLogToFile(getLogPath() + tag + "/Log_E.txt", msg);

    }

    public static void e(String tag, int msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, float msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, Long msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, double msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, boolean msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }


    public static synchronized void saveLogToFile(String path, String str) {
        try {
            createFile(path);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(System.currentTimeMillis()));
            str = time + "\n" + str + "\n  ================================== \n";
            saveLogHead(path, str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static synchronized void saveLogHead(String logPah, String logStr) {
        try {
            logStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date(System.currentTimeMillis())) + ">>>" + logStr + " \n";

            File file = new File(logPah);
            File buffFile = new File(file.getParentFile().getAbsolutePath() + "/" + file.getName() + "_buff");
            if (!buffFile.exists())
                buffFile.createNewFile();

            FileOutputStream buffOut = new FileOutputStream(buffFile);
            FileInputStream buffIn = new FileInputStream(buffFile);
            FileInputStream logInput = new FileInputStream(file);

            int len = 0;
            byte[] buff = new byte[20480];

            while ((len = logInput.read(buff)) > 0) {
                buffOut.write(buff, 0, len);
            }
            logInput.close();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.flush();
            fileWriter.close();

            FileOutputStream logOut = new FileOutputStream(file, true);
            logOut.write(logStr.getBytes());

            while ((len = buffIn.read(buff)) > 0) {
                logOut.write(buff, 0, len);
            }

            buffOut.close();
            buffIn.close();
            logOut.close();
            buffIn.close();

//            FileWriter buffwr = new FileWriter(buffFile);
//            buffwr.flush();
//            buffwr.close();
//            if(buffFile.exists())
//                buffFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void mkdirs(String paramString) {
        File localFile = new File(paramString);
        if (!localFile.exists())
            localFile.mkdirs();
    }

    public static File createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        return file;
    }


}
