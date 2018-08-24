package com.example.android.lifecycles.main.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.codelabs.lifecycle.R;
import com.example.android.lifecycles.main.viewmodel.LiveDataTimerViewModel;

import java.util.Locale;

/**
 * @author Erica Cao
 * @since 2018/8/20
 */
public class LiveDataTimerFragment extends Fragment {

    private LiveDataTimerViewModel mViewModel;

    private TextView mChronoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_data_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChronoView = view.findViewById(R.id.chronometer);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(LiveDataTimerViewModel.class);
        subscribe();
    }

    private void subscribe() {
        mViewModel.getElapsedTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                mChronoView.setText(String.format(Locale.getDefault(), "%d seconds elapsed...", aLong));
            }
        });
    }
}
