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

public class ViewSoloRoadMap extends AppCompatActivity {

    RecyclerView recyclerViewViewSoloRoadMapList;
    AdapterSoloRoadMapList adapterSoloRoadMapList;
    List<SoloRoadMap> soloRoadMapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_solo_road_map);

        recyclerViewViewSoloRoadMapList = findViewById(R.id.recyclerViewSoloRoadMaps);
        recyclerViewViewSoloRoadMapList.setLayoutManager(new LinearLayoutManager(this));

        soloRoadMapList = new ArrayList<>();

        getSoloRoadMapList();
    }

    public void getSoloRoadMapList(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SoloRoadMap");
        Query query = reference.orderByChild("RoadMapID");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    soloRoadMapList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        SoloRoadMap soloRoadMap = dataSnapshot.getValue(SoloRoadMap.class);
                        soloRoadMapList.add(soloRoadMap);
                    }
                    adapterSoloRoadMapList = new AdapterSoloRoadMapList(getBaseContext(),soloRoadMapList);
                    recyclerViewViewSoloRoadMapList.setAdapter(adapterSoloRoadMapList);
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