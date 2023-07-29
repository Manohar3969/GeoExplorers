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

public class AdapterDestinationsExplorer extends RecyclerView.Adapter<DestinationsExplorerViewHolder>{

    private Context context;
    private List<DestinationTypes> destinationTypeList;

    public AdapterDestinationsExplorer(){

    }

    public AdapterDestinationsExplorer(Context context, List<DestinationTypes> destinationTypeList) {
        this.context = context;
        this.destinationTypeList = destinationTypeList;
    }

    @NonNull
    @Override
    public DestinationsExplorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_destination_type_list, parent, false);
        return new DestinationsExplorerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationsExplorerViewHolder holder, int position) {
        String destType = destinationTypeList.get(position).getDestType();
        holder.textViewDestinationType.setText(destinationTypeList.get(position).getDestType());
        Uri imageUri = Uri.parse(destinationTypeList.get(position).getDestTypeImage());
        Picasso.get().load(imageUri).centerCrop().fit().into(holder.imageViewDestinationTypeImage);
    }

    @Override
    public int getItemCount() {
        return destinationTypeList.size();
    }
}

class DestinationsExplorerViewHolder extends RecyclerView.ViewHolder{

    TextView textViewDestinationType;
    ImageView imageViewDestinationTypeImage;
    public DestinationsExplorerViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDestinationType = itemView.findViewById(R.id.textViewDestinationType);
        imageViewDestinationTypeImage = itemView.findViewById(R.id.imageViewDestinationTypeImage);

    }
}