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

public class AdapterSearchTrip extends RecyclerView.Adapter<SearchTripViewHolder> {

    private Context context;
    private List<GroupTripRoadMap> groupTripRoadMapList;

    public AdapterSearchTrip(){

    }

    public AdapterSearchTrip(Context context, List<GroupTripRoadMap> groupTripRoadMapList) {
        this.context = context;
        this.groupTripRoadMapList = groupTripRoadMapList;
    }

    @NonNull
    @Override
    public SearchTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group_trip_road_map, parent, false);
        return new SearchTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTripViewHolder holder, int position) {

        String tripStartDate = groupTripRoadMapList.get(position).getStartDate();
        String tripEndDate = groupTripRoadMapList.get(position).getEndDate();
        String tripStartDest = groupTripRoadMapList.get(position).getStartDest();
        String tripEndDest = groupTripRoadMapList.get(position).getEndDest();
        String tripRoadMapID = groupTripRoadMapList.get(position).getRoadMapID();
        String tripTotalTravellers = groupTripRoadMapList.get(position).getTotalTravellers();
        String tripEndDestImage = groupTripRoadMapList.get(position).getEndDestImage();

        holder.textViewSoloTripStartDate.setText(groupTripRoadMapList.get(position).getStartDate());
        holder.textViewSoloTripStartDest.setText(groupTripRoadMapList.get(position).getStartDest());
        holder.textViewSoloTripEndDest.setText(groupTripRoadMapList.get(position).getEndDest());
        Uri imageUri = Uri.parse(groupTripRoadMapList.get(position).getEndDestImage());
        Picasso.get().load(imageUri).into(holder.imageViewSoloTripEndDestImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SearchTripDetailed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TripStartDate",tripStartDate);
                intent.putExtra("TripEndDate",tripEndDate);
                intent.putExtra("TripStartDest",tripStartDest);
                intent.putExtra("TripEndDest",tripEndDest);
                intent.putExtra("TripRoadMapID",tripRoadMapID);
                intent.putExtra("TripTotalTravellers", tripTotalTravellers);
                intent.putExtra("TripEndDestImage", tripEndDestImage);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupTripRoadMapList.size();
    }

}

class SearchTripViewHolder extends RecyclerView.ViewHolder{

    ImageView imageViewSoloTripEndDestImage;
    TextView textViewSoloTripStartDate,textViewSoloTripStartDest,textViewSoloTripEndDest;

    public SearchTripViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewSoloTripEndDestImage = itemView.findViewById(R.id.imageViewSoloTripEndDestImage);
        textViewSoloTripStartDate = itemView.findViewById(R.id.textViewSoloTripStartDate);
        textViewSoloTripStartDest = itemView.findViewById(R.id.textViewSoloTripStartDest);
        textViewSoloTripEndDest = itemView.findViewById(R.id.textViewSoloTripEndDest);

    }
}
