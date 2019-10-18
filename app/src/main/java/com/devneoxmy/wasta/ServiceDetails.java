package com.devneoxmy.wasta;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.time.Instant;
import java.util.jar.Attributes;

public class ServiceDetails extends AppCompatActivity {

    ImageView imgPost,imgUserPost,imgCurrentUser;
    TextView txtPostDesc,txtPostDateName,txtPostTitle,mUserName, mServiceN, mServiceD, mAddress, mPhNumber;
    EditText editTextComment;
    Button btnAddComment;
    RatingBar ratingBar;
    Button btSubmit;
    LikeButton LikeButton;
    Button gmail;
    Details details;
    DatabaseReference mServiceReference;
    StorageReference mImageReference;
    ValueEventListener mServiceListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        //RatingBar

        ratingBar = findViewById(R.id.rating_bar);
        btSubmit = findViewById(R.id.bt_submit);
        mServiceN = (TextView) findViewById(R.id.SN);
        mServiceD = (TextView) findViewById(R.id.SD);
        mPhNumber = (TextView) findViewById(R.id.PN);
        mUserName = (TextView) findViewById(R.id.username);




        mServiceReference = FirebaseDatabase.getInstance().getReference("Wasta_app_user").child("ServiceDetails");
        mImageReference = FirebaseStorage.getInstance().getReference().child("ServiceDetails");

        mServiceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                mServiceN.setText(value);
                mServiceD.setText(value);
                mPhNumber.setText(value);
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public void showData(DataSnapshot dataSnapshot) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();

                for (DataSnapshot uniqueIDSnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot vouchersnapshot : uniqueIDSnapshot.child("ServiceDetails").getChildren()) {
                        Details details = vouchersnapshot.getValue(Details.class);
                    }
                }
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), s+"Star",Toast.LENGTH_LONG).show();

            }
        });
        // gmail Button Report a Problem

        gmail =findViewById(R.id.email);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Soon activate the service",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setType("plain/text");
                //intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "xxxxxx@hotmail.com" });
                //intent.putExtra(Intent.EXTRA_SUBJECT, "Problem / Note regarding the application");
                //intent.putExtra(Intent.EXTRA_TEXT, "Hi guys ... I encountered my problems / observations .... as follows");
                //startActivity(Intent.createChooser(intent, "Report a Problem Put your problem name here in short"));
                //startActivity(intent);

            }
        });

        //Heart button like/unlike
        LikeButton  =  findViewById(R.id.heart_button);

        LikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(com.like.LikeButton likeButton) {

            }

            @Override
            public void unLiked(com.like.LikeButton likeButton) {

            }
        });


        // ini Views

        imgUserPost = findViewById(R.id.post_detail_user_img);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

        txtPostTitle = findViewById(R.id.post_detail_title);
        txtPostDesc = findViewById(R.id.post_detail_desc);
        txtPostDateName = findViewById(R.id.post_detail_date_name);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);

        //data


    }
}
