package com.example.lookfordev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DeveloperSortGig extends Fragment {
    FirebaseDatabase rootnode;
    DatabaseReference catReference, userReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button search;
    String name , email;
    LinearLayout sortgig;
    public DeveloperSortGig() {
        // Required empty public constructor
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_developer_sort_gig, container, false);
        final Spinner dropdown = v.findViewById(R.id.spinner3);
        String[] items = new String[]{"Web Developer", "App Developer", "Hybrid Developer", "UI Developer", "Backend Developer"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootnode = FirebaseDatabase.getInstance();
        sortgig = v.findViewById(R.id.sortlayout);
        userReference = rootnode.getReference("UserDetails");
        search = v.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = dropdown.getSelectedItem().toString();
                catReference = rootnode.getReference("Gigs/"+s);
                catReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            sortgig.removeAllViews();
                            final String userId = mAuth.getUid();
                            final GigHelperClass gig = dsp.getValue(GigHelperClass.class);
                            final View gigView = getLayoutInflater().inflate(R.layout.gig_layout,null,false);
                            TextView title = gigView.findViewById(R.id.gigtitle);
                            TextView budget = gigView.findViewById(R.id.gigbudget);
                            TextView description = gigView.findViewById(R.id.gigdescription);
                            TextView category = gigView.findViewById(R.id.gigcategory);
                            final String Email = gig.getEmail();
                            title.setText(gig.getTitle());
                            budget.setText(String.format("Rs.%s", gig.getBudget()));
                            description.setText(gig.getDescription());
                            category.setText(gig.getCategory());
                            sortgig.addView(gigView);
                            Button apply = gigView.findViewById(R.id.applyButton);
                            apply.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "Appling for Gig" + Email, Toast.LENGTH_SHORT).show();
                                    userReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);
                                            if (user != null) {
                                                name =user.getName();
                                                email =user.getEmail();
                                                String[] TO_EMAILS = {Email, email};

                                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                                intent.setData(Uri.parse("mailto:"));
                                                intent.putExtra(Intent.EXTRA_EMAIL, TO_EMAILS);
                                                intent.putExtra(Intent.EXTRA_SUBJECT, "Application for "+gig.getTitle());
                                                intent.putExtra(Intent.EXTRA_TEXT, "With reference to your gig on LookForDev app, I have decided to apply for the job \n" +
                                                        "My personal details are: \n" +
                                                        "Name: "+name+"\n" +
                                                        "Email: "+email+"\n" +
                                                        "Category:"+gig.getCategory()+"\n"+
                                                        "Please do consider my request and further contact me"
                                                );
                                                startActivity(Intent.createChooser(intent, "Choose your email client"));
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(getActivity(), "Error! Try Again", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        return v;
    }
}