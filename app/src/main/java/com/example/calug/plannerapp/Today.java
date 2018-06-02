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
import android.widget.AdapterView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Today extends Fragment {

    private ListView mEventListToday;
    private TextView mRegToday;
    private TextView mEtToday;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private ArrayList<String> mDate = new ArrayList<>();

    public String dateChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_today, container, false);
        View v = inflater.inflate(R.layout.fragment_today, container, false);

        mEventListToday = (ListView) v.findViewById(R.id.eventListToday);
        mRegToday = (TextView) v.findViewById(R.id.tvRegToday);
        mEtToday = (TextView) v.findViewById(R.id.etToday);


        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference current_user_db = mDatabase.child(uid).child("Dates");

        final String date = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(new Date());

        //mRegToday.setText(date);

        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mDate
        );

        mEventListToday.setAdapter(mAdapter);

        DatabaseReference dateToday = current_user_db.child(date);

        dateToday.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                mDate.add(value);
                //mRegToday.setVisibility(View.VISIBLE);
                //mRegToday.setText("On "+date+" you have to"+" "+value);
                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                mDate.add(value);
                //mRegToday.setVisibility(View.VISIBLE);
                //mRegToday.setText("On "+date+" you have to"+" "+value);
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

        mEventListToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String text = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
                DatabaseReference dateC = current_user_db.child(text);

                dateC.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mRegToday.setVisibility(View.VISIBLE);
                        mRegToday.setText("On "+date+" you have to "+key+" "+value);
                        //Toast.makeText(getActivity(), "Key: "+key+"/ Value: "+value, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mRegToday.setVisibility(View.VISIBLE);
                        mRegToday.setText("On "+date+" you have to "+key+" "+value);
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
        current_user_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                dateChild = value;
                //String sDate = someday.toString();
                mDate.add(value);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                dateChild = value;
                //String sDate = someday.toString();
                mDate.add(value);
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



        mEventListToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String text = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < mEventListToday.getCount(); i++) {
                    final String test = parent.getItemAtPosition(i).toString();
                    if(test.equals(date)){
                        Toast.makeText(getActivity(), "True", Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(getActivity(), "text: "+text+" / date: "+date, Toast.LENGTH_SHORT).show();

                //Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
                DatabaseReference dateC = current_user_db.child(date);

                dateC.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mRegToday.setVisibility(View.VISIBLE);
                        mRegToday.setText("On "+date+" you have to "+key+" "+value);
                        //Toast.makeText(getActivity(), "Key: "+key+"/ Value: "+value, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        mRegToday.setVisibility(View.VISIBLE);
                        mRegToday.setText("On "+date+" you have to "+key+" "+value);
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

*/




        return v;
    }


}
