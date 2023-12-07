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

import com.myfinalproject.hydrobill.RecordManager;

public class addnewbarangay extends AppCompatActivity {

    Button save, cancel;
    EditText name;

    RecordManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewbarangay);

        save = findViewById(R.id.doneBtn);
        cancel = findViewById(R.id.cancelBtn);
        name = findViewById(R.id.name_barangay);
        db = new RecordManager(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTEXT = name.getText().toString();
                Boolean savedata = db.saveBarangayTable(nameTEXT);
                if (TextUtils.isEmpty(nameTEXT)){
                    Toast.makeText(addnewbarangay.this, "Barangay name is required!", Toast.LENGTH_SHORT).show();
                }else{
                    if (savedata == true){

                        Toast.makeText(addnewbarangay.this, "Saved!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(addnewbarangay.this);
                        alertDialog.setTitle("SUCCESS!");
                        alertDialog.setMessage("New Barangay is Added");
                        alertDialog.setPositiveButton("Add Another Barangay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clear input field for new entry
                                name.getText().clear();
                            }
                        });
                        alertDialog.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,  int i){
                                Intent intent = new Intent(addnewbarangay.this, consumers_activity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alertDialog.show();
                    }else{
                        Toast.makeText(addnewbarangay.this, "Already exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(view -> {
            Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}




