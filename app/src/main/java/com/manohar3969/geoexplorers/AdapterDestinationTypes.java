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

public class AdapterDestinationTypes extends RecyclerView.Adapter<DestinationTypesViewHolder>{

    private Context context;
    private List<DestinationTypes> destinationTypeList;

    public AdapterDestinationTypes(){

    }

    public AdapterDestinationTypes(Context context, List<DestinationTypes> destinationTypeList) {
        this.context = context;
        this.destinationTypeList = destinationTypeList;
    }

    @NonNull
    @Override
    public DestinationTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_destination_type_list, parent, false);
        return new DestinationTypesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationTypesViewHolder holder, int position) {
        holder.textViewDestinationType.setText(destinationTypeList.get(position).getDestType());
        Uri imageUri = Uri.parse(destinationTypeList.get(position).getDestTypeImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationTypeImage);
    }

    @Override
    public int getItemCount() {
        return destinationTypeList.size();
    }
}

class DestinationTypesViewHolder extends RecyclerView.ViewHolder{

    TextView textViewDestinationType;
    ImageView imageViewDestinationTypeImage;
    public DestinationTypesViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDestinationType = itemView.findViewById(R.id.textViewDestinationType);
        imageViewDestinationTypeImage = itemView.findViewById(R.id.imageViewDestinationTypeImage);

    }
}
