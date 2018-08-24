package com.example.android.lifecycles.main;

import android.app.Application;
import android.util.Log;

/**
 * @author Erica Cao
 * @since 2018/8/20
 */
public class ChronoApplication extends Application {

    private static final String TAG = ChronoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

}
