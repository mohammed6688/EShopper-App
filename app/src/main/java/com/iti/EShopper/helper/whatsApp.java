package com.iti.EShopper.helper;

import android.content.Context;
import android.content.pm.PackageManager;

public class whatsApp {
    // if Whatsapp installed on device
    public static boolean appInstalled(String uri, Context context){
        PackageManager packageManager = context.getPackageManager();
        boolean appInstalled;
        try{
            packageManager.getPackageInfo(uri, packageManager.GET_ACTIVITIES);
            appInstalled = true;
        }catch (PackageManager.NameNotFoundException ex){
            appInstalled = false;
        }
        return appInstalled;
    }
}

