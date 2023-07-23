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

import java.util.List;

public class AdapterGroupTripRoadMapList extends RecyclerView.Adapter<GroupTripViewHolder>{

    private Context context;
    private List<GroupTripRoadMap> groupTripRoadMapList;

    public AdapterGroupTripRoadMapList(){

    }

    public AdapterGroupTripRoadMapList(Context context, List<GroupTripRoadMap> groupTripRoadMapList) {
        this.context = context;
        this.groupTripRoadMapList = groupTripRoadMapList;
    }

    @NonNull
    @Override
    public GroupTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group_trip_road_map, parent, false);
        return new GroupTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupTripViewHolder holder, int position) {
        String RoadMapID = groupTripRoadMapList.get(position).getEndDest();
        holder.textViewSoloTripStartDate.setText(groupTripRoadMapList.get(position).getStartDate());
        holder.textViewSoloTripStartDest.setText(groupTripRoadMapList.get(position).getStartDest());
        holder.textViewSoloTripEndDest.setText(groupTripRoadMapList.get(position).getEndDest());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"RoadMapID = "+RoadMapID,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupTripRoadMapList.size();
    }
}

class GroupTripViewHolder extends RecyclerView.ViewHolder{

    ImageView imageViewSoloTripEndDestImage;
    TextView textViewSoloTripStartDate,textViewSoloTripStartDest,textViewSoloTripEndDest;

    public GroupTripViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewSoloTripEndDestImage = itemView.findViewById(R.id.imageViewSoloTripEndDestImage);
        textViewSoloTripStartDate = itemView.findViewById(R.id.textViewSoloTripStartDate);
        textViewSoloTripStartDest = itemView.findViewById(R.id.textViewSoloTripStartDest);
        textViewSoloTripEndDest = itemView.findViewById(R.id.textViewSoloTripEndDest);

    }
}