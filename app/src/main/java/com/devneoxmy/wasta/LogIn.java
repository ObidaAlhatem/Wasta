package com.devneoxmy.wasta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devneoxmy.wasta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    //views
    EditText mEmailEt,mPasswordEt;
    Button mLoginBtn;
    TextView register;
    TextView recoverpw;
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    //progres dialog
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        mEmailEt=findViewById(R.id.name);
        mPasswordEt=findViewById(R.id.password);
register=findViewById(R.id.signup);
recoverpw=findViewById(R.id.forgot);

        mLoginBtn=findViewById(R.id.loginbtn);
        // login btn click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String email=mEmailEt.getText().toString();
                String password=mPasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email pattern set error
                    mEmailEt.setError("InValid email");
                    mEmailEt.setFocusable(true);

                }else {
                    //valid email patterns
                    Loginuser(email,password);
                }

            }
        });

        //if new user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LogIn.this,NewUser.class);
                startActivity(regIntent);
                finish();
            }
        });

        //init progress dialog
        progressDialog=new ProgressDialog(LogIn.this);
        progressDialog.setMessage("Logging in ....");
    }

    private void Loginuser(String email, String password) {
        // show progress dialog
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //dismiss progress dialog
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // user is logged in ,open profile activity
                            startActivity(new Intent(LogIn.this,MainActivity.class));
                            finish();
                        } else {
                            //dismiss progress dialog
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error and get error
                Toast.makeText(LogIn.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        recoverpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPassword();
            }
        });

    }

    private void showRecoverPassword() {
        //AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

      //Views to set in dialog
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setEms(16);


        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        //buttons recover
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //input email
                String email = emailEt.getText().toString().trim();
                beingRecovery(email);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

           //dismiss dialog
                dialog.dismiss();

            }
        });
        //show dialog
        builder.create().show();

    }

    private void beingRecovery(String email) {
        progressDialog.setMessage("Sending email ....");
progressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
           if(task.isSuccessful()){
               Toast.makeText(LogIn.this, "Email Sent", Toast.LENGTH_SHORT).show();
           }
           else {
               Toast.makeText(LogIn.this, "Failed...", Toast.LENGTH_SHORT).show();
           }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                //get and show proper error message
                Toast.makeText(LogIn.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
