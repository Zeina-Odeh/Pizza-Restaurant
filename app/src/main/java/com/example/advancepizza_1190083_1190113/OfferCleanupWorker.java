package com.example.advancepizza_1190083_1190113;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OfferCleanupWorker extends Worker {

    private DataBaseHelper dbHelper;

    public OfferCleanupWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dbHelper = new DataBaseHelper(context); // Initialize dbHelper
    }

    @NonNull
    @Override
    public Result doWork() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String currentDateTime = sdf.format(new Date());
            Log.d("OfferCleanupWorker", "Current DateTime: " + currentDateTime);

            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM SpecialOffers WHERE endDateTime <= ?", new String[]{currentDateTime});

            Log.d("OfferCleanupWorker", "Number of rows returned by query: " + cursor.getCount());

            if (cursor.moveToFirst()) {
                do {
                    // Read and log each row
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String offerPeriod = cursor.getString(cursor.getColumnIndex("offer_period"));
                    @SuppressLint("Range") String endDateTime = cursor.getString(cursor.getColumnIndex("endDateTime"));

                    Log.d("OfferCleanupWorker", "Expired Offer Found - ID: " + id + ", Offer Period: " + offerPeriod + ", End DateTime: " + endDateTime);

                    // Compare endDateTime with currentDateTime
                    Date endDate = sdf.parse(endDateTime);
                    Date currentDate = sdf.parse(currentDateTime);

                    if (endDate != null && currentDate != null) {
                        if (endDate.compareTo(currentDate) <= 0) {
                            Log.d("OfferCleanupWorker", "Offer with ID " + id + " is expired.");
                        } else {
                            Log.d("OfferCleanupWorker", "Offer with ID " + id + " is still valid.");
                        }
                    } else {
                        Log.e("OfferCleanupWorker", "Error parsing dates.");
                    }

                    // Delete the expired offer (if deletion logic is implemented)
                    // Example: db.delete("SpecialOffers", "id=?", new String[]{String.valueOf(id)});
                } while (cursor.moveToNext());
            } else {
                Log.d("OfferCleanupWorker", "No expired offers to delete.");
            }

            return Result.success();
        } catch (Exception e) {
            Log.e("OfferCleanupWorker", "Error cleaning up offers: " + e.getMessage());
            return Result.failure();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
