package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUserData extends AppCompatActivity {
    TextView login;
    Button next;
    EditText username,email,password,conpassword;
    RadioGroup group;
    RadioButton dev,comp,radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_data);
        login = findViewById(R.id.Login);
        next = findViewById(R.id.button);
        username = findViewById(R.id.Username);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        conpassword = findViewById(R.id.editTextTextConfirmpassword);
        group = findViewById(R.id.radioGroup);
        dev = findViewById(R.id.dev);
        comp = findViewById(R.id.comp);
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
                //Toast.makeText(RegisterUserData.this, "Pressed", Toast.LENGTH_SHORT).show();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String uname,mail,pass,cpass,role;
                uname = username.getText().toString();
                mail = email.getText().toString();
                pass = password.getText().toString();
                cpass = conpassword.getText().toString();
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
                        int radioId = group.getCheckedRadioButtonId();
                        radioButton = findViewById(radioId);
                        role = radioButton.getText().toString();
                        Toast.makeText(RegisterUserData.this, role , Toast.LENGTH_SHORT).show();
                    }
                }
//                Intent intent = new Intent(RegisterUserData.this, RegisterDevData.class);
//                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        //super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        //Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}