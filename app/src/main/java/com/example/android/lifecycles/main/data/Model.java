package com.example.android.lifecycles.main.data;

import android.support.v4.app.Fragment;

import com.example.android.lifecycles.main.ui.ChronometerFragment;
import com.example.android.lifecycles.main.ui.LiveDataTimerFragment;
import com.example.android.lifecycles.main.ui.LocationFragment;

/**
 * @author Erica Cao
 * @since 2018/8/21
 */
public enum Model {
    Chronometer,
    LiveDataTimer,
    LocationBounder;

    public static Fragment getModelFragment(Model model) {
        Fragment fragment;
        switch (model) {
            case Chronometer:
                fragment = new ChronometerFragment();
                break;
            case LiveDataTimer:
                fragment = new LiveDataTimerFragment();
                break;
            case LocationBounder:
                fragment = new LocationFragment();
                break;
            default:
                fragment = new ChronometerFragment();
                break;
        }
        return fragment;
    }
}
