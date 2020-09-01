package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterUserData extends AppCompatActivity {
    TextView login;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_data);
        login = findViewById(R.id.Login);
        next = findViewById(R.id.button);
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
                Toast.makeText(RegisterUserData.this, "Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterUserData.this, RegisterDevData.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        //super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        //Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}