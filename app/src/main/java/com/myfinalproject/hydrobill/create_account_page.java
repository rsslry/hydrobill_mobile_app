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

public class create_account_page extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup;
    TextView signin;
    DBHelper DB;
    Switch switchShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        username = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        repassword = findViewById(R.id.confirm_password_edit_text);
        signin = findViewById(R.id.login_text_view_btn);
        signup = findViewById(R.id.reset_account_btn);
        DB = new DBHelper(this);
        switchShowPassword = findViewById(R.id.switchswitchShowPassword1);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();


                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(create_account_page.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert) {
                                Toast.makeText(create_account_page.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), login_page.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(create_account_page.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(create_account_page.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(create_account_page.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_page.class);
                startActivity(intent);
            }
        });
    }

    public void toggleShowPassword(View view) {
        Switch switchShowPassword = (Switch) view;
        if (switchShowPassword.isChecked()) {
            repassword.setTransformationMethod(null);
            password.setTransformationMethod(null); // Show password
        } else {
            repassword.setTransformationMethod(new PasswordTransformationMethod());
            password.setTransformationMethod(new PasswordTransformationMethod()); // Hide password
        }
    }
}