package com.devneoxmy.wasta;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.time.Instant;

public class ServiceDetails extends AppCompatActivity {

    ImageView imgPost,imgUserPost,imgCurrentUser;
    TextView txtPostDesc,txtPostDateName,txtPostTitle;
    EditText editTextComment;
    Button btnAddComment;
    RatingBar ratingBar;
    Button btSubmit;
    LikeButton LikeButton;
    Button gmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        //RatingBar

        ratingBar = findViewById(R.id.rating_bar);
        btSubmit = findViewById(R.id.bt_submit);

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
