package com.example.calug.plannerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Tomorrow extends Fragment {

    private ListView mEventListTomorrow;
    private TextView mEtTomorrow;
    private TextView mEtRegTomorrow;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private ArrayList<String> mDateT = new ArrayList<>();

    public String dateChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tomorrow, container, false);
        View v = inflater.inflate(R.layout.fragment_tomorrow, container, false);

        mEventListTomorrow = (ListView) v.findViewById(R.id.eventListTomorrow);
        mEtRegTomorrow = (TextView) v.findViewById(R.id.tvRegTomorrow);
        mEtTomorrow = (TextView) v.findViewById(R.id.etTomorrow);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference current_user_db = mDatabase.child(uid).child("Dates");

        final String date = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(new Date());

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");

        String todayAsString = dateFormat.format(today);
        final String tomorrowAsString = dateFormat.format(tomorrow);
        Toast.makeText(getActivity(), tomorrowAsString, Toast.LENGTH_SHORT).show();

        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mDateT
        );

        mEventListTomorrow.setAdapter(mAdapter);

        DatabaseReference dateTomorrow = current_user_db.child(tomorrowAsString);

        dateTomorrow.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                mDateT.add(tomorrowAsString);
                mEtRegTomorrow.setVisibility(View.VISIBLE);
                mEtRegTomorrow.setText("On "+tomorrowAsString+" you have to "+key+" "+value);
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                mDateT.add(tomorrowAsString);
                mEtRegTomorrow.setVisibility(View.VISIBLE);
                mEtRegTomorrow.setText("On "+tomorrowAsString+" you have to "+key+" "+value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

}
