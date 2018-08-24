package com.example.android.lifecycles.main;

import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * @author Erica Cao
 * @since 2018/8/21
 */
public class BoundLocationManager {

    private static final String TAG = BoundLocationManager.class.getSimpleName();

    public static void bindLocationListener(LifecycleOwner lifecycleOwner,
                                            LocationListener listener, Context context) {
        new BoundLocationListener(lifecycleOwner, listener, context);
    }

    @SuppressWarnings("MissingPermission")
    static class BoundLocationListener implements LifecycleObserver {

        private final Context mContext;
        private final LocationListener mListener;
        private LocationManager mLocationManager;

        BoundLocationListener(LifecycleOwner lifecycleOwner,
                              LocationListener listener, Context context) {
            mContext = context;
            mListener = listener;
            lifecycleOwner.getLifecycle().addObserver(this);
        }

        @SuppressWarnings("ConstantConditions")
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void addLocationListener() {
            if (mListener == null) {
                Log.e(TAG, "Bound Location need listener to track location changed");
                return;
            }

            Log.d(TAG, "add listener");
            mLocationManager = (LocationManager) mContext.getSystemService(Service.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);

            // Force an update with the last location, if available.
            Location lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                mListener.onLocationChanged(lastLocation);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void removeLocationListener() {
            if (mLocationManager == null) {
                return;
            }
            Log.d(TAG, "remove listener");
            mLocationManager.removeUpdates(mListener);
            mLocationManager = null;
        }
    }
}
