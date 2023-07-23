package com.manohar3969.geoexplorers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        String RoadMapID = soloRoadMapList.get(position).getEndDest();
        holder.textViewSoloTripStartDate.setText(soloRoadMapList.get(position).getStartDate());
        holder.textViewSoloTripStartDest.setText(soloRoadMapList.get(position).getStartDest());
        holder.textViewSoloTripEndDest.setText(soloRoadMapList.get(position).getEndDest());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"RoadMapID = "+RoadMapID,Toast.LENGTH_SHORT).show();
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
