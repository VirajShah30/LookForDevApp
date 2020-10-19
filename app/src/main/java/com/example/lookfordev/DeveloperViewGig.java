package com.example.lookfordev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DeveloperViewGig extends Fragment {
    private FirebaseAuth mAuth;
    TextView a,b;
    FirebaseDatabase rootnode;
    DatabaseReference devReference,gigReference,userReference;
    FirebaseUser current;
    String Role, Experience, name , email;
    LinearLayout displaygig;
    public DeveloperViewGig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_developer_view_gig, container, false);
        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        devReference = rootnode.getReference("DeveloperDetails");
        current = mAuth.getCurrentUser();
        userReference = rootnode.getReference("UserDetails");
        displaygig = v.findViewById(R.id.gigLayout);
        if(current != null) {
            final String userId = mAuth.getUid();
            devReference.child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DeveloperHelperClass user = dataSnapshot.getValue(DeveloperHelperClass.class);
                            Role = user.getCategory();
                            Experience = user.getExperience();
                            gigReference = rootnode.getReference("Gigs/"+ Role);
                            gigReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
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
                                        displaygig.addView(gigView);
                                        Button apply = gigView.findViewById(R.id.applyButton);
                                        apply.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Toast.makeText(getActivity(), "Appling for Gig" + Email, Toast.LENGTH_SHORT).show();

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
                                                                    "Category:"+Role+"\n"+
                                                                    "Experience "+Experience+" years \n" + "Please do consider my request and further contact me"
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
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Error! Try Again", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        return v;
    }


}
