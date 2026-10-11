package com.rymec.plantguardai;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private final ArrayList<PlantModel> plantList;

    public PlantAdapter(ArrayList<PlantModel> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plant, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PlantModel plant = plantList.get(position);

        holder.imgPlant.setImageResource(plant.getImage());
        holder.txtPlantName.setText(plant.getPlantName());
        holder.txtDiseaseCount.setText(plant.getDiseases());

        holder.itemView.setOnClickListener(v -> {

            Context context = v.getContext();

            Intent intent = new Intent(context, PlantDetailsActivity.class);

            intent.putExtra("plantName", plant.getPlantId());
            intent.putExtra("plantDiseases", plant.getDiseases());
            intent.putExtra("plantImage", plant.getImage());

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPlant;
        TextView txtPlantName;
        TextView txtDiseaseCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPlant = itemView.findViewById(R.id.imgPlant);
            txtPlantName = itemView.findViewById(R.id.txtPlantName);
            txtDiseaseCount = itemView.findViewById(R.id.txtDiseaseCount);
        }
    }
}