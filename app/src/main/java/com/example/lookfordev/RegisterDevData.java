package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class RegisterDevData extends AppCompatActivity {
    EditText name,email,experience;
    Button btn;
    FirebaseDatabase rootnode;
    DatabaseReference devReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dev_data);
        name = findViewById(R.id.Username);
        email = findViewById(R.id.editTextTextEmailAddress);
        experience = findViewById(R.id.experience);
        btn = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        devReference = rootnode.getReference("DeveloperDetails");
        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Web Developer", "App Developer", "Hybrid Developer", "UI/UX Developer", "Backend Developer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname,mail,exp,cat;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                fname = name.getText().toString();
                mail = email.getText().toString();
                exp = experience.getText().toString();
                cat = dropdown.getSelectedItem().toString();
                //Toast.makeText(RegisterDevData.this, cat, Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(fname)) {
                    name.setError( "Name is required!" );
                }
                else if (TextUtils.isEmpty(mail)){
                    email.setError( "Email is required!" );
                }
                else if (TextUtils.isEmpty(exp)){
                    experience.setError( "Experience is required!" );
                }
                else if (!(mail.trim().matches(emailPattern))){
                    email.setError("Enter valid Email address");
                }
                else{
                    user = mAuth.getCurrentUser();
                    DeveloperHelperClass devdetails = new DeveloperHelperClass(fname,mail,exp,cat);
                    devReference.child(user.getUid()).setValue(devdetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(RegisterDevData.this,DevDashboard.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}