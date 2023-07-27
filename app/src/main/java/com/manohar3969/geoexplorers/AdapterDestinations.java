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

public class AdapterDestinations extends RecyclerView.Adapter<DestinationsViewHolder> {

    private Context context;
    private List<Destinations> destinationsList;

    public AdapterDestinations(){

    }

    public AdapterDestinations(Context context, List<Destinations> destinationsList) {
        this.context = context;
        this.destinationsList = destinationsList;
    }

    @NonNull
    @Override
    public DestinationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_destinations_list, parent, false);
        return new DestinationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationsViewHolder holder, int position) {

        holder.textViewDestinationName.setText(destinationsList.get(position).getDestName());
        Uri imageUri = Uri.parse(destinationsList.get(position).getDestImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationImage);

    }

    @Override
    public int getItemCount() {
        return destinationsList.size();
    }
}

class DestinationsViewHolder extends RecyclerView.ViewHolder{

    TextView textViewDestinationName;
    ImageView imageViewDestinationImage;
    public DestinationsViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDestinationName = itemView.findViewById(R.id.textViewDestinationName);
        imageViewDestinationImage = itemView.findViewById(R.id.imageViewDestinationImage);

    }
}