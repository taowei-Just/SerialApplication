package com.tao.globle;

import android.os.Environment;

/**
 * Created by Tao on 2019/1/8.
 */

public class Path {
    public static String RootPath = Environment.getExternalStorageDirectory() + "/HytTobacco/";

    public static String AdvPath = RootPath + "adv/";
    public static String ImageAdvPath = AdvPath + "image/";
    public static String VideoAdvPath = AdvPath + "video/";

    public static String transit  =  RootPath +"transitCache/" ;
}
