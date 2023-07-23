package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class GoogleSignIn extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    DatabaseReference reference;
    ArrayList<Users> usersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("113753947768-tsps7c0vlm6b6t3lf7udlt4oc1uohka9.apps.googleusercontent.com").requestEmail().build();
        googleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(getBaseContext(), googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(getBaseContext(), TripPlanner.class);
            startActivity(intent);
            finish();
        }
    }

    public void signInUsingGoogle(View view){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,100);
    }

    public void addUserDetails(){
        reference = FirebaseDatabase.getInstance().getReference("Users");
        String UserID = "User"+ Calendar.getInstance().getTimeInMillis();

        reference.child(UserID).child("UserID").setValue(UserID);
        reference.child(UserID).child("UserName").setValue(firebaseAuth.getCurrentUser().getDisplayName());
        reference.child(UserID).child("UserEmailID").setValue(firebaseAuth.getCurrentUser().getEmail());

        Intent intent = new Intent(getBaseContext(), TripPlanner.class);
        startActivity(intent);
        finish();
    }

    public void userExists(){
        Intent intent = new Intent(getBaseContext(), TripPlanner.class);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100)
        {
            //displayToast("Entered On Activity Result");
            Task<GoogleSignInAccount> signInAccountTask = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful())
            {
                String s = "Google Sign In Successful";
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    getUserDetails();
                                } else {
                                    Toast.makeText(getBaseContext(), "Sign in With Email Failed !!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getBaseContext(), "Sign in With Email Failed !!!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getBaseContext(), "Sign in With Email Failed !!!", Toast.LENGTH_SHORT).show();
        }
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
                    userExists();
                }
                else {
                    addUserDetails();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}