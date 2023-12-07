package com.myfinalproject.hydrobill;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class update_delete extends AppCompatActivity {

    EditText name;
    TextView delete, edit;
    ImageView back;
    RecordManager db;
    String brgyName;  // Store the Barangay name as a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        name = findViewById(R.id.nameEditText);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        db = new RecordManager(this);
        back = findViewById(R.id.back);


        Intent intent = getIntent();
        brgyName = intent.getStringExtra("names");
        System.out.println("Barangay:" + brgyName);
        name.setText(brgyName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(update_delete.this, list_of_consumer_in_barangay.class);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                startActivity(intent1);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameTEXT = name.getText().toString();
                Boolean deleteBarangay = db.deleteBarangay(nameTEXT);

                if (deleteBarangay == true) {
                    Toast.makeText(update_delete.this, "Delete Barangay", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(update_delete.this, consumers_activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(update_delete.this, "Not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect old and new names from UI
                String oldName = name.getText().toString();
                // Assuming you have another UI element to input the new name, modify this accordingly
                String newName =  name.getText().toString();
                Boolean updatedata = db.updateBarangay(brgyName);

                // Check if the new name is not empty
                if (TextUtils.isEmpty(brgyName)) {
                    Toast.makeText(update_delete.this, "New name cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (updatedata == true) {
                    Toast.makeText(update_delete.this, "Barangay Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(update_delete.this, consumers_activity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Toast.makeText(update_delete.this, "Not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
