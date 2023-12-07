package com.myfinalproject.hydrobill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class list_of_consumer_in_barangay extends AppCompatActivity {
    ImageView imageView;
    TextView edit, barangayname;
    FloatingActionButton addnewconsumer;
    RecyclerView recyclerView;
    ArrayList<String> consumersList;
    RecordManager db;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_consumer_in_barangay);

        imageView = findViewById(R.id.back);
        edit = findViewById(R.id.editBarangayname);
        barangayname = findViewById(R.id.barangayname);
        consumersList = new ArrayList<>();
        db = new RecordManager(this);
        recyclerView = findViewById(R.id.consumerrecyclerView); // Make sure to initialize recyclerView
        adapter = new MyAdapter(this, consumersList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addnewconsumer = findViewById(R.id.fabconsumer);

        displayConsumers();

        // Sort the list alphabetically
        Collections.sort(consumersList, String.CASE_INSENSITIVE_ORDER);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();

        Intent intent = getIntent();
        String barangayName = intent.getStringExtra("names");
        barangayname.setText(barangayName);
        System.out.println("Barangay: " + barangayName);

        addnewconsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(list_of_consumer_in_barangay.this, consumer_details.class);
                v.putExtra("names", barangayName);
                startActivity(v);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(list_of_consumer_in_barangay.this, update_delete.class);
                v.putExtra("names", barangayName);
                startActivity(v);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getApplicationContext(), consumers_activity.class);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        });
    }

    private void displayConsumers() {
        String barangayName = barangayname.getText().toString(); // Get the selected barangay name

        // Use the getConsumersForBarangay method to retrieve consumers for the given barangay
        Cursor cursor = db.getConsumersForBarangay(barangayName);

        consumersList.clear(); // Clear the previous data

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Consumers", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex("name");
                if (nameIndex != -1) {
                    String consumerName = cursor.getString(nameIndex);
                    consumersList.add(consumerName);
                }
            }

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }


    }
}
