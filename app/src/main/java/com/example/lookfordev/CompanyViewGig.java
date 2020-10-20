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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyViewGig extends Fragment {
    TextView title, budget, category, description;
    Button remove;
    FirebaseDatabase rootnode;
    DatabaseReference gigReference,appReference;
    FirebaseUser current;
    private FirebaseAuth mAuth;
    public CompanyViewGig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_view_gig, container, false);
        title = v.findViewById(R.id.title99);
        budget = v.findViewById(R.id.title9);
        category = v.findViewById(R.id.title3);
        description = v.findViewById(R.id.title4);
        remove = v.findViewById(R.id.Remove1);
        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        current = mAuth.getCurrentUser();
        gigReference = rootnode.getReference("CompanyDetails/" + current.getUid());
        gigReference.child("Gig").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GigHelperClass gig = dataSnapshot.getValue(GigHelperClass.class);
                        if(gig != null)
                        {
                            title.setText(gig.getTitle());
                            budget.setText(String.format("Rs.%s", gig.getBudget()));
                            description.setText(gig.getDescription());
                            category.setText(gig.getCategory());
                            appReference = rootnode.getReference("Gigs/"+gig.getCategory());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error! Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gigReference.child("Gig").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        appReference.child(current.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Gig Removed", Toast.LENGTH_SHORT).show();
                                title.setText(" ");
                                budget.setText(" ");
                                description.setText(" ");
                                category.setText(" ");
                            }
                        });
                    }
                });
            }
        });
        return v;
    }
}