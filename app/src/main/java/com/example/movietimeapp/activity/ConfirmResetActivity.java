package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;

public class ConfirmResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reset);

        MyPreference preference = MyPreference.getInstance(this);
        Button btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(ConfirmResetActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}