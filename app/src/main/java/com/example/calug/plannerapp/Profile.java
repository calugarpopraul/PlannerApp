package com.example.calug.plannerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView mProfileNames;
    private TextView mCardUsername;
    private TextView mCardEmail;
    private TextView mCardPhone;
    private ImageView mProfileImageView;

    public String publicfname;
    public String publiclname;
    public String publicusername;
    public String publicemail;
    public String publicphone;
    public String uid;

    public String dataPassed;

    private static final int GALLERY_INTENT = 2;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false);

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfileNames = (TextView) v.findViewById(R.id.profileNames);
        mCardUsername = (TextView) v.findViewById(R.id.cardUsername);
        mCardEmail = (TextView) v.findViewById(R.id.cardEmail);
        mCardPhone = (TextView) v.findViewById(R.id.cardPhone);
        mProfileImageView = (ImageView) v.findViewById(R.id.profileImage);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){


                }else{

                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final DatabaseReference current_user_db = mDatabase.child(uid);
        DatabaseReference fname = current_user_db.child("firstname");
        DatabaseReference lname = current_user_db.child("lastname");
        DatabaseReference dphone = current_user_db.child("phone");

        mStorage = FirebaseStorage.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathRef = storageReference.child(uid);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference.child(uid))
                .into(mProfileImageView);

        fname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                publicfname = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String publiclname = dataSnapshot.getValue(String.class);
                String names = publicfname + " " + publiclname;
                mProfileNames.setText(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                publicphone = dataSnapshot.getValue(String.class);
                mCardPhone.setText(publicphone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mCardUsername.setText(uid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        mCardEmail.setText(email);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mProfileNames.setText(publicfname + " " + publiclname);


    }


}


