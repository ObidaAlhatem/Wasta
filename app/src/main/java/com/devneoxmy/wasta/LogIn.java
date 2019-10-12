package com.devneoxmy.wasta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devneoxmy.wasta.R;
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

public class LogIn extends AppCompatActivity {

    //views
    EditText mEmailEt,mPasswordEt;
    Button mLoginBtn;
    //Declare an instance of FirebaseAuth
    public FirebaseAuth mAuth;
    //progres dialog
    ProgressDialog progressDialog;
    //Sign in with google
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    SignInButton signInByGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        mEmailEt=findViewById(R.id.name);
        mPasswordEt=findViewById(R.id.password);

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
                            startActivity(new Intent(LogIn.this, MainActivity.class));
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
                Toast.makeText(LogIn.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInByGoogle = (SignInButton) findViewById(R.id.signInByGoogle);
        signInByGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"User logged in successfully",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Could not log in",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
