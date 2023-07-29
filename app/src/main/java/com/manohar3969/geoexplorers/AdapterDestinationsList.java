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

public class AdapterDestinationsList extends RecyclerView.Adapter<DestinationsListViewHolder>{

    private Context context;
    private List<DestinationTypes> destinationTypeList;

    public AdapterDestinationsList(){

    }

    public AdapterDestinationsList(Context context, List<DestinationTypes> destinationTypeList) {
        this.context = context;
        this.destinationTypeList = destinationTypeList;
    }

    @NonNull
    @Override
    public DestinationsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_destination_type_list, parent, false);
        return new DestinationsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationsListViewHolder holder, int position) {
        String destType = destinationTypeList.get(position).getDestType();
        holder.textViewDestinationType.setText(destinationTypeList.get(position).getDestType());
        Uri imageUri = Uri.parse(destinationTypeList.get(position).getDestTypeImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationTypeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewDestinations.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("DestType", destType);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationTypeList.size();
    }
}

class DestinationsListViewHolder extends RecyclerView.ViewHolder{

    TextView textViewDestinationType;
    ImageView imageViewDestinationTypeImage;
    public DestinationsListViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDestinationType = itemView.findViewById(R.id.textViewDestinationType);
        imageViewDestinationTypeImage = itemView.findViewById(R.id.imageViewDestinationTypeImage);

    }
}