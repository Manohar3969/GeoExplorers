package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    TextView textViewUserID, textViewUserName, textViewUserEmailID;
    ArrayList<Users> usersArrayList = new ArrayList<>();

    CardView cardViewAdminProfile;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getUserDetails();

        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserID = findViewById(R.id.textViewUserID);
        textViewUserEmailID = findViewById(R.id.textViewUserEmailID);
        cardViewAdminProfile = findViewById(R.id.cardViewAdminProfile);

        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("reddymanohar396@gmail.com"))
        {
            cardViewAdminProfile.setVisibility(View.VISIBLE);
        }
        else{
            cardViewAdminProfile.setVisibility(View.GONE);
        }

    }

    public void adminProfile(View view){
        Intent intent = new Intent(this, DestinationPlanner.class);
        startActivity(intent);
        finish();
    }

    public void createUserProfile(){
        textViewUserName.setText(usersArrayList.get(0).getUserName());
        textViewUserID.setText(usersArrayList.get(0).getUserID());
        textViewUserEmailID.setText(usersArrayList.get(0).getUserEmailID());
    }


    public void signOut(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserProfile.this);

        alertDialogBuilder.setTitle("Sign Out");
        alertDialogBuilder.setMessage("Do You Want to SignOut?")
                .setCancelable(true).
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getBaseContext(),"Signed Out Successfully!!!", Toast.LENGTH_SHORT).show();
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
                    createUserProfile();
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