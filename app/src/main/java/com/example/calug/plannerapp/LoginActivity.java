package com.example.calug.plannerapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    EditText mEtUsername;
    EditText mEtPassword;
    TextView mEtRegisterHere;
    Button mLoginBtn;

    MainActivity mA = new MainActivity();

    private static Socket s;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;

    String username = "";
    String password = "";
    private static String ip="192.168.0.102";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


       mEtUsername = (EditText) findViewById(R.id.etUsername);
       mEtPassword = (EditText) findViewById(R.id.etPassword);
       mEtRegisterHere = (TextView) findViewById(R.id.etRegisterHere);
       mLoginBtn = (Button) findViewById(R.id.loginBtn);

        //login user verification
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                    startActivity(new Intent(LoginActivity.this,UI.class));
                }
            }
        };




        mEtUsername.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              mEtUsername.setHint("");
              return false;
          }
      });

      mEtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
              if(!hasFocus){
                  mEtUsername.setHint("username");
              }
          }
      });
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


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("CLIENT","LOGIN BUTTON PRESSED");
                login();


            }
        });

        mEtRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    public void login(){
        String email = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(mA, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(mA, "Login problem...", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent ui = new Intent(LoginActivity.this,UI.class);
                        startActivity(ui);
                    }
                }
            });
        }
    }

    public void send_text(View v){

        username=mEtUsername.getText().toString();
        password=mEtPassword.getText().toString();

        myTask mt = new myTask();
        mt.execute();
        Log.e("CLIENT","TEXT SENT");

        Toast.makeText(getApplicationContext(),"Data sent",Toast.LENGTH_SHORT).show();

    }

    class myTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

           try{
               s = new Socket(ip,4444);
               printWriter = new PrintWriter(s.getOutputStream());
               printWriter.write(username);
               printWriter.write(password);
               printWriter.flush();
               printWriter.close();
               s.close();



           }catch (IOException e){
               e.printStackTrace();

           }

            return null;
        }
    }
}
