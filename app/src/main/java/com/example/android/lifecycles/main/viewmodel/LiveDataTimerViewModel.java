package com.example.android.lifecycles.main.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Erica Cao
 * @since 2018/8/15
 */
public class LiveDataTimerViewModel extends ViewModel {

    private static final long SECOND_TIME = 1000;

    private long mInitialTime;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    public LiveDataTimerViewModel() {
        mInitialTime = SystemClock.elapsedRealtime();

        // Update the elapsed time every second.
        new Timer("LiveDateTimer").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(elapsedTime);
            }
        }, SECOND_TIME, SECOND_TIME);
    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }

}
