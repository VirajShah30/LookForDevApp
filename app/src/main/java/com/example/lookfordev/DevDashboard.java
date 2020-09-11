package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DevDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_dashboard);
        BottomNavigationView devNav = findViewById(R.id.devBottomNav);
        NavController navController = Navigation.findNavController(this, R.id.dev_nav_fragment);
        NavigationUI.setupWithNavController(devNav, navController);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}