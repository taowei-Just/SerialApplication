package com.tao.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017-10-24.
 */

public class AppBackUtil {

    /**
     * @Description 将app由data/app目录拷贝到sd卡下的指定目录中
     * @  appId 应用程序的ID号，如com.wondertek.jttxl
     * @param dest 需要将应用程序拷贝的目标位置
     * @date 2013-7-24 下午3:32:12
     */
    public static  String backupApplication(String packagePath, String dest) {
        if (packagePath == null || packagePath.length() == 0
                || dest == null || dest.length() == 0) {
            return "illegal parameters";
        }
//        Util.Trace("[backupApplication] appId: " + packageName + ", dest:" + dest);
        // check file /data/app/appId-1.apk exists
//        String apkPath = "/data/app/" + packagePath + "-1.apk";
        File apkFile = new File(packagePath);
        
        
        if (apkFile.exists() == false) {
            return packagePath +  " doesn't exist!";
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(apkFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        File file = new File(dest);
        if (file.exists())
            file.delete();
        // create dest folder if necessary
        int i = dest.lastIndexOf('/');
        if (i != -1) {
            File dirs = new File(dest.substring(0, i));
            dirs.mkdirs();
            dirs = null;
        }

  
       
        // do file copy operation
        byte[] c = new byte[1024];
        int slen;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            while ((slen = in.read(c, 0, c.length)) != -1)
                out.write(c, 0, slen);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            }
        }
        return "success";
    }
}
