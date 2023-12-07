package com.myfinalproject.hydrobill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class login_page extends AppCompatActivity {

    EditText username, password;
    TextView createAcc;
    Button btnlogin;
    Switch switchShowPassword;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = (EditText) findViewById(R.id.email_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        btnlogin = (Button) findViewById(R.id.loginBtn);
        DB = new DBHelper(this);
        createAcc = findViewById(R.id.create_Account_Btn_Textview);
        switchShowPassword = findViewById(R.id.switchswitchShowPassword);

        switchShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility
                if (isChecked) {
                    password.setTransformationMethod(null); // Show password
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod()); // Hide password
                }
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, create_account_page.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(login_page.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass) {
                        Toast.makeText(login_page.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        // Clear input fields after successful login
                        username.getText().clear();
                        password.getText().clear();
                    } else {
                        Toast.makeText(login_page.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                        // Optionally, clear password field after unsuccessful login
                        password.getText().clear();
                    }
                }
            }
        });


    }
}