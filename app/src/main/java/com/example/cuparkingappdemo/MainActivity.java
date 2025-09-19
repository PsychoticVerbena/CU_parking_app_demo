package com.example.cuparkingappdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView sessionStatus;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_map) {
                String url = "https://www.google.com/maps/search/?api=1&query=Clemson+University+Parking";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

            } else if (id == R.id.nav_myspace) {
                startActivity(new Intent(MainActivity.this, MySpaceActivity.class));

            } else if (id == R.id.nav_help) {
                String url = "https://www.clemson.edu/campus-life/parking/";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recyclerViewParking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ParkingLotAdapter adapter = new ParkingLotAdapter(getParkingLots());
        recyclerView.setAdapter(adapter);

        // Session status TextView
        sessionStatus = findViewById(R.id.sessionStatus);

        // Runnable to update countdown every second
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                ParkingSession session = ParkingSession.getInstance();
                long remaining = session.getTimeRemaining();

                if (remaining > 0) {
                    int h = (int) (remaining / (1000 * 60 * 60));
                    int m = (int) ((remaining / (1000 * 60)) % 60);
                    int s = (int) ((remaining / 1000) % 60);

                    sessionStatus.setText(
                            session.getSpot() + " " + String.format("%02d:%02d:%02d left", h, m, s)
                    );

                    handler.postDelayed(this, 1000);
                } else {
                    sessionStatus.setText("NO ACTIVE SESSION"); // ✅ placeholder instead of blank
                    handler.postDelayed(this, 1000); // keep refreshing
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Immediately update once
        updateRunnable.run();
        // Then keep updating every second
        handler.post(updateRunnable);
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRunnable);
    }

    private List<ParkingLot> getParkingLots() {
        List<ParkingLot> lots = new ArrayList<>();
        lots.add(new ParkingLot("Brooks Lot", 18));
        lots.add(new ParkingLot("East Campus Lot", 5));
        lots.add(new ParkingLot("West Deck", 12));
        lots.add(new ParkingLot("Ridge Lot", 20));
        lots.add(new ParkingLot("Stadium Deck", 8));
        lots.add(new ParkingLot("Hickory Lot", 15));
        lots.add(new ParkingLot("Sikes Lot", 10));
        lots.add(new ParkingLot("All Lots", 88));
        return lots;
    }
}
