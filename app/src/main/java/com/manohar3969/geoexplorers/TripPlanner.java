package com.manohar3969.geoexplorers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TripPlanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);
    }

    // Activity Functions

    public void createSoloRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,CreateSoloRoadMap.class);
        startActivity(intent);
    }

    public void createGroupRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,CreateGroupRoadMap.class);
        startActivity(intent);
    }

    public void viewSoloRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,ViewSoloRoadMap.class);
        startActivity(intent);
    }

    public void viewGroupRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,ViewGroupRoadMap.class);
        startActivity(intent);
    }
    public void destinationPlanner(View view){
        Intent intent = new Intent(TripPlanner.this,DestinationPlanner.class);
        startActivity(intent);
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