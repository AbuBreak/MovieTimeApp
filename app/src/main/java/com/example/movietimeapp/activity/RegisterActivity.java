package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    ImageView img_back;
    EditText editUsername, editEmail, editPassword, editConfirmPass;
    Button btnAgree;


    public static final String MyPref = "myPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        MyPreference myPreference = new MyPreference(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ExistedEmail = myPreference.getData(editEmail.getText().toString()).getEmail();


                if(editUsername.getText().toString().isEmpty() ||
                editEmail.getText().toString().isEmpty()||
                editPassword.getText().toString().isEmpty() ||
                editConfirmPass.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"You must fill all required fields!",Toast.LENGTH_SHORT).show();
                }

                else if (editEmail.getText().toString().equals(ExistedEmail)) {
                    Toast.makeText(RegisterActivity.this, "User with the same email is already registered!", Toast.LENGTH_SHORT).show();

                } else if(!editPassword.getText().toString().equals(editConfirmPass.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password dose not match!", Toast.LENGTH_SHORT).show();
                } else {
                        myPreference.saveData(editEmail.getText().toString(), new Register(editUsername.getText().toString(),
                                editEmail.getText().toString(), editPassword.getText().toString()));
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                }

            }

        });

    }

    private void initViews() {
        img_back = findViewById(R.id.img_back);
        editUsername = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPass = findViewById(R.id.editConfirmPass);
        btnAgree = findViewById(R.id.btnAgreeRegister);
    }
}