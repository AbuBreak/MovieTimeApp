package com.malikproject.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malikproject.newsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ForgetActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText forget_email;
    private TextView txtLogin;
    private Button btnSend;

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initViews();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (forget_email.getText().toString().isEmpty()) {
                    forget_email.setError("Email is required!");
                    forget_email.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(forget_email.getText().toString()).matches()) {
                    forget_email.setError("Please provide a valid email!");
                    forget_email.requestFocus();

                } else {
                    mAuth.sendPasswordResetEmail(forget_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetActivity.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgetActivity.this, OTPActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ForgetActivity.this, "try again! Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    private void initViews() {
        img_back = findViewById(R.id.img_back);
        forget_email = findViewById(R.id.forget_email);
        txtLogin = findViewById(R.id.txtLogin);
        btnSend = findViewById(R.id.btnSend);
    }
}