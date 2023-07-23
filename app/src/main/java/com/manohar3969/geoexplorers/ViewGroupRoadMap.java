package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupRoadMap extends AppCompatActivity {
    RecyclerView recyclerViewViewGroupRoadMapList;
    AdapterGroupTripRoadMapList adapterGroupTripRoadMapList;
    List<GroupTripRoadMap> groupTripRoadMapList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_road_map);

        recyclerViewViewGroupRoadMapList = findViewById(R.id.recyclerViewGroupTripRoadMaps);
        recyclerViewViewGroupRoadMapList.setLayoutManager(new LinearLayoutManager(this));

        groupTripRoadMapList = new ArrayList<>();

        getGroupTripRoadMapList();

    }

    public void getGroupTripRoadMapList(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        Query query = reference.orderByChild("RoadMapID");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    groupTripRoadMapList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        GroupTripRoadMap groupTripRoadMap = dataSnapshot.getValue(GroupTripRoadMap.class);
                        groupTripRoadMapList.add(groupTripRoadMap);
                    }
                    adapterGroupTripRoadMapList = new AdapterGroupTripRoadMapList(getBaseContext(),groupTripRoadMapList);
                    recyclerViewViewGroupRoadMapList.setAdapter(adapterGroupTripRoadMapList);
                }
                else {
                    Toast.makeText(getBaseContext(),"No Data Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),"Data Fetch Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }

}