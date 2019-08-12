package com.tao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
/*
 * @Description ʱ��ת��
 */
public class TimeUtil {

	@SuppressLint("SimpleDateFormat")
	public static String timeStamp2Date(long timeStamp, String formats) {
		// Long timestamp = Long.parseLong(timestampString);
		String date = new SimpleDateFormat(formats).format(new Date(timeStamp));
		return date;
	}
	
	@SuppressLint("SimpleDateFormat") 
	public static String TimeNowWithFormat(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

}
