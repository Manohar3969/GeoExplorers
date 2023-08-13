package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DestinationsList extends AppCompatActivity {
    RecyclerView recyclerViewViewDestinations;
    AdapterDestinationsListDetails adapterDestinationsListDetails;
    List<Destinations> destinationsList;
    String destinationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations_list);
        Intent intent = getIntent();
        destinationType = intent.getStringExtra("DestType");

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
                    adapterDestinationsListDetails = new AdapterDestinationsListDetails(getBaseContext(),destinationsList);
                    recyclerViewViewDestinations.setAdapter(adapterDestinationsListDetails);
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

    // Bottom Navigation Bar Functions
    public void dashBoard(View view){
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void userProfile(View view){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
        finish();
    }

    public void destinationExplorer(View view){
        Intent intent = new Intent(this,DestinationsExplorer.class);
        startActivity(intent);
        finish();
    }

    public void tripPlanner(View view){
        Intent intent = new Intent(this,TripPlanner.class);
        startActivity(intent);
        finish();
    }

    public void searchTrips(View view){
        Intent intent = new Intent(this,SearchTrips.class);
        startActivity(intent);
        finish();
    }
}