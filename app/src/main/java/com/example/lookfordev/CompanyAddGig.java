package com.example.lookfordev;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyAddGig extends Fragment {

    EditText title,budget,description;
    Button submit;
    FirebaseDatabase rootnode;
    DatabaseReference web,app,hybrid,ui,backend,gigReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    public CompanyAddGig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_add_gig, container, false);
        final Spinner dropdown = v.findViewById(R.id.spinner2);
        String[] items = new String[]{"Web Developer", "App Developer", "Hybrid Developer", "UI/UX Developer", "Backend Developer"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        title = v.findViewById(R.id.title);
        budget = v.findViewById(R.id.budget);
        description = v.findViewById(R.id.description);
        submit = v.findViewById(R.id.submit);
        rootnode = FirebaseDatabase.getInstance();
        web = rootnode.getReference("Gigs/Web Developer");
        app = rootnode.getReference("Gigs/App Developer");
        hybrid = rootnode.getReference("Gigs/Hybrid Developer");
        ui = rootnode.getReference("Gigs/UI Developer");
        backend = rootnode.getReference("Gigs/Backend Developer");
        gigReference = rootnode.getReference("CompanyDetails/"+ user.getUid());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t,cat,bud,des;
                t = title.getText().toString();
                cat = dropdown.getSelectedItem().toString();
                bud = budget.getText().toString();
                des = description.getText().toString();
                if (TextUtils.isEmpty(t)) {
                    title.setError( "Title is required!" );
                }
                else if (TextUtils.isEmpty(bud)){
                    budget.setError( "Budget is required!" );
                }
                else if (TextUtils.isEmpty(des)){
                    description.setError( "Description is required!" );
                }
                else{
                    user = mAuth.getCurrentUser();
                    final GigHelperClass gig = new GigHelperClass(t,bud,des,cat);
                    switch (cat) {
                        case "Web Developer":
                            web.child(user.getUid()).setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    gigReference.child("Gig").setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            break;
                        case "App Developer":
                            app.child(user.getUid()).setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    gigReference.child("Gig").setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            break;
                        case "Hybrid Developer":
                            hybrid.child(user.getUid()).setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    gigReference.child("Gig").setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            break;
                        case "UI/UX Developer":
                            ui.child(user.getUid()).setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    gigReference.child("Gig").setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            break;
                        case "Backend Developer":
                            backend.child(user.getUid()).setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    gigReference.child("Gig").setValue(gig).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            break;
                    }
                }
            }
        });
        return v;
    }
}

