package com.tao.utilslib.ui;

 
import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017-10-12.
 */

public class ViewUtil {


    private   View view;
    private   Activity activity;

    public ViewUtil(View view) {
   this.view =view ;    
        
    }    public ViewUtil(Activity activity) {
   this.activity =activity ;    
        
    }

    public  View viewFromId (int id){
        
        if (null!=view)
        return  view.findViewById(id);
        
        if (null!=activity)
        return  activity.findViewById(id);
        
        return null;
        
    }
    
}
