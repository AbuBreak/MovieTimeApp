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
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ImageView img_back;
    EditText editUsername, editEmail, editPassword, editConfirmPass;
    Button btnAgree;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        mAuth = FirebaseAuth.getInstance();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editUsername.getText().toString().isEmpty()) {
                    editUsername.setError("Required field!");
                    editUsername.requestFocus();

                } else if (editEmail.getText().toString().isEmpty()) {
                    editEmail.setError("Required field!");
                    editEmail.requestFocus();

                } else if (editPassword.getText().toString().isEmpty()) {
                    editPassword.setError("Required field!");
                    editPassword.requestFocus();

                } else if (editConfirmPass.getText().toString().isEmpty()) {
                    editConfirmPass.setError("Required field");
                    editConfirmPass.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()) {
                    editEmail.setError("Please provide valid email!");
                    editEmail.requestFocus();

                } else if (!editPassword.getText().toString().equals(editConfirmPass.getText().toString())) {
                    editPassword.setError("Password dose not match!");
                    editPassword.requestFocus();

                } else if (editPassword.length() < 6) {
                    editPassword.setError("Min Password length should be 6 characters!");
                    editPassword.requestFocus();
                } else {
                    mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Register register = new Register(editUsername.getText().toString()
                                                , editEmail.getText().toString(), editPassword.getText().toString());

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(register).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegisterActivity.this,
                                                                    "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "User is already registered!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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