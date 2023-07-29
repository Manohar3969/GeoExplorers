package com.manohar3969.geoexplorers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DestinationPlanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_planner);
    }

    public void addDestination(View view){
        Intent intent = new Intent(DestinationPlanner.this,AddDestinations.class);
        startActivity(intent);
    }

    public void addDestinationType(View view){
        Intent intent = new Intent(DestinationPlanner.this,AddDestinationType.class);
        startActivity(intent);
    }

    public void viewDestinationsList(View view){
        Intent intent = new Intent(DestinationPlanner.this,ViewDestinationsList.class);
        startActivity(intent);
    }

    public void viewDestinationType(View view){
        Intent intent = new Intent(DestinationPlanner.this,ViewDestinationTypes.class);
        startActivity(intent);
    }

    public void destinationExplorer(View view){
        Intent intent = new Intent(DestinationPlanner.this,DestinationsExplorer.class);
        startActivity(intent);
    }
    public void userProfile(View view){
        Intent intent = new Intent(DestinationPlanner.this,UserProfile.class);
        startActivity(intent);
    }

    public void tripPlanner(View view){
        Intent intent = new Intent(DestinationPlanner.this,TripPlanner.class);
        startActivity(intent);
    }
}