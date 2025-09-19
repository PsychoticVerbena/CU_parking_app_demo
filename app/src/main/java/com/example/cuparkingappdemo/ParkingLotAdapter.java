package com.example.cuparkingappdemo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuparkingappdemo.LotDetailActivity;

import java.util.List;

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ParkingViewHolder> {

    private List<ParkingLot> parkingLots;

    public ParkingLotAdapter(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    @NonNull
    @Override
    public ParkingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parking_lot, parent, false);
        return new ParkingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingViewHolder holder, int position) {
        ParkingLot lot = parkingLots.get(position);
        holder.lotName.setText(lot.getName());
        holder.lotAvailability.setText(lot.getAvailableSpots() + " spots available");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LotDetailActivity.class);
            intent.putExtra("lotName", lot.getName());
            intent.putExtra("availableSpots", lot.getAvailableSpots());
            // If you have total spots separately, send it too
            intent.putExtra("totalSpots", 20);
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return parkingLots.size();
    }

    static class ParkingViewHolder extends RecyclerView.ViewHolder {
        TextView lotName, lotAvailability;

        public ParkingViewHolder(View itemView) {
            super(itemView);
            lotName = itemView.findViewById(R.id.lotName);
            lotAvailability = itemView.findViewById(R.id.lotAvailability);
        }
    }

}
