package com.manohar3969.geoexplorers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSoloRoadMapList extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<SoloRoadMap> soloRoadMapList;

    public AdapterSoloRoadMapList() {
    }

    public AdapterSoloRoadMapList(Context context, List<SoloRoadMap> soloRoadMapList) {
        this.context = context;
        this.soloRoadMapList = soloRoadMapList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_solo_road_map, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String tripStartDate = soloRoadMapList.get(position).getStartDate();
        String tripEndDate = soloRoadMapList.get(position).getEndDate();
        String tripStartDest = soloRoadMapList.get(position).getStartDest();
        String tripEndDest = soloRoadMapList.get(position).getEndDest();
        String tripRoadMapID = soloRoadMapList.get(position).getRoadMapID();
        String tripEndDestImage = soloRoadMapList.get(position).getEndDestImage();

        holder.textViewSoloTripStartDate.setText(soloRoadMapList.get(position).getStartDate());
        holder.textViewSoloTripStartDest.setText(soloRoadMapList.get(position).getStartDest());
        holder.textViewSoloTripEndDest.setText(soloRoadMapList.get(position).getEndDest());
        Uri imageUri = Uri.parse(soloRoadMapList.get(position).getEndDestImage());
        Picasso.get().load(imageUri).into(holder.imageViewSoloTripEndDestImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,ViewSoloRoadMapDetailed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TripStartDate",tripStartDate);
                intent.putExtra("TripEndDate",tripEndDate);
                intent.putExtra("TripStartDest",tripStartDest);
                intent.putExtra("TripEndDest",tripEndDest);
                intent.putExtra("TripRoadMapID",tripRoadMapID);
                intent.putExtra("TripEndDestImage", tripEndDestImage);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return soloRoadMapList.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView imageViewSoloTripEndDestImage;
    TextView textViewSoloTripStartDate,textViewSoloTripStartDest,textViewSoloTripEndDest;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewSoloTripEndDestImage = itemView.findViewById(R.id.imageViewSoloTripEndDestImage);
        textViewSoloTripStartDate = itemView.findViewById(R.id.textViewSoloTripStartDate);
        textViewSoloTripStartDest = itemView.findViewById(R.id.textViewSoloTripStartDest);
        textViewSoloTripEndDest = itemView.findViewById(R.id.textViewSoloTripEndDest);

    }
}
