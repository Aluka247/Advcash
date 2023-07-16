package com.advcashs.advcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        RewardedAd.load(this, "ca-app-pub-1637479756161526/7759711843",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Ad was loaded.");
                        ServerSideVerificationOptions options = new ServerSideVerificationOptions
                                .Builder()
                                .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                                .build();
                        rewardedAd.setServerSideVerificationOptions(options);
                    }
                });


        InterstitialAd.load(this,"ca-app-pub-1637479756161526/7621531923", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });


        ImageButton back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(v -> {
            onBackPressed();
            vibrate(15); // Vibrate for 50 milliseconds
            if (mInterstitialAd != null) {
                mInterstitialAd.show(SettingsActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }

            if (rewardedAd != null) {
                Activity activityContext = SettingsActivity.this;
                rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        });


        // Set click listener for the button
        CardView clearCache = findViewById(R.id.clear_cache);
        CardView moreApps = findViewById(R.id.more_apps);
        CardView rateUs = findViewById(R.id.rate_us);
        CardView shareApp = findViewById(R.id.share_app);
        CardView appVersion = findViewById(R.id.app_version);


        moreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(15); // Vibrate for 50 milliseconds
                Toast.makeText(SettingsActivity.this,"You made a good decision",Toast.LENGTH_SHORT).show();
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SettingsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (rewardedAd != null) {
                    Activity activityContext = SettingsActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }


        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(15); // Vibrate for 50 milliseconds
                Toast.makeText(SettingsActivity.this,"Thank you",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.advcashs.advcash");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SettingsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (rewardedAd != null) {
                    Activity activityContext = SettingsActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }

            }
        });


        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(15); // Vibrate for 50 milliseconds
                Toast.makeText(SettingsActivity.this,"Thank you",Toast.LENGTH_SHORT).show();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Download ADVCASH and enjoy all round Transaction, with High rate Exchange:" +
                        " https://play.google.com/store/apps/details?id=com.advcashs.advcash");
                startActivity(shareIntent);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SettingsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (rewardedAd != null) {
                    Activity activityContext = SettingsActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });

        appVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(15); // Vibrate for 50 milliseconds
                Toast.makeText(SettingsActivity.this,"Version: 1.0",Toast.LENGTH_SHORT).show();
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SettingsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (rewardedAd != null) {
                    Activity activityContext = SettingsActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });



        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(15); // Vibrate for 50 milliseconds
                clearCache(SettingsActivity.this);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SettingsActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

                if (rewardedAd != null) {
                    Activity activityContext = SettingsActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }


    private void clearCache(Context context) {
        // Get the cache directory
        File cacheDir = context.getCacheDir();

        // Get the initial cache size
        long initialCacheSize = getDirectorySize(cacheDir);

        if (initialCacheSize > 0) {
            // Clear the application cache
            deleteDirectory(cacheDir);

            // Get the final cache size
            long finalCacheSize = getDirectorySize(cacheDir);

            // Calculate the size difference
            long cacheClearedSize = initialCacheSize - finalCacheSize;

            // Display a toast message with the cache cleared size
            Toast.makeText(context, "Cache cleared: " + formatFileSize(cacheClearedSize), Toast.LENGTH_SHORT).show();
        } else {
            // Display a toast message when there is nothing to clear
            Toast.makeText(context, "Nothing to clear", Toast.LENGTH_SHORT).show();
        }
    }

    private long getDirectorySize(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return 0;
        }

        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += getDirectorySize(file);
                }
            }
        }
        return size;
    }

    private void deleteDirectory(File directory) {
        if (directory == null || !directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDirectory(file);
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatFileSize(long size) {
        if (size <= 0) {
            return "0 B";
        }

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return String.format("%.2f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }


    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(milliseconds);
            }
        }
    }



}