package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.Movie1_Fragment;
import com.example.movietimeapp.models.Movie2_Fragment;

public class MovieActivity extends AppCompatActivity {
ImageView img_back;
TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtName=findViewById(R.id.txtName);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");

        txtName.setText(name);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer,new Movie1_Fragment(),null)
                .commit();
    }
}