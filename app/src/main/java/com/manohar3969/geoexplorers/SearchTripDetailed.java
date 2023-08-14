package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchTripDetailed extends AppCompatActivity {

    String TripStartDate, TripEndDate, TripStartDest, TripEndDest, TripRoadMapID, TripTotalTravellers,TripEndDestImage;
    ImageView imageViewEndDestImage;
    TextView textViewTripStartDate, textViewTripEndDate, textViewTripStartDest, textViewTripEndDest, textViewTripRoadMapID, textViewTripDistance, textViewTripTotalTravellers;
    ArrayList<Users> usersArrayList = new ArrayList<>();

    CardView cardViewJoinTrip, cardViewWithDrawTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_detailed);

        Intent intent = getIntent();

        TripStartDate = intent.getStringExtra("TripStartDate");
        TripEndDate = intent.getStringExtra("TripEndDate");
        TripStartDest = intent.getStringExtra("TripStartDest");
        TripEndDest = intent.getStringExtra("TripEndDest");
        TripRoadMapID = intent.getStringExtra("TripRoadMapID");
        TripTotalTravellers  =intent.getStringExtra("TripTotalTravellers");
        TripEndDestImage = intent.getStringExtra("TripEndDestImage");

        TripRoadMapID = TripRoadMapID.replace("RoadMap","");

        textViewTripRoadMapID = findViewById(R.id.textView11);
        textViewTripStartDate = findViewById(R.id.textView13);
        textViewTripEndDate = findViewById(R.id.textView14);
        textViewTripStartDest = findViewById(R.id.textView15);
        textViewTripEndDest = findViewById(R.id.textView16);
        textViewTripDistance = findViewById(R.id.textView17);
        textViewTripTotalTravellers = findViewById(R.id.textView18);
        imageViewEndDestImage = findViewById(R.id.imageView16);

        cardViewJoinTrip = findViewById(R.id.cardViewJoinTrip);
        cardViewWithDrawTrip = findViewById(R.id.cardViewWithdrawTrip);

        getDistanceOfDestination();
        getUserDetails();
        checkTripRegistration();

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
        textViewTripTotalTravellers.setText(TripTotalTravellers);
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

    public void updateGroupTripRoadMap(View view){
        Intent intent = new Intent(getBaseContext(),UpdateGroupTripRoadMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("TripStartDate",TripStartDate);
        intent.putExtra("TripEndDate",TripEndDate);
        intent.putExtra("TripStartDest",TripStartDest);
        intent.putExtra("TripEndDest",TripEndDest);
        intent.putExtra("TripRoadMapID","RoadMap"+TripRoadMapID);
        intent.putExtra("TripTotalTravellers", TripTotalTravellers);
        intent.putExtra("TripEndDestImage", TripEndDestImage);
        startActivity(intent);
    }

    public void deleteGroupTripRoadMap(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchTripDetailed.this);

        alertDialogBuilder.setTitle("Delete Group Trip Road Map");
        alertDialogBuilder.setMessage("Do You Want to Delete this Road Map?")
                .setCancelable(true).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
                        reference.orderByChild("RoadMapID").equalTo("RoadMap"+TripRoadMapID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                                    {
                                        dataSnapshot.getRef().removeValue();

                                    }

                                    //Toast.makeText(getBaseContext(),"Total Children"+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getBaseContext(),ViewGroupRoadMap.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"RoadMap "+TripRoadMapID+" Not Found to Delete", Toast.LENGTH_SHORT).show();
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
                        usersArrayList.add(users);
                    }
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

    public void joinTrip(View view){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        DatabaseReference reference1 = reference.child("RoadMap"+TripRoadMapID).child("Travellers").child(usersArrayList.get(0).getUserID());

        reference1.child("UserID").setValue(usersArrayList.get(0).getUserID());
        reference1.child("UserName").setValue(usersArrayList.get(0).getUserName());
        reference1.child("UserEmailID").setValue(usersArrayList.get(0).getUserEmailID());

        checkTripRegistration();
    }
    public void withDrawTrip(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchTripDetailed.this);

        alertDialogBuilder.setTitle("Withdraw From Trip");
        alertDialogBuilder.setMessage("Do You Want to Withdraw from Trip?")
                .setCancelable(true).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
                        Query query = reference.child("RoadMap"+TripRoadMapID).child("Travellers").orderByChild("UserEmailID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                                    {
                                        dataSnapshot.getRef().removeValue();
                                    }
                                    checkTripRegistration();
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


    public void checkTripRegistration(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        Query query = reference.child("RoadMap"+TripRoadMapID).child("Travellers").orderByChild("UserEmailID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    cardViewWithDrawTrip.setVisibility(View.VISIBLE);
                    cardViewJoinTrip.setVisibility(View.GONE);
                }
                else {
                    //Log.v("Error","Data Not Found");
                    cardViewWithDrawTrip.setVisibility(View.GONE);
                    cardViewJoinTrip.setVisibility(View.VISIBLE);
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