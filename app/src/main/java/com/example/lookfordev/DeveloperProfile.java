package com.example.lookfordev;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;


public class DeveloperProfile extends Fragment {

    TextView initials, name, email, experience, workEmail, category;
    Button signout,chgpass;
    FirebaseAuth fauth;
    FirebaseUser user;
    FirebaseDatabase rootnode;
    DatabaseReference userReference,devReference;

    public DeveloperProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_developer_profile, container, false);
        fauth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        initials = v.findViewById(R.id.initials_dev);
        name = v.findViewById(R.id.name_dev);
        email = v.findViewById(R.id.email_dev);
        experience = v.findViewById(R.id.experience);
        workEmail = v.findViewById(R.id.workEmail);
        chgpass = v.findViewById(R.id.chgpass_dev);
        signout = v.findViewById(R.id.signout_dev);
        category = v.findViewById(R.id.category);
        rootnode = FirebaseDatabase.getInstance();
        userReference = rootnode.getReference("UserDetails");
        devReference = rootnode.getReference("DeveloperDetails");
        final String userId = fauth.getUid();
        userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);
                if (user != null) {
                    initials.setText(String.valueOf(user.getName().charAt(0)));
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error! Try Again", Toast.LENGTH_LONG).show();
            }
        });
        devReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DeveloperHelperClass dev = dataSnapshot.getValue(DeveloperHelperClass.class);
                if (dev != null) {
                    experience.setText(String.format("%s years", dev.getExperience()));
                    workEmail.setText(dev.getWorkEmail());
                    category.setText(dev.getCategory());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error! Try Again", Toast.LENGTH_LONG).show();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
            }
        });
        chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();
                fauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Reset Email Sent",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return v;
    }
}