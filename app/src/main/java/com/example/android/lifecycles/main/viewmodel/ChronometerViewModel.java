package com.example.android.lifecycles.main.viewmodel;

import android.arch.lifecycle.ViewModel;

/**
 * @author Erica Cao
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
