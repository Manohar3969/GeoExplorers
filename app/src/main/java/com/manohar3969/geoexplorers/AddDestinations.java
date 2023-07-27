package com.manohar3969.geoexplorers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class AddDestinations extends AppCompatActivity {

    Spinner spinnerDestType;
    String destTypeSelected;
    EditText editTextDestName;
    TextView textViewAddDestination;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView imageView;
    String destImage = null;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destinations);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView = findViewById(R.id.imageView10);


        spinnerDestType = findViewById(R.id.spinnerDestType);
        editTextDestName = findViewById(R.id.editTextDestName);
        textViewAddDestination = findViewById(R.id.textViewAddDestination);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("- Select Destination Type -");
        arrayList.add("Beach");
        arrayList.add("Trekking");
        arrayList.add("Rock Climbing");
        arrayList.add("Mountain Climbing");
        arrayList.add("Holy Place");
        arrayList.add("Mountains");
        arrayList.add("Train Trips");

        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewDestType);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestType.setAdapter(arrayAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        autoCompleteTextView.setAdapter(adapter);

        spinnerDestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destTypeSelected =  parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textViewAddDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDestination();
            }
        });
    }

    public void addDestinationImg(View view) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage (View view)
    {
        if (filePath != null) {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddDestinations.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        destImage = task.getResult().toString();
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(),"Fetch Download URL Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(AddDestinations.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
    public void addDestination() {
        String DestinationId = "DestID" + Calendar.getInstance().getTimeInMillis();

        if (editTextDestName.getText().toString().isEmpty()) {
            editTextDestName.setError("Mandatory Field");
            editTextDestName.requestFocus();
        }
        else if (autoCompleteTextView.getText().toString().isEmpty()) {
            autoCompleteTextView.requestFocus();
            autoCompleteTextView.setError("Mandatory Field");
        }
        /*else if (destTypeSelected.equals("- Select Destination Type ")) {
            spinnerDestType.requestFocus();
        }*/
        else {

            if (destImage != null) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Destinations");

                DatabaseReference reference1 = reference.child(autoCompleteTextView.getText().toString()).child(DestinationId);
                reference1.child("DestID").setValue(DestinationId);
                reference1.child("DestName").setValue(editTextDestName.getText().toString());
                reference1.child("DestType").setValue(autoCompleteTextView.getText().toString());
                reference1.child("DestImage").setValue(destImage);

                //Toast.makeText(getBaseContext(),"Destination Added Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(),ViewDestinations.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getBaseContext(), "Destination Type Image Not Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}