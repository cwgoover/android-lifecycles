package com.example.android.lifecycles.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.PopupMenu;

import com.example.android.codelabs.lifecycle.R;

/**
 * @author Erica Cao (i352072)
 * @email erica.cao@sap.com
 * @since 2018/8/14
 */
public class ChronoMainActivity extends AppCompatActivity {

    private static final String TAG = ChronoMainActivity.class.getSimpleName();

    private static final int MENU_GROUP_ID = 0;

    private ActivityInfo[] mMyActivityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chrono);
        setupToolbal();

        // The ViewModelStore provides a new ViewModel or one previously created.
        ChronometerViewModel chronometerViewModel
                = ViewModelProviders.of(this).get(ChronometerViewModel.class);

        startChronometer(chronometerViewModel);

        listAllActivities();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View view = findViewById(R.id.activity_list);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMenu(v);
                    }
                });
            }
        });
        return true;
    }

    private void setupToolbal() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void startChronometer(ChronometerViewModel chronometerViewModel) {
        Chronometer chronometer = findViewById(R.id.chronometer);
        if (chronometerViewModel.getStartTime() == 0) {
            // If the start date is not defined, it's a new ViewModel so set it.
            long base = SystemClock.elapsedRealtime();
            chronometerViewModel.setStartTime(base);
            chronometer.setBase(base);
        } else {
            // Otherwise the ViewModel has been retained, set the chronometer's base to the original
            // starting time.
            chronometer.setBase(chronometerViewModel.getStartTime());
        }
        chronometer.start();
    }

    private void listAllActivities() {
        PackageManager pm = getPackageManager();
        String packageName = getApplicationContext().getPackageName();
        try {
            mMyActivityInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showMenu(View view) {
        if (mMyActivityInfo == null || mMyActivityInfo.length <= 0) {
            Log.e(TAG, "showMenu fail, mMyActivityInfo is invalid");
        }

        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < mMyActivityInfo.length; i++) {
            ActivityInfo activityInfo = mMyActivityInfo[i];
            if (activityInfo != null && !TextUtils.isEmpty(activityInfo.name)) {
                // find the class name in the last of the string
                String className = activityInfo.name.substring(activityInfo.name.lastIndexOf(".") + 1,
                        activityInfo.name.length());
                menu.add(MENU_GROUP_ID, i, i, className);
            }
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ActivityInfo activity = mMyActivityInfo[item.getItemId()];
                ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                Intent intent = new Intent();
                intent.setComponent(name);
                startActivity(intent);
                return true;
            }
        });
        popupMenu.show();
    }

}
