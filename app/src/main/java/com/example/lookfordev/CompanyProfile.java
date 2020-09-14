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

public class CompanyProfile extends Fragment {
    TextView initials, name, email, compName, compEmail;
    Button signout,chgpass;
    FirebaseAuth fauth;
    FirebaseUser user;
    FirebaseDatabase rootnode;
    DatabaseReference userReference,compReference;
    public CompanyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_profile, container, false);
        fauth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        initials = v.findViewById(R.id.initials);
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        compName = v.findViewById(R.id.compName);
        compEmail = v.findViewById(R.id.compEmail);
        chgpass = v.findViewById(R.id.chgpass_comp);
        signout = v.findViewById(R.id.signout_comp);
        rootnode = FirebaseDatabase.getInstance();
        userReference = rootnode.getReference("UserDetails");
        compReference = rootnode.getReference("CompanyDetails");
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
        compReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CompanyHelperClass comp = dataSnapshot.getValue(CompanyHelperClass.class);
                if (comp != null) {
                    compName.setText(comp.getCompanyName());
                    compEmail.setText(comp.getCompanyEmail());
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