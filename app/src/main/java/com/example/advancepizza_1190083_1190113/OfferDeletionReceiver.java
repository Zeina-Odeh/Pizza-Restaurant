package com.example.advancepizza_1190083_1190113;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OfferDeletionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int offerId = intent.getIntExtra("offer_id", -1);
        if (offerId != -1) {
            DataBaseHelper dbHelper = new DataBaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Delete the special offer
            int rowsDeleted = db.delete("SpecialOffers", "id=?", new String[]{String.valueOf(offerId)});
            db.close();

            if (rowsDeleted > 0) {
                Log.d("OfferDeletionReceiver", "Special offer with ID " + offerId + " deleted.");
            } else {
                Log.d("OfferDeletionReceiver", "Failed to delete special offer with ID " + offerId);
            }
        }
    }
}
