package com.myfinalproject.hydrobill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        View rootView = findViewById(android.R.id.content);

        // Fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500); // 1 second
        fadeIn.setFillAfter(true);

        // Apply the animation to the root view
        rootView.startAnimation(fadeIn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavMenu);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.consumers) {
                    startActivity(new Intent(getApplicationContext(), consumers_activity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.readings) {
                    startActivity(new Intent(getApplicationContext(), readings_activity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                } else if (item.getItemId() == R.id.profile) {
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}