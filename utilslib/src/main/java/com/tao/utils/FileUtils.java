package com.tao.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;


import com.tao.globle.Path;
import com.tao.utilslib.log.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;


/**
 * 创建文件夹shidoe
 */
public class FileUtils {
    //用于存放本程序所有相关文件的文件夹
    public static String SHIDOE = Environment.getExternalStorageDirectory().toString() + "/shidoe";

    /**
     * 构造初始化 创建文件夹
     */
    public FileUtils(Activity context) {
        /**
         * 设备系统大于6.0请求权限
         */
//        requestPermission(context);

        File file = new File(SHIDOE);
        /**
         *如果文件夹不存在就创建
         */
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 动态申请权限(存储权限)
     *
     * @param context
     */
//    private void requestPermission(Activity context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//            }
//        }
//    }

    /**
     * 保存图片至本地
     *
     * @param path   图片存放路径 带后缀名
     * @param bitmap 保存的图片
     */
    public static void saveImage(String path, Bitmap bitmap) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据至本地
     *
     * @param path 图片存放路径 带后缀名
     * @ bitmap 保存的图片
     */
    public static void saveByte2File(String path, byte[] b) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (b != null) {
                out.write(b);

                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("ERROR", " 保存byte异常 " + e.toString());
        }
    }

    public static File[] getFileDirList(String path) {

        File file = new File(path);

        File[] files = file.listFiles();

        return files;


    }

    /**
     * 复制单个文件
     *
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     * @ oldPath String 原文件路径 如：c:/fqf.txt
     */
    public static void copyFileFromStream(InputStream oldStrem, String newPath) {

        int bytesum = 0;
        int byteread = 0;

        FileOutputStream fs;
        try {
            fs = new FileOutputStream(newPath);

            byte[] buffer = new byte[1444];

            while ((byteread = oldStrem.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小   
//                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            oldStrem.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            LogUtil.e("", "  复制文件出错！ ");
        }

    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时   
                InputStream inStream = new FileInputStream(oldPath); //读入原文件   
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小   

                    fs.write(buffer, 0, byteread);
                }
                System.out.println("文件大小：" + bytesum);
                inStream.close();
                return true;
            }
           

        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return false;
    }

    public static void copyAssiets(InputStream is, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;

            if (is != null) {
                //读入原文件   
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = is.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小   
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "" + e.toString());

        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹   
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹   
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }


    public static String getFileNamefromPath(String path) throws Exception {


        String[] split = path.split("/");


        return split[split.length - 1];

    }


    public static void saveString2file(String path, String message) {

        File file = new File(path.trim());

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            long fileSize = FileSizeUtil.getFileSize(file);

            if (fileSize > (1024 * 1024 * 50)) {
                FileUtils.copyFile(file.getAbsolutePath(), Path.transit + "/" + file.getName());
                file.delete();
                file.createNewFile();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            outputStreamWriter.write(message);

            outputStreamWriter.flush();
            outputStreamWriter.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileString(String path){
        File file = new File(path);
        if (file.exists()){
            try {
                FileInputStream fileInput = new FileInputStream(file);

                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                StringBuilder sb=new StringBuilder();
                String str ;
                while ((str =reader.readLine())!=null)
                {
                    sb.append(str);
                }

                try {
                    reader.close();
                    fileInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            } catch ( Exception e) {
                e.printStackTrace();
            }
 
        }


        return "";
    }


    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

}