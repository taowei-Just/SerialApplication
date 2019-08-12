package com.tao.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Administrator on 2017-10-23.
 */

public class PollingUtils {

    //开启轮询服务  
    public static void startPollingService(Context context, int seconds, int alarmTag, Class<?> cls, String action) {
        //获取AlarmManager系统服务  
        AlarmManager manager = (AlarmManager) context .getSystemService(Context.ALARM_SERVICE);
        //包装需要执行Service的Intent  
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, alarmTag, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //触发服务的起始时间  
        long triggerAtTime = System.currentTimeMillis();
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service  
        manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, seconds * 1000, pendingIntent);
    }

    //停止轮询服务  
    public static void stopPollingService(Context context, int alarmTag, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager) context .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, alarmTag,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //取消正在执行的服务  
        manager.cancel(pendingIntent);
    }

    public static void startPollingReceiver(Context applicationContext, int updataAlareTime, int updataAlarmTag, Class<BroadcastReceiver> updataServiceClass, String s) {
        
    }
}
