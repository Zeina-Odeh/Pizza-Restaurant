package com.example.advancepizza_1190083_1190113.ui.home.specialoffers;

import android.content.Context;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.advancepizza_1190083_1190113.OfferCleanupWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SpecialOfferScheduler {

    public static void scheduleOfferEnd(Context context, String offerId, String endDateTimeString) {
        try {
            // Extract the date and time part from endDateTimeString
            String dateTimePart = endDateTimeString.replace("End Date and Time: ", "");

            // Parse the extracted date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Date endDateTime = dateFormat.parse(dateTimePart);

            // Schedule the worker to run at the specified endDateTime
            long delayInMillis = endDateTime.getTime() - System.currentTimeMillis();
            OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(OfferCleanupWorker.class)
                    .setInitialDelay(delayInMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
                    .addTag(offerId); // Optionally tag the work request with offerId

            OneTimeWorkRequest workRequest = builder.build();
            WorkManager.getInstance(context).enqueue(workRequest);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
