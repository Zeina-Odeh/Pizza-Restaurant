package com.example.advancepizza_1190083_1190113;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "OrderStatus";
    private static final String KEY_ORDER_STATUS = "orderStatus";

    public static void setOrderStatus(Context context, boolean ordered) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_ORDER_STATUS, ordered);
        editor.apply();
    }

    public static boolean getOrderStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_ORDER_STATUS, false); // Default value is false (not ordered)
    }
}

