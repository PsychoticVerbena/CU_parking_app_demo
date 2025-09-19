package com.example.cuparkingappdemo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LotDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_detail);

        // Get data from intent
        String lotName = getIntent().getStringExtra("lotName");
        int totalSpots = getIntent().getIntExtra("totalSpots", 0);
        int availableSpots = getIntent().getIntExtra("availableSpots", 0);

        // Set lot name and availability at the top
        TextView tvLotName = findViewById(R.id.lotName);
        TextView tvAvailability = findViewById(R.id.availability);
        tvLotName.setText(lotName != null ? lotName : "Unknown Lot");
        tvAvailability.setText(availableSpots + " / " + totalSpots + " spots available");

        // Populate the spot list
        LinearLayout spotList = findViewById(R.id.spotList);
        spotList.removeAllViews(); // Clear any previous views
        for (int i = 1; i <= totalSpots; i++) {
            if (i <= availableSpots) {
                TextView spotTv = new TextView(this);
                spotTv.setText("A" + i); // Example naming
                spotTv.setTextSize(16f);
                spotTv.setPadding(4, 4, 4, 4);
                spotList.addView(spotTv);
            }
        }

        // Bottom icons
        ImageView mapIcon = findViewById(R.id.nav_map);
        ImageView mySpaceIcon = findViewById(R.id.nav_myspace);

        mapIcon.setOnClickListener(v -> {
            String url = "https://www.google.com/maps/search/?api=1&query=Clemson+University+Parking";
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(mapIntent);
        });

        mySpaceIcon.setOnClickListener(v -> {
            Intent intent = new Intent(LotDetailActivity.this, MySpaceActivity.class);
            intent.putExtra("lotName", lotName);
            intent.putExtra("spot", "A17"); // hardcoded for now, later you can pass whichever was clicked
            startActivity(intent);
        });
    }
}

