package com.manohar3969.geoexplorers;

import android.content.Context;
import android.content.Intent;
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
import java.util.Locale;

public class AdapterDestinationsListDetails extends RecyclerView.Adapter<DestinationsListDetailsViewHolder> {

    private Context context;
    private List<Destinations> destinationsList;

    public AdapterDestinationsListDetails(){

    }

    public AdapterDestinationsListDetails(Context context, List<Destinations> destinationsList) {
        this.context = context;
        this.destinationsList = destinationsList;
    }

    @NonNull
    @Override
    public DestinationsListDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_destinations_list, parent, false);
        return new DestinationsListDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationsListDetailsViewHolder holder, int position) {
        String destinationName = destinationsList.get(position).getDestName();
        holder.textViewDestinationName.setText(destinationsList.get(position).getDestName());
        Uri imageUri = Uri.parse(destinationsList.get(position).getDestImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationImage);
        holder.textViewGetDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(destinationName));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationsList.size();
    }
}

class  DestinationsListDetailsViewHolder extends RecyclerView.ViewHolder {
    TextView textViewDestinationName, textViewGetDirections;
    ImageView imageViewDestinationImage;
    public DestinationsListDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDestinationName = itemView.findViewById(R.id.textViewDestinationName);
        textViewGetDirections = itemView.findViewById(R.id.textViewGetDirections);
        imageViewDestinationImage = itemView.findViewById(R.id.imageViewDestinationImage);
    }
}
