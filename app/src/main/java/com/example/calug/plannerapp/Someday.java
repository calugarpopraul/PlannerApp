package com.example.calug.plannerapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Someday extends Fragment {

      private ListView mEventList;
      private TextView mReg;
      private FirebaseAuth mAuth;
      private FirebaseUser mUser;
      private DatabaseReference mDatabase;

      private ArrayList<String> mDates = new ArrayList<>();

      public String dateChild;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_someday, container, false);
        final View v = inflater.inflate(R.layout.fragment_someday, container, false);

        mEventList = (ListView) v.findViewById(R.id.eventList);
        mReg = (TextView) v.findViewById(R.id.tvReg);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference current_user_db = mDatabase.child(uid).child("Dates");


        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mDates
        );

        mEventList.setAdapter(mAdapter);

        current_user_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                dateChild = value;
                //String sDate = someday.toString();
                mDates.add(value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                dateChild = value;
                //String sDate = someday.toString();
                mDates.add(value);
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



        mEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
                DatabaseReference dateC = current_user_db.child(text);

                dateC.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mReg.setVisibility(View.VISIBLE);
                        mReg.setText("On "+text+" you have to "+key+" "+value);
                        //Toast.makeText(getActivity(), "Key: "+key+"/ Value: "+value, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mReg.setVisibility(View.VISIBLE);
                        mReg.setText("On "+text+" you have to "+key+" "+value);
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
            }
        });


/*
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = mDatabase.child(user_id).child("Dates");
        current_user_db.child(fullDate).child("Payment").setValue(infos);
/*

        /*
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        itemList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(uid);
        */





        /*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();

                String dates = dataSnapshot.child(uid).child("firstname").getValue(String.class);

                itemList.add(dates);

                adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,itemList);
                mEventList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/


        return v;
    }

}
