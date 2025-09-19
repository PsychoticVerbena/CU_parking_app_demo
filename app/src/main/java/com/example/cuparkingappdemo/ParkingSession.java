package com.example.cuparkingappdemo;

import android.content.Context;
import android.content.SharedPreferences;

public class ParkingSession {
    private static ParkingSession instance;

    private String lot;
    private String spot;
    private long endTimeMillis; // absolute expiration time

    private ParkingSession() {
        lot = "";
        spot = "";
        endTimeMillis = 0;
    }

    public static ParkingSession getInstance() {
        if (instance == null) {
            instance = new ParkingSession();
        }
        return instance;
    }

    public void startSession(Context context, String lot, String spot, long durationMillis) {
        this.lot = lot;
        this.spot = lot + " - " + spot;
        this.endTimeMillis = System.currentTimeMillis() + durationMillis;

        saveToPrefs(context);
    }

    public void clearSession(Context context) {
        lot = "";
        spot = "";
        endTimeMillis = 0;

        SharedPreferences prefs = context.getSharedPreferences("parking_session", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    public boolean isActive() {
        return getTimeRemaining() > 0;
    }

    public String getLot() {
        return lot;
    }

    public String getSpot() {
        return spot;
    }

    public long getTimeRemaining() {
        long remaining = endTimeMillis - System.currentTimeMillis();
        return Math.max(remaining, 0);
    }

    // 🔽 persistence helpers
    private void saveToPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("parking_session", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("lot", lot)
                .putString("spot", spot)
                .putLong("endTimeMillis", endTimeMillis)
                .apply();
    }

    public void restoreFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("parking_session", Context.MODE_PRIVATE);
        lot = prefs.getString("lot", "");
        spot = prefs.getString("spot", "");
        endTimeMillis = prefs.getLong("endTimeMillis", 0);
    }

    public void endSession() {
        this.lot = "";
        this.spot = "";
        this.endTimeMillis = 0;
    }

}
