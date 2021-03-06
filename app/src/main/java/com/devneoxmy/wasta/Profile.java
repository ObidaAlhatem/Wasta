package com.devneoxmy.wasta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;

public class Profile extends AppCompatActivity {

    private final static int PICK_IMAGE = 1;
    ImageView pic;
    Uri imageUri;
    TextView username;
    String userName;
    LinearLayout L1;
    LinearLayout L2;
    FirebaseAuth firebaseAuth;
    DatabaseReference muserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.username);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uId = currentUser.getUid();

        muserReference = FirebaseDatabase.getInstance().getReference("Wasta_app_users").child(uId);

        pic = (ImageView) findViewById(R.id.personal_picture);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });


        username = (TextView) findViewById(R.id.name);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        L1 = (LinearLayout) findViewById(R.id.phonecall);
        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1234567890"));

                if (ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        L2 = (LinearLayout) findViewById(R.id.fbaccount);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkUserStatuse() {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            muserReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String value = dataSnapshot.child("name").getValue(String.class);
                    username.setText(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {

            // user not signed and go to MainActivity
            startActivity(new Intent(Profile.this, MainActivity.class));
            finish();
        }
    }



        public void openfb (View view){
            Intent fbIntent = new Intent();
        }
    }