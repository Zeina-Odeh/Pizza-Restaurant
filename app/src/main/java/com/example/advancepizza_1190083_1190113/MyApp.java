package com.example.advancepizza_1190083_1190113;

import android.app.Application;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PeriodicWorkRequest offerCleanupWorkRequest = new PeriodicWorkRequest.Builder(OfferCleanupWorker.class, 2, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(offerCleanupWorkRequest);
    }
}
