package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.Register;

public class OTPActivity extends AppCompatActivity {
    ImageView img_back;
    Button btnVerify;
    EditText code1, code2, code3, code4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        initViews();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (code1.getText().toString().isEmpty() || code2.getText().toString().isEmpty()
                        || code3.getText().toString().isEmpty() || code4.getText().toString().isEmpty()) {
                    Toast.makeText(OTPActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(OTPActivity.this, CreatePassActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {

        img_back = findViewById(R.id.img_back);
        btnVerify = findViewById(R.id.btnVerify);
        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
    }
}