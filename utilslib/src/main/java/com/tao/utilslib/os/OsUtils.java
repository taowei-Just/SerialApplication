package com.tao.utilslib.os;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.tao.utilslib.log.LogUtil;

import java.io.File;
import java.util.List;


/**
 * Created by Administrator on 2017-12-11.
 */

public class OsUtils {
    // 检查网络

    /**
     * 返回 当前网络是否可用
     *
     * @param context
     * @return
     */
    
    public static boolean checkNetStatue(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // 查看apk 信息
    public static PackageInfo getApkInfo(String apkPath, Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
    }

    /**
     * 检测辅助功能是否开启<br>
     * 方 法 名：isAccessibilitySettingsOn <br>
     * 创 建 人
     *
     * @param mContext
     * @return boolean
     */
    public static boolean isAccessibilitySettingsOn(Context mContext, String classPath) {
        int accessibilityEnabled = 0;
        String TAG = "AccessibilitySettings";
        // TestService为对应的服务  
//        final String service = getPackageName() + "/" + TestService.class.getCanonicalName();
        // com.z.buildingaccessibilityservices/android.accessibilityservice.AccessibilityService  
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            LogUtil.e(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            LogUtil.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
//            LogUtil.e(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            // com.z.buildingaccessibilityservices/com.z.buildingaccessibilityservices.TestService  
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

//                    LogUtil.e(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + classPath);
                    if (accessibilityService.equalsIgnoreCase(classPath)) {
                        LogUtil.e(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            LogUtil.e(TAG, " ACCESSIBILITY IS DISABLED ");
        }
        return false;
    }


    public static void jumpAccessibility(Activity mContext, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mContext.startActivityForResult(intent, requestCode);
    }

    //获取指定包信息

    public static PackageInfo getAppointPackageInfo(Context context, String packageName) {
        List<PackageInfo> packageInfoList = AppInfoUtil.getPackageInfoList(context);
//        String packageName = context.getPackageName();

        for (PackageInfo info : packageInfoList) {

            if (packageName != null) {
//                LogUtil.e("getAppointPackageInfo", " 已安装包名 " + info.packageName);
                if (info.packageName.equals(packageName)) {

                    return info;
                }

            }

        }

        return null;
    }

    //普通安装
    public static void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.example.chenfengyao.installapkdemo", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void installAPK(Context context, String path) {

        installNormal(context, path);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
//        context.startActivity(intent);

    }

    public static boolean checkIsPolicy(Context context, ComponentName componentName) {
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        return mDPM.isAdminActive(componentName);
    }

    public static void registerPolicy(Activity context, ComponentName componentName, int REQUEST_ACTIVATE) {

        //        直接跳转到当前应用的 激活/关闭 界面
//        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "请激活设备管理器");
//        context.startActivityForResult(intent, REQUEST_ACTIVATE);

        // 会跳转到设备管理器列表
        // this will go to the list of admin apps
        Intent  intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings"));
        context.startActivityForResult(intent,REQUEST_ACTIVATE);

    }
    public static TopActivityInfo getTopActivityInfo(Context context) {
        ActivityManager manager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        TopActivityInfo info = new TopActivityInfo();
        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
            if (topAppProcess != null && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                info.packageName = topAppProcess.processName;
                info.topActivityName = "";
            }
        } else {
            //getRunningTasks() is deprecated since API Level 21 (Android 5.0)  
            List localList = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo) localList.get(0);
            info.packageName = localRunningTaskInfo.topActivity.getPackageName();
            info.topActivityName = localRunningTaskInfo.topActivity.getClassName();
        }
        return info;
    }

    public  static class TopActivityInfo {

      String  packageName ;
        String topActivityName ;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getTopActivityName() {
            return topActivityName;
        }

        public void setTopActivityName(String topActivityName) {
            this.topActivityName = topActivityName;
        }
    }
}
