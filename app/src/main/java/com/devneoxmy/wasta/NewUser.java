package com.devneoxmy.wasta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class NewUser extends AppCompatActivity {
    //views
    EditText mPasseord,mEmail,mUserName,mPhoneNumber;
    TextView mRegiter;
    ProgressDialog progressDialog;
    TextView haveAccount;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mEmail=findViewById(R.id.email);
        mPasseord=findViewById(R.id.pass);
        mRegiter=findViewById(R.id.signup);
        mUserName=findViewById(R.id.username);
        mPhoneNumber=findViewById(R.id.mob);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Registring user...");

        //handel register btn click
        mRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email and password
                String Email=mEmail.getText().toString().trim();
                String Password=mPasseord.getText().toString().trim();
                String userName=mUserName.getText().toString().trim();
                String phone=mPhoneNumber.getText().toString().trim();



                //validate
                if(userName.isEmpty()){

                    //set error and focus to password editText
                    mUserName.setError("user name is required");
                    mUserName.setFocusable(true);

                }else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){

                    //set error and focus to email editText

                    mEmail.setError("Invalid email");
                    mEmail.setFocusable(true);
                }else if(Password.length()<6){

                    //set error and focus to password editText
                    mPasseord.setError("password length at least 6 characters");
                    mPasseord.setFocusable(true);

                }else if(phone.isEmpty()){

                    //set error and focus to password editText
                    mPhoneNumber.setError("phone is required");
                    mPhoneNumber.setFocusable(true);

                }else if(phone.length()<10){

                    //set error and focus to password editText
                    mPhoneNumber.setError("enter valid phone number");
                    mPhoneNumber.setFocusable(true);

                }else {
                    RegisterUser(Email,Password);  // registeruser
                }

            }
        });

    }

    private void RegisterUser(final String email, final String password) {
        final String userName=mUserName.getText().toString().trim();
        final String phone=mPhoneNumber.getText().toString().trim();

        //email and password patterns is valid show prgressdialog and registeruser
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start registration activity
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            UserInfo userInfo=new UserInfo(userName,email,phone);
                            // create firebase database
                            FirebaseDatabase.getInstance().getReference()
                                    .child("Wasta_app_user")
                                    .child(user.getUid())
                                    .setValue(userInfo)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(NewUser.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            Toast.makeText(NewUser.this,"Registered..."+user.getEmail(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewUser.this, IntentActivity.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(NewUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //error and cancel progress dialog and get and show error message
                progressDialog.dismiss();
                Toast.makeText(NewUser.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



    }
}
