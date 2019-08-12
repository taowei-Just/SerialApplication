package com.tao.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017-09-11.
 */



public class DisplayUtil {

    /**
     *  
     * PPI = √（长度像素数² + 宽度像素数²） / 屏幕对角线英寸数
     * px = dp*ppi/160
      dp = px / (ppi / 160)
       px = sp*ppi/160
      sp = px / (ppi / 160)
     
     * 
     */

    /**
     * dp 转px
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    
    /**
     * px转dp
     * 
     * @param context
     * @param px
     * @return
     */
    
    
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    
    public  static  void  caculateDP (Context context){
        int dp;
        float px;
        for (int i=1; i<=1920 ;i++) {
            px =i;
            dp = DisplayUtil.Px2Dp(context, px);
          
        }

    } 

    
    

}
