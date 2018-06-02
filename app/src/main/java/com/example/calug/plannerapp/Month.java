package com.example.calug.plannerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


public class Month extends Fragment {

    CalendarView mCalendarView;
    FloatingActionButton fab_plus, fab_pay , fab_buy , fab_read;
    Animation FabOpen,FabClose,FabClockWise,FabAnticlockwise;
    private TextView mInfo;
    private EditText mGetInfo;
    private Button mFinish;
    boolean isOpen = false;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    public String fullDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_month, container, false);

        mInfo = (TextView) v.findViewById(R.id.tvInfo);
        mGetInfo = (EditText) v.findViewById(R.id.etGetInfo);
        mFinish = (Button) v.findViewById(R.id.buttonFinish);


        fab_plus =  v.findViewById(R.id.fab_plus);
        fab_pay = v.findViewById(R.id.fab_pay);

        fab_buy =  v.findViewById(R.id.fab_buy);
        fab_read = v.findViewById(R.id.fab_read);

        mCalendarView = (CalendarView) v.findViewById(R.id.mCalendarView);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //listener for user logged in
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



        //?
        FabOpen = AnimationUtils.loadAnimation(getActivity() , R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        FabClockWise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        FabAnticlockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    fab_buy.startAnimation(FabClose);
                    fab_pay.startAnimation(FabClose);
                    fab_read.startAnimation(FabClose);

                    fab_plus.startAnimation(FabAnticlockwise);
                    fab_buy.setClickable(false);
                    fab_read.setClickable(false);
                    fab_pay.setClickable(false);

                    isOpen = false;

                }
                else
                {
                    fab_buy.startAnimation(FabOpen);
                    fab_pay.startAnimation(FabOpen);
                    fab_read.startAnimation(FabOpen);

                    fab_plus.startAnimation(FabClockWise);
                    fab_buy.setClickable(true);
                    fab_read.setClickable(true);
                    fab_pay.setClickable(true);

                    isOpen = true;
                }


            }
        });

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fullDate =  year+"-"+(month+1)+"-"+dayOfMonth;
                //Toast.makeText(getActivity(), "Date: "+
                        //year+"/"+month+"/"+dayOfMonth, Toast.LENGTH_SHORT).show();


                //pay button

                fab_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //make event on selected date
                        //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), fullDate, Toast.LENGTH_SHORT).show();

                        mInfo.setVisibility(View.VISIBLE);
                        mGetInfo.setVisibility(View.VISIBLE);
                        mFinish.setVisibility(View.VISIBLE);

                        mFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String infos = mGetInfo.getText().toString();
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = mDatabase.child(user_id).child("Dates");
                                current_user_db.child(fullDate).child("Payment").setValue(infos);
                                mInfo.setVisibility(View.INVISIBLE);
                                mGetInfo.setVisibility(View.INVISIBLE);
                                mFinish.setVisibility(View.INVISIBLE);

                            }
                        });

                    }
                });
                //end of pay button

                //buy button
                fab_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mInfo.setVisibility(View.VISIBLE);
                        mGetInfo.setVisibility(View.VISIBLE);
                        mFinish.setVisibility(View.VISIBLE);

                        mFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String infos = mGetInfo.getText().toString();
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = mDatabase.child(user_id).child("Dates");
                                current_user_db.child(fullDate).child("Buy").setValue(infos);
                                mInfo.setVisibility(View.INVISIBLE);
                                mGetInfo.setVisibility(View.INVISIBLE);
                                mFinish.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                });
                //end of buy button

                fab_read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInfo.setVisibility(View.VISIBLE);
                        mGetInfo.setVisibility(View.VISIBLE);
                        mFinish.setVisibility(View.VISIBLE);

                        mFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String infos = mGetInfo.getText().toString();
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = mDatabase.child(user_id).child("Dates");
                                current_user_db.child(fullDate).child("Event").setValue(infos);
                                mInfo.setVisibility(View.INVISIBLE);
                                mGetInfo.setVisibility(View.INVISIBLE);
                                mFinish.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });


            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
