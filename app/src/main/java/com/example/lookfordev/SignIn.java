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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;
    TextView forgotpass,register;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        forgotpass = findViewById(R.id.forgotpass);
        register = findViewById(R.id.Register);
        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail,pass;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                mail = email.getText().toString();
                pass = password.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                   // Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    email.setError( "Email is required!" );
                }
                else if (TextUtils.isEmpty(pass)) {
                    //Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    password.setError( "Password is required!" );
                }
                else if (!(mail.trim().matches(emailPattern))) {
                    email.setError("Enter valid Email address");
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignIn.this, DevDashboard.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail;
                mail = email.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                    //Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    email.setError( "Email is required!" );
                }
                else
                {
                    mAuth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Mail for password reset sent", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Reset failed! Please try again later", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, RegisterUserData.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        //Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}
