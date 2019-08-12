package com.tao.utilslib.os.alarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Tao on 2018/11/27.
 */

public class OsAlarmUtil {

      enum PendingType {
        service, broadCast
    }
    public static boolean excuteAlarm(Context context, Class cl, int requestCode, long startTime, long intervalTime, PendingType type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cl);
        PendingIntent pendingIntent = null;
        
        switch (type) {
            case service:
                pendingIntent = PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case broadCast:
                pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;
        }
        if (pendingIntent == null)
            return false;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
             return true;
    }

    public static boolean cancleAlarm(Context context, Class cl, int requestCode, PendingType type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cl);
        PendingIntent pendingIntent = null;
        switch (type) {
            case service:
                pendingIntent = PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case broadCast:
                pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;
        }
        
        if (pendingIntent == null)
            return false;
        alarmManager.cancel(pendingIntent);
        return true;
    }


}
