package com.tao.utilslib.ui;

/*
 * date: 2017/9/11 11:15
 * author: tao
 */
 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


public class FragmentTools {



    public static void addFragment(FragmentActivity activity , Fragment fragment, int id, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(id, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
    
    public static void replaceFragment(FragmentActivity activity , Fragment fragment, int id, String tag) {
    	FragmentManager manager = activity.getSupportFragmentManager();
    	FragmentTransaction transaction = manager.beginTransaction();
    	transaction.replace( id, fragment, tag);
    	transaction.commitAllowingStateLoss();
    }   
    
    public static void replaceFragmentAttechAnimation(FragmentActivity activity , Fragment fragment, int id, String tag , int in , int out) {
    	FragmentManager manager = activity.getSupportFragmentManager();
    	FragmentTransaction transaction = manager.beginTransaction();
    	transaction.setCustomAnimations(in,out);
    	transaction.replace( id, fragment, tag);
    	transaction.commitAllowingStateLoss();
    }


    public static void hideFragment(FragmentActivity activity , Fragment fragment ) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide( fragment);
        View parent = (View) fragment.getView().getParent();
        if (parent!=null) {
            parent.setVisibility(View.GONE);
        }
        transaction.commitAllowingStateLoss();

    }

    public static void removeFragment(FragmentActivity activity , Fragment fragment ) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove( fragment);
        transaction.commitAllowingStateLoss();
        
       
    }
    
     

    public static void showFragment(FragmentActivity activity , Fragment fragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show( fragment);
        View parent = (View) fragment.getView().getParent();
        if (parent!=null) {
            parent.setVisibility(View.VISIBLE);
        }
        transaction.commitAllowingStateLoss();
    }

    public static Fragment findFragmentByTag(FragmentActivity activity , String tag) {

        return activity.getSupportFragmentManager().findFragmentByTag(tag);

    }
    public static Fragment findFragment(FragmentActivity activity , int id) {



        return activity.getSupportFragmentManager().findFragmentById(id);

    }

    

}
