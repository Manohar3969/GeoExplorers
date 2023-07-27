package com.manohar3969.geoexplorers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;

public class ViewSoloRoadMapDetailed extends AppCompatActivity {
    String TripStartDate, TripEndDate, TripStartDest, TripEndDest, TripRoadMapID;

    TextView textViewTripStartDate, textViewTripEndDate, textViewTripStartDest, textViewTripEndDest, textViewTripRoadMapID, textViewTripDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_solo_road_map_detailed);

        Intent intent = getIntent();

        TripStartDate = intent.getStringExtra("TripStartDate");
        TripEndDate = intent.getStringExtra("TripEndDate");
        TripStartDest = intent.getStringExtra("TripStartDest");
        TripEndDest = intent.getStringExtra("TripEndDest");
        TripRoadMapID = intent.getStringExtra("TripRoadMapID");

        TripRoadMapID = TripRoadMapID.replace("RoadMap","");

        textViewTripRoadMapID = findViewById(R.id.textView11);
        textViewTripStartDate = findViewById(R.id.textView13);
        textViewTripEndDate = findViewById(R.id.textView14);
        textViewTripStartDest = findViewById(R.id.textView15);
        textViewTripEndDest = findViewById(R.id.textView16);
        textViewTripDistance = findViewById(R.id.textView17);

        getDistanceOfDestination();

    }

    public void getDistanceOfDestination(){
        double[] startDest = getDistance(TripStartDest);
        double[] endDest = getDistance(TripEndDest);

        Location startPoint = new Location("Location 1");
        startPoint.setLatitude(startDest[0]);
        startPoint.setLongitude(startDest[1]);

        Location endPoint = new Location("Location 2");
        endPoint.setLatitude(endDest[0]);
        endPoint.setLongitude(endDest[1]);

        double distance = startPoint.distanceTo(endPoint);
        distance = distance/1000;

        textViewTripRoadMapID.setText(TripRoadMapID);
        textViewTripStartDest.setText(TripStartDest);
        textViewTripEndDest.setText(TripEndDest);
        textViewTripStartDate.setText(TripStartDate);
        textViewTripEndDate.setText(TripEndDate);
        textViewTripDistance.setText(Math.round(distance)+" Kms");


    }

    public double[] getDistance(String location){
        double[] locDetails = new double[2];
        try{
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            List addressList = geocoder.getFromLocationName(location, 1);

            if(addressList !=null && addressList.size()>0){
                Address address = (Address) addressList.get(0);
                locDetails[0] = address.getLatitude();
                locDetails[1] = address.getLongitude();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Location Details Not Fetched", Toast.LENGTH_SHORT).show();
        }
        return locDetails;
    }




}