package com.malikproject.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.malikproject.newsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePassActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText new_pass, confirm_pass;
    private Button btnReset;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        initViews();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateReset();
                if (new_pass.getText().toString().isEmpty()) {
                    new_pass.setError("Field is required!");
                    new_pass.requestFocus();

                } else if (confirm_pass.getText().toString().isEmpty()) {
                    confirm_pass.setError("Field is required!");
                    confirm_pass.requestFocus();

                } else if (new_pass.length() < 6) {
                    new_pass.setError("Min length should be 6 character!");
                    new_pass.requestFocus();
                } else if (reference.child("").child("password").equals(new_pass.getText().toString())) {
                    new_pass.setError("You cannot enter an old password!");
                    new_pass.requestFocus();

                } else if (!new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                    confirm_pass.setError("Password dose not match, Please check!");
                    confirm_pass.requestFocus();

                } else if (new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                    reference.child("").child("password").setValue(new_pass.getText().toString());
                    Intent intent = new Intent(CreatePassActivity.this, ConfirmResetActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void animateReset(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        btnReset.startAnimation(animation);
    }
    private void initViews() {

        img_back = findViewById(R.id.img_back);
        new_pass = findViewById(R.id.new_pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        btnReset = findViewById(R.id.btnBack);
    }
}