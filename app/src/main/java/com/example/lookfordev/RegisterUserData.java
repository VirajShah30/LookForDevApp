package com.example.lookfordev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserData extends AppCompatActivity {
    TextView login;
    Button next;
    EditText username,email,password,conpassword;
    RadioGroup group;
    RadioButton dev,comp,radioButton;
    FirebaseDatabase rootnode;
    DatabaseReference userReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_data);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.Login);
        next = findViewById(R.id.button);
        username = findViewById(R.id.Username);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        conpassword = findViewById(R.id.editTextTextConfirmpassword);
        group = findViewById(R.id.radioGroup);
        dev = findViewById(R.id.dev);
        comp = findViewById(R.id.comp);
        rootnode = FirebaseDatabase.getInstance();
        userReference = rootnode.getReference("UserDetails");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUserData.this, SignIn.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                //Toast.makeText(RegisterUserData.this, "Pressed", Toast.LENGTH_SHORT).show();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String uname,mail,pass,cpass,role;
                uname = username.getText().toString().trim();
                mail = email.getText().toString().trim();
                pass = password.getText().toString().trim();
                cpass = conpassword.getText().toString().trim();
                int radioId = group.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                role = radioButton.getText().toString();
                if (TextUtils.isEmpty(uname)) {
                    // Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    username.setError( "Username is required!" );
                }
                else if (TextUtils.isEmpty(mail)) {
                    // Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    email.setError( "Email is required!" );
                }
                else if (TextUtils.isEmpty(pass)) {
                    // Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    password.setError( "Password is required!" );
                }
                else if (TextUtils.isEmpty(cpass)) {
                    // Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    conpassword.setError( "Confirm Password is required!" );
                }
                else {
                    if (!(mail.trim().matches(emailPattern))) {
                        email.setError("Enter valid Email address");
                    }
                    else if(!pass.equals(cpass))
                        conpassword.setError( "Password & Confirm Password don't match" );
                    else {
                        //Toast.makeText(RegisterUserData.this, role , Toast.LENGTH_SHORT).show();
                        mAuth.createUserWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(RegisterUserData.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            user = mAuth.getCurrentUser();
                                            UserHelperClass userdetail = new UserHelperClass(uname,mail,role);
                                            userReference.child(user.getUid()).setValue(userdetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (role.equals("Developer")) {
                                                        Intent intent = new Intent(RegisterUserData.this,RegisterDevData.class);
                                                        startActivity(intent);
                                                    }
                                                    else{
                                                        Intent intent = new Intent(RegisterUserData.this, RegisterCompData.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(RegisterUserData.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
    public void onBackPressed() { }
}