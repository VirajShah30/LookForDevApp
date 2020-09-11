package com.example.lookfordev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_dashboard);
        BottomNavigationView compNav = findViewById(R.id.compBottomNav);
        NavController navController = Navigation.findNavController(this, R.id.comp_nav_fragment);
        NavigationUI.setupWithNavController(compNav, navController);
//      NavigationUI.setupActionBarWithNavController(this, navController);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    public void onBackPressed() { }
}