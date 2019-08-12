package com.tao.utils;

import com.tao.utilslib.log.LogUtil;

/**
 * Created by Administrator on 2017-10-26.
 */

public class ExceptionLocationUtil {
    
     
    public static String takeExceptionInfo ( Throwable exception){
        StringBuilder builder = new StringBuilder();
        exception.printStackTrace();
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        if (stackTrace!=null){
            for (StackTraceElement stackTraceElement:stackTrace)
            {
                builder.append(  stackTraceElement.toString()+"\n");
              LogUtil.e("takeExceptionInfo" ,   stackTraceElement.toString()+"\n");
                 
            }
        }
         
        return builder.toString();
    }
    
    
    
    
    
}
