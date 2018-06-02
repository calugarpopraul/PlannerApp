package com.example.calug.plannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UI extends AppCompatActivity {

    public DrawerLayout mDrawerlayout;
    public ActionBarDrawerToggle mToogle;
    public TextView mTv1;
    Fragment fragment;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //login user verification
        /*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){

                    //Intent intent  = new Intent(UI.this,RegisterActivity.class);
                    //startActivity(intent);
                }
            }
        };
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment home = new Profile();
        FragmentManager FM = getSupportFragmentManager();
        FM
                .beginTransaction()
                .replace(R.id.flcontent, home)
                .commit();

        mDrawerlayout =(DrawerLayout) findViewById(R.id.drawerlayoutUI);
        mToogle = new ActionBarDrawerToggle(this,mDrawerlayout, R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToogle);
        NavigationView nDrawer = (NavigationView) findViewById(R.id.nv);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nDrawer);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }




    public void selectItemDrawer(MenuItem menuItem){


        Fragment myFragment = null;
        Class fragmentClass;




        switch (menuItem.getItemId()){

            case R.id.profile:
                fragmentClass = Profile.class;
                break;
            case R.id.month:
                fragmentClass = Month.class;
                break;
            case R.id.today:
                fragmentClass = Today.class;
                break;
            case R.id.tomorrow:
                fragmentClass = Tomorrow.class;
                break;
            case R.id.someday:
                    fragmentClass = Someday.class;
                break;
            case R.id.settings:
                    fragmentClass = Settings.class;
                break;
            case R.id.logout:
                fragmentClass = LogoutUser.class;

                Intent intent = new Intent(UI.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Redirecting to Login Page...",
                        Toast.LENGTH_SHORT).show();
                break;


            default:
                fragmentClass = Profile.class;

        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }
       catch(Exception e){
            e.printStackTrace();
       }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();

    }

    private void  setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
