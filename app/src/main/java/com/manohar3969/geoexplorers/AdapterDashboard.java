package com.manohar3969.geoexplorers;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<DashboardViewHolder> {

    private Context context;
    private List<Destinations> destinationsList;

    public AdapterDashboard(){

    }

    public AdapterDashboard(Context context, List<Destinations> destinationsList) {
        this.context = context;
        this.destinationsList = destinationsList;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard_destinations, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        holder.textViewDestinationName.setText(destinationsList.get(position).getDestName());
        Uri imageUri = Uri.parse(destinationsList.get(position).getDestImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationImage);
    }

    @Override
    public int getItemCount() {
        return destinationsList.size();
    }
}

class DashboardViewHolder extends RecyclerView.ViewHolder{
    TextView textViewDestinationName;
    ImageView imageViewDestinationImage;
    public DashboardViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDestinationName = itemView.findViewById(R.id.textViewDestinationName);
        imageViewDestinationImage = itemView.findViewById(R.id.imageViewDestinationImage);
    }
}
