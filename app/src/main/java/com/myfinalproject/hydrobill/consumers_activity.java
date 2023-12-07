package com.myfinalproject.hydrobill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class consumers_activity extends AppCompatActivity {

    ImageView buttonAdd;

    RecyclerView recyclerView;
    ArrayList<String> uBarangayName;

    RecordManager db;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumers);

        db = new RecordManager(this);
        buttonAdd = findViewById(R.id.imageADD);
        uBarangayName = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewBarangay); // Make sure to initialize recyclerView
        adapter = new MyAdapter(this, uBarangayName);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displaydata();

        // Sort the list alphabetically
        Collections.sort(uBarangayName, String.CASE_INSENSITIVE_ORDER);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addnewbarangay.class);
                startActivity(intent);
            }
        });

        View rootView = findViewById(android.R.id.content);

        // Fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500); // 1 second
        fadeIn.setFillAfter(true);

        // Apply the animation to the root view
        rootView.startAnimation(fadeIn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavMenu);

        bottomNavigationView.setSelectedItemId(R.id.consumers);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.consumers) {
                    // Do nothing as the user is already in the consumers activity
                    return true;
                } else if (item.getItemId() == R.id.readings) {
                    startActivity(new Intent(getApplicationContext(), readings_activity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), profile_activity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void displaydata() {
        Cursor cursor = db.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Barangay", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                uBarangayName.add(cursor.getString(0));
            }
        }
    }
}
