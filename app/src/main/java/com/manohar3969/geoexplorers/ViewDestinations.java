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

public class ViewDestinations extends AppCompatActivity {

    RecyclerView recyclerViewViewDestinations;
    AdapterDestinations adapterDestinations;
    List<Destinations> destinationsList;

    String destinationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_destinations);

        destinationType = "Holy Place";

        recyclerViewViewDestinations = findViewById(R.id.recyclerViewViewDestinations);
        recyclerViewViewDestinations.setLayoutManager(new LinearLayoutManager(this));

        destinationsList = new ArrayList<>();

        getDestinationsList();

    }

    public void getDestinationsList(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destinations");
        Query query = reference.child(destinationType).orderByChild("DestID");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    destinationsList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Destinations destinations = dataSnapshot.getValue(Destinations.class);
                        destinationsList.add(destinations);
                    }
                    adapterDestinations = new AdapterDestinations(getBaseContext(),destinationsList);
                    recyclerViewViewDestinations.setAdapter(adapterDestinations);
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