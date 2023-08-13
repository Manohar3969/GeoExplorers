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

public class ViewDestinationTypes extends AppCompatActivity {

    RecyclerView recyclerViewViewDestinationType;
    AdapterDestinationTypes adapterDestinationTypes;
    List<DestinationTypes> destinationTypesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_destination_types);

        recyclerViewViewDestinationType = findViewById(R.id.recyclerViewViewDestinationTypes);
        recyclerViewViewDestinationType.setLayoutManager(new LinearLayoutManager(this));

        destinationTypesList = new ArrayList<>();
        getDestinationTypesList();
    }

    public void getDestinationTypesList(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DestinationTypes");
        Query query = reference.orderByChild("DestType");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    destinationTypesList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        DestinationTypes destinationTypes = dataSnapshot.getValue(DestinationTypes.class);
                        destinationTypesList.add(destinationTypes);
                    }
                    adapterDestinationTypes = new AdapterDestinationTypes(getBaseContext(),destinationTypesList);
                    recyclerViewViewDestinationType.setAdapter(adapterDestinationTypes);
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