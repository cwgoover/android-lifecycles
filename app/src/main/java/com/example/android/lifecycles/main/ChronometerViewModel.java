package com.example.android.lifecycles.main;

import android.arch.lifecycle.ViewModel;

/**
 * @author Erica Cao (i352072)
 * @email erica.cao@sap.com
 * @since 2018/8/14
 */
public class ChronometerViewModel extends ViewModel {

    private long mStartTime;

    public ChronometerViewModel() {

    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public long getStartTime() {
        return mStartTime;
    }
}
