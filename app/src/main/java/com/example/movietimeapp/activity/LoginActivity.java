package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edit_email;
    TextInputEditText edit_pass;
    TextView txtForget, txtRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        MyPreference preference = new MyPreference(this);

        Register register = new Register(preference.getData(edit_email.getText().toString()).getUsername(),
                preference.getData(edit_email.getText().toString()).getEmail(), preference.getData(edit_email.getText().toString()).getPassword());
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_email.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email!", Toast.LENGTH_SHORT).show();

                } else if (edit_pass.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please provide a password!", Toast.LENGTH_SHORT).show();

                } else if (!edit_email.getText().toString().equals(preference.getData(edit_email.getText().toString()).getEmail())) {
                    Toast.makeText(LoginActivity.this, "User dose not exist!", Toast.LENGTH_SHORT).show();

                } else if (!edit_pass.getText().toString().equals(preference.getData(edit_email.getText().toString()).getPassword())) {
                    Toast.makeText(LoginActivity.this, "Password Invalid! ", Toast.LENGTH_SHORT).show();

                } else if (edit_email.getText().toString().equals(preference.getData(edit_email.getText().toString()).getEmail())
                        && edit_pass.getText().toString().equals(preference.getData(edit_email.getText().toString()).getPassword())) {

                    preference.saveData("ActiveUser", new Register(preference.getData(edit_email.getText().toString()).getUsername(),
                            edit_email.getText().toString(), edit_pass.getText().toString()));
                    Intent intent1 = new Intent(LoginActivity.this, HomePageActivity.class);
                    intent1.putExtra("user", preference.getData("ActiveUser").getUsername());
                    startActivity(intent1);
                }
            }
        });

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initViews() {
        img_back = findViewById(R.id.img_back);
        edit_email = findViewById(R.id.confrimPass);
        edit_pass = findViewById(R.id.edit_pass);
        txtForget = findViewById(R.id.txtForget);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.loginBtn);
    }
}