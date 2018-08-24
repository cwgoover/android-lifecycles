package com.example.android.lifecycles.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.android.codelabs.lifecycle.R;
import com.example.android.lifecycles.main.data.Model;
import com.example.android.lifecycles.main.ui.LocationFragment;
import com.example.android.lifecycles.main.util.MiscUtil;

/**
 * @author Erica Cao
 * @since 2018/8/14
 * Reference: https://codelabs.developers.google.com/codelabs/android-lifecycles/#0
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
        // FIXME: user can pick up each one
        addView(Model.LocationBounder);

        mMyActivityInfo = MiscUtil.listAllActivities(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Checking permission in the fragment can't get response:
        // 1. In AppCompatActivity use the method ActivityCompat.requestpermissions
        // 2. In v4 support fragment you should use requestpermissions
        // 3. Catch is if you call AppcompatActivity.requestpermissions in your fragment then callback will come to activity and not fragment
        // 4. Make sure to call super.onRequestPermissionsResult from the activity's onRequestPermissionsResult.
        // Reference: https://stackoverflow.com/questions/35989288/onrequestpermissionsresult-not-being-called-in-fragment-if-defined-in-both-fragm
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment != null && currentFragment instanceof LocationFragment) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    private void setupToolbal() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void addView(Model model) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, Model.getModelFragment(model));
        ft.addToBackStack(null);
        ft.commit();
    }

}
