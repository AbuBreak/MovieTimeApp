package com.malikproject.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.malikproject.newsapp.R;
import com.malikproject.newsapp.models.MyPreference;

public class ConfirmResetActivity extends AppCompatActivity {
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reset);

        MyPreference preference = MyPreference.getInstance(this);
        Button btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateBackTo();
                Intent intent = new Intent(ConfirmResetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public void animateBackTo() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        btnBack.startAnimation(animation);
    }
}