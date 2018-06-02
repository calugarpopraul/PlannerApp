package com.example.calug.plannerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //widgets
    EditText mEtPassword;
    EditText mEtFirstName;
    EditText mEtLastName;
    EditText mEtPhone;
    EditText mEtEmail;
    Button mSignUpBtn;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //check for user
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    //startActivity(new Intent(RegisterActivity.this,UI.class));
                }else{
                    //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        };

        //initialize widgets
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mEtFirstName = (EditText) findViewById(R.id.etFirstName);
        mEtLastName = (EditText) findViewById(R.id.etLastName);
        mEtPhone = (EditText) findViewById(R.id.etPhone);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        //end of initialize widgets




       mEtPassword.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               mEtPassword.setHint("");
               return false;
           }
       });

       mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   mEtPassword.setHint("password");
               }
           }
       });

       mEtFirstName.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               mEtFirstName.setHint("");
               return false;
           }
       });

       mEtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   mEtFirstName.setHint("first name");
               }
           }
       });

       mEtLastName.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               mEtLastName.setHint("");
               return false;
           }
       });

       mEtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   mEtLastName.setHint("last name");
               }
           }
       });

       mEtPhone.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               mEtPhone.setHint("");
               return false;
           }
       });

       mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   mEtPhone.setHint("phone");
               }
           }
       });

       mEtEmail.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               mEtEmail.setHint("");
               return false;
           }
       });

       mEtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   mEtEmail.setHint("email");
               }
           }
       });
       //End of focus listeners for fields


        //Sign up button
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerUser();


                String password = mEtPassword.getText().toString().trim();
                final String firstname = mEtFirstName.getText().toString().trim();
                final String lastname = mEtLastName.getText().toString().trim();
                final String phone = mEtPhone.getText().toString().trim();
                String email = mEtEmail.getText().toString().trim();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) &&
                        !TextUtils.isEmpty(phone)){

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_id = mDatabase.child(user_id);
                                current_user_id.child("firstname").setValue(firstname);
                                current_user_id.child("lastname").setValue(lastname);
                                current_user_id.child("phone").setValue(phone);

                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                            } else {

                            }
                        }
                    });
                }


                Toast.makeText(getApplicationContext(), "Redirecting...",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /*

    private void registerUser() {


        String password = mEtPassword.getText().toString().trim();
        final String firstname = mEtFirstName.getText().toString().trim();
        final String lastname = mEtLastName.getText().toString().trim();
        final String phone = mEtPhone.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) &&
        !TextUtils.isEmpty(phone)){

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_id = mDatabase.child(user_id);
                        current_user_id.child("firstname").setValue(firstname);
                        current_user_id.child("lastname").setValue(lastname);
                        current_user_id.child("phone").setValue(phone);

                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                    } else {

                    }
                }
            });
        }
    }
    */
}
