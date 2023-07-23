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

    public void createSoloRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,CreateSoloRoadMap.class);
        startActivity(intent);
    }

    public void createGroupRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,CreateGroupRoadMap.class);
        startActivity(intent);
    }

    public void viewSoloRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,AddDestinations.class);
        startActivity(intent);
    }

    public void viewGroupRoadMap(View view){
        Intent intent = new Intent(TripPlanner.this,ViewGroupRoadMap.class);
        startActivity(intent);
    }

    public void userProfile(View view){
        Intent intent = new Intent(TripPlanner.this,UserProfile.class);
        startActivity(intent);
    }
}