package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class RegisterCompData extends AppCompatActivity {
    EditText name,email;
    Button btn;
    FirebaseDatabase rootnode;
    DatabaseReference compReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_comp_data);
        name = findViewById(R.id.Username);
        email = findViewById(R.id.editTextTextEmailAddress);
        btn = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        compReference = rootnode.getReference("CompanyDetails");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String cname,cemail;
                cname = name.getText().toString();
                cemail = email.getText().toString();
                if (TextUtils.isEmpty(cname)) {
                    name.setError( "Name is required!" );
                }
                else if (TextUtils.isEmpty(cemail)){
                    email.setError( "Email is required!" );
                }
                else if (!(cemail.trim().matches(emailPattern))){
                    email.setError("Enter valid Email address");
                }
                else {
                    user = mAuth.getCurrentUser();
                    CompanyHelperClass compdetail = new CompanyHelperClass(cname,cemail);
                    compReference.child(user.getUid()).setValue(compdetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(RegisterCompData.this,CompDashboard.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    public void onBackPressed() { }
}