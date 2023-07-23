package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Calendar;

public class CreateGroupRoadMap extends AppCompatActivity {

    Calendar myCalendar;
    EditText editTextStartDate,editTextEndDate;
    EditText editTextStartDest, editTextEndDest, editTextTotalTravellers;
    ImageView imageViewStartDate, imageViewEndDate;

    ArrayList<Users> usersArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_road_map);

        myCalendar = Calendar.getInstance();

        editTextStartDate =findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);

        editTextStartDest = findViewById(R.id.editTextStartDest);
        editTextEndDest= findViewById(R.id.editTextEndDest);
        editTextTotalTravellers = findViewById(R.id.editTextTotalTravellers);

        imageViewStartDate = findViewById(R.id.imageView13);
        imageViewEndDate = findViewById(R.id.imageView14);

        getUserDetails();

        imageViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateGroupRoadMap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //Toast.makeText(getBaseContext(),"Date Selected: "+ dayOfMonth+":"+month+":"+year,Toast.LENGTH_SHORT).show();
                        editTextStartDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        imageViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateGroupRoadMap.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //Toast.makeText(getBaseContext(),"Date Selected: "+ dayOfMonth+":"+month+":"+year,Toast.LENGTH_SHORT).show();
                        editTextEndDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                },myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });


    }

    public void createGroupTripRoadMap(View view){
        addGroupTrip();
    }

    public void addGroupTrip(){
        Toast.makeText(getBaseContext(),"Data Found Outside"+ usersArrayList.size(), Toast.LENGTH_SHORT).show();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("GroupTripRoadMap");
        String RoadMapID = "RoadMap"+ Calendar.getInstance().getTimeInMillis();

        reference1 = reference1.child(RoadMapID);
        reference1.child("RoadMapID").setValue(RoadMapID);
        reference1.child("UserID").setValue(usersArrayList.get(0).getUserID());
        reference1.child("StartDate").setValue(editTextStartDate.getText().toString());
        reference1.child("EndDate").setValue(editTextEndDate.getText().toString());
        reference1.child("StartDest").setValue(editTextStartDest.getText().toString());
        reference1.child("EndDest").setValue(editTextEndDest.getText().toString());
        reference1.child("TotalTravellers").setValue(editTextTotalTravellers.getText().toString());

        //Toast.makeText(getBaseContext(),"Road Map Added Successfully !!!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CreateGroupRoadMap.this, ViewGroupRoadMap.class);
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
}