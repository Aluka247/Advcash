package com.advcashs.advcash;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private AppOpenAd appOpenAd;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
        showAppOpenAdIfAvailable();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Save any app state if needed
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // Save any app state if needed
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // Save any app state if needed
    }

    private void loadAppOpenAd() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle ad loading failure
            }
        };

        AdRequest adRequest = new AdRequest.Builder().build();
        AppOpenAd.load(
                this,
                "ca-app-pub-1637479756161526/3820466831",
                adRequest,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback);
    }

    private void showAppOpenAdIfAvailable() {
        if (appOpenAd != null) {
            appOpenAd.show(currentActivity);
        } else {
            loadAppOpenAd();
        }
    }
}