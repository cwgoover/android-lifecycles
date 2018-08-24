package com.example.android.lifecycles.main.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.example.android.codelabs.lifecycle.R;
import com.example.android.lifecycles.main.viewmodel.ChronometerViewModel;

/**
 * @author Erica Cao
 * @since 2018/8/20
 */
public class ChronometerFragment extends Fragment {

    private ChronometerViewModel mChronometerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The ViewModelStore provides a new ViewModel or one previously created.
        mChronometerViewModel = ViewModelProviders.of(this).get(ChronometerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_chronometer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startChronometer(view);
    }

    private void startChronometer(View v) {
        Chronometer chronometer = v.findViewById(R.id.chronometer);
        if (mChronometerViewModel.getStartTime() == 0) {
            // If the start date is not defined, it's a new ViewModel so set it.
            long base = SystemClock.elapsedRealtime();
            mChronometerViewModel.setStartTime(base);
            chronometer.setBase(base);
        } else {
            // Otherwise the ViewModel has been retained, set the chronometer's base to the original
            // starting time.
            chronometer.setBase(mChronometerViewModel.getStartTime());
        }
        chronometer.start();
    }
}
