package com.devneoxmy.wasta;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.jar.Attributes;


public class AddService extends AppCompatActivity {

    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    //a Uri object to store file path
    private Uri filePath;

    Button choose, upload, add;
    ImageView img;
    StorageReference mStorageRef;
    EditText mName, mDetails, mAddress, mPhNumber;

    Service service;
    DatabaseReference reff;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_service);
        //Activity Title
        getSupportActionBar().setTitle("Add Service");

        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        choose = (Button)findViewById(R.id.choose_pp);
        upload = (Button)findViewById(R.id.upload_pp);
        img = (ImageView)findViewById(R.id.personal_picture);
        mName = (EditText)findViewById(R.id.SN);
        mDetails = (EditText)findViewById(R.id.SD);
        mAddress = (EditText)findViewById(R.id.Ad);
        mPhNumber = (EditText)findViewById(R.id.PN);
        add = (Button)findViewById(R.id.add_service);
        service = new Service();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String ServiceId = firebaseUser.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Wasta_app_user").child(ServiceId).child("ServiceDetails");

        String Name = mName.getText().toString().trim();
        String Details = mDetails.getText().toString().trim();
        String Address = mAddress.getText().toString().trim();
        String PhoneNm = mPhNumber.getText().toString().trim();

        if (Name.isEmpty()) {
            mName.setError("Service name is required");
            mName.setFocusable(true);
        }else if (Details.isEmpty()) {
            mDetails.setError("Service details is required");
            mDetails.setFocusable(true);
        }else if (Address.isEmpty()) {
            mAddress.setError("Your Address is required");
            mAddress.setFocusable(true);
        }else if (PhoneNm.length()<10){
            mAddress.setError("enter valid phone number");
            mAddress.setFocusable(true);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PhNumber = Integer.parseInt(mPhNumber.getText().toString().trim());

                service.setName(mName.getText().toString().trim());
                service.setDetails(mDetails.getText().toString().trim());
                service.setAddress(mAddress.getText().toString().trim());
                service.setPhNumber(PhNumber);

                if (firebaseUser!=null) {

                    Toast.makeText(AddService.this, "Please Login", Toast.LENGTH_LONG).show();
                    }
                else {

                    reff.push().child(ServiceId).setValue(service);
                    Toast.makeText(AddService.this, "Service Added", Toast.LENGTH_LONG).show();
                }
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
            }
        });

    }

    private void FileUploader(){
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+GetFileExtension(filePath)).child("ServiceDetails");

        Ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        // Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void FileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    private String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }



}


