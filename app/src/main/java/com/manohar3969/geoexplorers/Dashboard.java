package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerViewViewGroupRoadMapList;
    AdapterDashboard adapterDashboard;
    List<GroupTripRoadMap> groupTripRoadMapList;
    ArrayList<String> usersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerViewViewGroupRoadMapList = findViewById(R.id.recyclerViewGroupTripRoadMaps);
        recyclerViewViewGroupRoadMapList.setLayoutManager(new LinearLayoutManager(this));

        groupTripRoadMapList = new ArrayList<>();

        getUserDetails();

    }

    public void getGroupTripRoadMapList(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        Query query = reference.orderByChild("GroupTripID");

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
                    adapterDashboard = new AdapterDashboard(getBaseContext(),groupTripRoadMapList);
                    recyclerViewViewGroupRoadMapList.setAdapter(adapterDashboard);
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

    public void getUserDetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = reference.orderByChild("UserEmailID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    usersArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Users users = dataSnapshot.getValue(Users.class);
                        usersArrayList.add(users.getUserID());
                    }
                    getGroupTripRoadMapList();
                }
                else {
                    Log.v("Error","Data Not Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Bottom Navigation Bar Functions
    public void dashBoard(View view){
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
        //finish();
    }

    public void userProfile(View view){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
        //finish();
    }

    public void destinationExplorer(View view){
        Intent intent = new Intent(this,DestinationsExplorer.class);
        startActivity(intent);
        //finish();
    }

    public void tripPlanner(View view){
        Intent intent = new Intent(this,TripPlanner.class);
        startActivity(intent);
        //finish();
    }

    public void searchTrips(View view){
        Intent intent = new Intent(this,SearchTrips.class);
        startActivity(intent);
        //finish();
    }
}