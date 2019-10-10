package com.devneoxmy.wasta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);


        Button mainactivity = (Button) findViewById(R.id.main);

        mainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent =new Intent(IntentActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        Button login = (Button) findViewById(R.id.log);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent =new Intent(IntentActivity.this, LogIn.class);
                startActivity(logIntent);
            }
        });

        Button regActivity = (Button) findViewById(R.id.reg);

        regActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent =new Intent(IntentActivity.this, NewUser.class);
                startActivity(regIntent);
            }
        });

        Button addServiceActivity = (Button) findViewById(R.id.add);

        addServiceActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent =new Intent(IntentActivity.this, AddService.class);
                startActivity(addIntent);
            }
        });

        Button detailsActivity = (Button) findViewById(R.id.details);

        detailsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent =new Intent(IntentActivity.this, ServiceDetails.class);
                startActivity(detailsIntent);
            }
        });

        Button offersActivity = (Button) findViewById(R.id.offers);

        offersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offersIntent =new Intent(IntentActivity.this, OffersActivity.class);
                startActivity(offersIntent);
            }
        });





        Button profileActivity = (Button) findViewById(R.id.profile);

        profileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent =new Intent(IntentActivity.this, Profile.class);
                startActivity(profileIntent);
            }
        });


    }
}
