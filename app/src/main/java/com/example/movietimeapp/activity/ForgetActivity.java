package com.example.movietimeapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText forget_email;
    private TextView txtLogin;
    private Button btnSend;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initViews();
        MyPreference MyPrf = new MyPreference(this);

        mAuth = FirebaseAuth.getInstance();
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
                   /* intent.putExtra("email", new Register(MyPrf.getData(forget_email.getText().toString()).getUsername()
                            , forget_email.getText().toString(), MyPrf.getData(forget_email.getText().toString()).getPassword()));*/
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