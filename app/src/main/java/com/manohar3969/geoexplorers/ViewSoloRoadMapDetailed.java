package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
    String TripStartDate, TripEndDate, TripStartDest, TripEndDest, TripRoadMapID,TripEndDestImage;
    ImageView imageViewEndDestImage;
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
        TripEndDestImage = intent.getStringExtra("TripEndDestImage");

        TripRoadMapID = TripRoadMapID.replace("RoadMap","");

        textViewTripRoadMapID = findViewById(R.id.textView11);
        textViewTripStartDate = findViewById(R.id.textView13);
        textViewTripEndDate = findViewById(R.id.textView14);
        textViewTripStartDest = findViewById(R.id.textView15);
        textViewTripEndDest = findViewById(R.id.textView16);
        textViewTripDistance = findViewById(R.id.textView17);
        imageViewEndDestImage = findViewById(R.id.imageView16);

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

        Uri imageUri = Uri.parse(TripEndDestImage);
        Picasso.get().load(imageUri).centerCrop().fit().into(imageViewEndDestImage);
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

    public void updateSoloRoadMap(View view){
        Intent intent = new Intent(getBaseContext(),UpdateSoloRoadMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("TripStartDate",TripStartDate);
        intent.putExtra("TripEndDate",TripEndDate);
        intent.putExtra("TripStartDest",TripStartDest);
        intent.putExtra("TripEndDest",TripEndDest);
        intent.putExtra("TripRoadMapID","RoadMap"+TripRoadMapID);
        intent.putExtra("TripEndDestImage", TripEndDestImage);
        startActivity(intent);
    }

    public void deleteSoloTripRoadMap(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewSoloRoadMapDetailed.this);

        alertDialogBuilder.setTitle("Delete Solo Trip Road Map");
        alertDialogBuilder.setMessage("Do You Want to Delete this Road Map?")
                .setCancelable(true).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SoloRoadMap");
                        reference.orderByChild("RoadMapID").equalTo("RoadMap"+TripRoadMapID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {

                                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                                    {
                                        dataSnapshot.getRef().removeValue();
                                    }

                                    //Toast.makeText(getBaseContext(),"Total Snapshots"+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getBaseContext(),ViewSoloRoadMap.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"Road Map "+TripRoadMapID+" Not Found to Delete", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getBaseContext(),"Cancelled Sign Out", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
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