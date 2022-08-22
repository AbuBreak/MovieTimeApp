package com.example.movietimeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ImageView img_back;
    EditText edit_email;
    TextInputEditText edit_pass;
    TextView txtForget, txtRegister;
    Button btnLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        mAuth = FirebaseAuth.getInstance();

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
                    edit_email.setError("Email is required!");
                    edit_email.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
                    edit_email.setError("Provide a valid email!");
                    edit_email.requestFocus();

                } else if (edit_pass.getText().toString().isEmpty()) {
                    edit_pass.setError("Password is required!");
                    edit_pass.requestFocus();

                } else if (edit_pass.length() < 6) {
                    edit_pass.setError("Min password length is 6 characters!");
                    edit_pass.requestFocus();

                }
                mAuth.signInWithEmailAndPassword(edit_email.getText().toString(), edit_pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user.isEmailVerified()) {
                                        Intent intent1 = new Intent(LoginActivity.this, HomePageActivity.class);
                                        startActivity(intent1);
                                    } else {
                                        user.sendEmailVerification();
                                        Toast.makeText(LoginActivity.this, "Please check your email to verify your account!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

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