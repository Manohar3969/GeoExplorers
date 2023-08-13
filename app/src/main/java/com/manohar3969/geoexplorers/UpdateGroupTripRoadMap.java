package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UpdateGroupTripRoadMap extends AppCompatActivity {

    Calendar myCalendar;
    EditText editTextStartDate,editTextEndDate;
    EditText editTextStartDest, editTextEndDest, editTextTotalTravellers;
    ImageView imageViewStartDate, imageViewEndDate;
    List<String> destinationsNames, endDestinationsImages;
    AutoCompleteTextView autoCompleteTextViewStartDest, autoCompleteTextViewEndDest;
    ArrayList<Users> usersArrayList = new ArrayList<>();
    int position;

    String TripStartDate, TripEndDate, TripStartDest, TripEndDest, TripRoadMapID, TripTotalTravellers,TripEndDestImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group_trip_road_map);

        Intent intent = getIntent();

        TripStartDate = intent.getStringExtra("TripStartDate");
        TripEndDate = intent.getStringExtra("TripEndDate");
        TripStartDest = intent.getStringExtra("TripStartDest");
        TripEndDest = intent.getStringExtra("TripEndDest");
        TripRoadMapID = intent.getStringExtra("TripRoadMapID");
        TripTotalTravellers  =intent.getStringExtra("TripTotalTravellers");
        TripEndDestImage = intent.getStringExtra("TripEndDestImage");

        myCalendar = Calendar.getInstance();

        editTextStartDate =findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);

        editTextStartDest = findViewById(R.id.editTextStartDest);
        editTextEndDest= findViewById(R.id.editTextEndDest);
        editTextTotalTravellers = findViewById(R.id.editTextTotalTravellers);

        imageViewStartDate = findViewById(R.id.imageView13);
        imageViewEndDate = findViewById(R.id.imageView14);

        autoCompleteTextViewStartDest = findViewById(R.id.autoCompleteTextViewStartDest);
        autoCompleteTextViewEndDest = findViewById(R.id.autoCompleteTextViewEndDest);

        destinationsNames = new ArrayList<>();
        endDestinationsImages = new ArrayList<>();

        getUserDetails();
        getDestinationDetails();

        editTextStartDate.setText(TripStartDate);
        editTextEndDate.setText(TripEndDate);
        autoCompleteTextViewStartDest.setText(TripStartDest);
        autoCompleteTextViewEndDest.setText(TripEndDest);
        editTextTotalTravellers.setText(TripTotalTravellers);

        imageViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateGroupTripRoadMap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //Toast.makeText(getBaseContext(),"Date Selected: "+ dayOfMonth+":"+month+":"+year,Toast.LENGTH_SHORT).show();
                        editTextStartDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        imageViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateGroupTripRoadMap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //Toast.makeText(getBaseContext(),"Date Selected: "+ dayOfMonth+":"+month+":"+year,Toast.LENGTH_SHORT).show();
                        editTextEndDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

    }

    public void dataValidations(View view){
        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), editTextStartDate.getText().toString(), editTextEndDate.getText().toString());
        if(autoCompleteTextViewStartDest.getText().toString().isEmpty()){
            autoCompleteTextViewStartDest.setError("Mandatory Field");
        } else if (autoCompleteTextViewEndDest.getText().toString().isEmpty()) {
            autoCompleteTextViewEndDest.setError("Mandatory Field");
        } else if (destinationsNames.contains(autoCompleteTextViewStartDest.getText().toString()) == false) {
            autoCompleteTextViewStartDest.setError("Enter Destination from Drop Down");
        } else if (destinationsNames.contains(autoCompleteTextViewEndDest.getText().toString())==false) {
            autoCompleteTextViewEndDest.setError("Enter Destination from Drop Down");
        } else if (autoCompleteTextViewStartDest.getText().toString().equals(autoCompleteTextViewEndDest.getText().toString())) {
            autoCompleteTextViewEndDest.setError("Start and End Destination Cannot Be Same");
        } else if (editTextTotalTravellers.getText().toString().isEmpty()) {
            editTextTotalTravellers.setError("Mandatory Field");
        }else if (dateDifference<0) {
            editTextEndDate.setError("End Date Should be greater than or Equal to Start Date!!!");
        } else {
            position = destinationsNames.indexOf(autoCompleteTextViewEndDest.getText().toString());
            updateGroupTrip();
        }
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateGroupTrip(){
        //Toast.makeText(getBaseContext(),"Data Found Outside"+ usersArrayList.size(), Toast.LENGTH_SHORT).show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        //String RoadMapID = "RoadMap"+ Calendar.getInstance().getTimeInMillis();

        reference1 = reference1.child(TripRoadMapID);
        //reference1.child("RoadMapID").setValue(TripRoadMapID);
        //reference1.child("UserID").setValue(usersArrayList.get(0).getUserID());
        reference1.child("StartDate").setValue(editTextStartDate.getText().toString());
        reference1.child("EndDate").setValue(editTextEndDate.getText().toString());
        reference1.child("StartDest").setValue(autoCompleteTextViewStartDest.getText().toString());
        reference1.child("EndDest").setValue(autoCompleteTextViewEndDest.getText().toString());
        reference1.child("EndDestImage").setValue(endDestinationsImages.get(position));
        reference1.child("TotalTravellers").setValue(editTextTotalTravellers.getText().toString());

        //Toast.makeText(getBaseContext(),"Road Map Added Successfully !!!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UpdateGroupTripRoadMap.this, ViewGroupRoadMap.class);
        startActivity(intent);
        finish();
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

    public void getDestinationDetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destinations");
        Query query = reference.child("Beaches").orderByChild("DestID");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    destinationsNames.clear();
                    endDestinationsImages.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Destinations destinations = dataSnapshot.getValue(Destinations.class);
                        destinationsNames.add(destinations.getDestName());
                        endDestinationsImages.add(destinations.getDestImage());
                    }
                    addStartDestAutoText();
                    addEndDestAutoText();
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

    public void addStartDestAutoText(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, destinationsNames);
        autoCompleteTextViewStartDest.setAdapter(adapter);
    }

    public void addEndDestAutoText(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, destinationsNames);
        autoCompleteTextViewEndDest.setAdapter(adapter);
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