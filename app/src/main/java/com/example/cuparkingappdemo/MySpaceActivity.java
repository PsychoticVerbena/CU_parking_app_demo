package com.example.cuparkingappdemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MySpaceActivity extends AppCompatActivity {

    private TextView spotText, timerText;
    private NumberPicker hourPicker;
    private Button startButton, endButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);

        // Views
        spotText = findViewById(R.id.spotText);
        timerText = findViewById(R.id.timerText);
        hourPicker = findViewById(R.id.hourPicker);
        startButton = findViewById(R.id.startButton);
        endButton = findViewById(R.id.endButton);

        // Get extras
        String lotName = getIntent().getStringExtra("lotName");
        String spot = getIntent().getStringExtra("spot");

        if (lotName == null) lotName = "Unknown Lot";
        if (spot == null) spot = "A17";

        // NumberPicker
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(6);
        hourPicker.setValue(1);

        // Restore session from prefs
        ParkingSession session = ParkingSession.getInstance();
        session.restoreFromPrefs(this);

        if (session.isActive()) {
            spotText.setText(session.getSpot());
            startTimerRemaining(session.getTimeRemaining());
        } else {
            spotText.setText(lotName + " - " + spot);
        }

        // Start button
        String finalLotName = lotName;
        String finalSpot = spot;

        startButton.setOnClickListener(v -> {
            int hours = hourPicker.getValue();
            long durationMillis = hours * 60 * 60 * 1000L;

            ParkingSession.getInstance().startSession(this, finalLotName, finalSpot, durationMillis);

            spotText.setText(ParkingSession.getInstance().getSpot());
            startTimerRemaining(durationMillis);
        });

        endButton.setOnClickListener(v -> {
            // Stop the timer
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }

            // End the session
            ParkingSession.getInstance().endSession();

            // Update UI
            timerText.setText("Session ended!");
            spotText.setText("No active session");
        });
    }

    private void startTimerRemaining(long millis) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int h = (int) (millisUntilFinished / (1000 * 60 * 60));
                int m = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int s = (int) ((millisUntilFinished / 1000) % 60);
                timerText.setText(String.format("%02d:%02d:%02d", h, m, s));
            }

            @Override
            public void onFinish() {
                timerText.setText("Time expired!");
                ParkingSession.getInstance().clearSession(MySpaceActivity.this);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
