package com.example.android.lifecycles.main.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

/**
 * @author Erica Cao
 * @since 2018/8/15
 */
public class MiscUtil {

    public static ActivityInfo[] listAllActivities(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getApplicationContext().getPackageName();
        try {
            return pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
