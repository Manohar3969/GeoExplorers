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
    private List<GroupTripRoadMap> groupTripRoadMapList;

    public AdapterDashboard(){

    }

    public AdapterDashboard(Context context, List<GroupTripRoadMap> groupTripRoadMapList) {
        this.context = context;
        this.groupTripRoadMapList = groupTripRoadMapList;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        String tripStartDate = groupTripRoadMapList.get(position).getStartDate();
        String tripEndDate = groupTripRoadMapList.get(position).getEndDate();
        String tripStartDest = groupTripRoadMapList.get(position).getStartDest();
        String tripEndDest = groupTripRoadMapList.get(position).getEndDest();
        String tripRoadMapID = groupTripRoadMapList.get(position).getRoadMapID();
        String tripTotalTravellers = groupTripRoadMapList.get(position).getTotalTravellers();
        String tripEndDestImage = groupTripRoadMapList.get(position).getEndDestImage();

        holder.textViewSoloTripStartDate.setText(groupTripRoadMapList.get(position).getStartDate());
        //holder.textViewSoloTripStartDest.setText(groupTripRoadMapList.get(position).getStartDest());
        holder.textViewSoloTripEndDest.setText(groupTripRoadMapList.get(position).getEndDest());
        Uri imageUri = Uri.parse(groupTripRoadMapList.get(position).getEndDestImage());
        Picasso.get().load(imageUri).into(holder.imageViewSoloTripEndDestImage);
    }

    @Override
    public int getItemCount() {
        return groupTripRoadMapList.size();
    }
}

class DashboardViewHolder extends RecyclerView.ViewHolder{
    ImageView imageViewSoloTripEndDestImage;
    TextView textViewSoloTripStartDate,textViewSoloTripStartDest,textViewSoloTripEndDest;
    public DashboardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewSoloTripEndDestImage = itemView.findViewById(R.id.imageViewSoloTripEndDestImage);
        textViewSoloTripStartDate = itemView.findViewById(R.id.textViewSoloTripStartDate);
        //textViewSoloTripStartDest = itemView.findViewById(R.id.textViewSoloTripStartDest);
        textViewSoloTripEndDest = itemView.findViewById(R.id.textViewSoloTripEndDest);
    }
}
