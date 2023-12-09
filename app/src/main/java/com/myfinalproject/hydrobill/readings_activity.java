package com.myfinalproject.hydrobill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class readings_activity extends AppCompatActivity {

    Button button;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        button = findViewById(R.id.search_consumer);
        dialog = new Dialog(this);
        View rootView = findViewById(android.R.id.content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        // Fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500); // 1 second
        fadeIn.setFillAfter(true);

        // Apply the animation to the root view
        rootView.startAnimation(fadeIn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavMenu);

        bottomNavigationView.setSelectedItemId(R.id.readings);

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
                    finish();
                    return true;

                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), profile_activity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
    private void showCustomDialog() {
        dialog.setContentView(R.layout.popup_new_barangay_added_successfully);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button backButtonInDialog = dialog.findViewById(R.id.backbtndialog);
        backButtonInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click in the dialog
                dialog.dismiss(); // Dismiss the dialog if needed
            }
        });

        dialog.show();
    }
}