package com.myfinalproject.hydrobill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class consumer_details extends AppCompatActivity {

        Button cancel, save;

        EditText firstname;
        RecordManager db;

        String brgyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_details);

        save = findViewById(R.id.doneBUTTON);
        cancel = findViewById(R.id.cancelBUTTON);
        firstname = findViewById(R.id.firstnameEDT);
        db = new RecordManager(this);

        Intent intent = getIntent();
        brgyName = intent.getStringExtra("names");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String consumerFirstName = firstname.getText().toString();
                String barangayName = brgyName; // Replace with actual source of barangay name
                System.out.println("Barangay: "+ brgyName);
                boolean savedata = db.saveConsumer(consumerFirstName, barangayName);

                if (TextUtils.isEmpty(consumerFirstName)){
                    Toast.makeText(consumer_details.this, "Consumer name is required!", Toast.LENGTH_SHORT).show();
                }else{
                    if (savedata == true){

                        Toast.makeText(consumer_details.this, "Saved!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(consumer_details.this);
                        alertDialog.setTitle("SUCCESS!");
                        alertDialog.setMessage("New Consumer is Added");
                        alertDialog.setPositiveButton("Add Another Consumer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clear input field for new entry
                                firstname.getText().clear();
                            }
                        });
                        alertDialog.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,  int i){
                                Intent intent = new Intent(consumer_details.this, consumers_activity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alertDialog.show();
                    }else{
                        Toast.makeText(consumer_details.this, "Already exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(consumer_details.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(consumer_details.this, list_of_consumer_in_barangay.class);
                startActivity(intent);
                finish();

            }
        });
    }
}