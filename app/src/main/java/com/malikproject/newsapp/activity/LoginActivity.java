package com.malikproject.newsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malikproject.newsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final String CHECKED = "rememberMe";
    private ImageView img_back;
    private EditText edit_email;
    private TextInputEditText edit_pass;
    private TextView txtForget, txtRegister;
    private Button btnLogin;
    private CheckBox cbRemember;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        sharedPreferences = getSharedPreferences(CHECKED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String saveEmail = sharedPreferences.getString("svEmail", "");
        String savePass = sharedPreferences.getString("svPass", "");

        cbRemember.setChecked(sharedPreferences.contains("checked") && sharedPreferences.getBoolean("checked", false));

        edit_email.setText(saveEmail);
        edit_pass.setText(savePass);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbRemember.isChecked()) {
                    editor.putBoolean("checked", true);
                    editor.apply();
                    StoreDataUsingPref(edit_email.getText().toString(), edit_pass.getText().toString());

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
                                    animateLogin();
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
                                        animateLogin();
                                        Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
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
                    getSharedPreferences(CHECKED,MODE_PRIVATE).edit().clear().commit();
                    mAuth.signInWithEmailAndPassword(edit_email.getText().toString(), edit_pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    animateLogin();
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        if (user.isEmailVerified()) {
                                            Intent intent1 = new Intent(LoginActivity.this, HomePageActivity.class);
                                            startActivity(intent1);
                                        } else {
                                            animateLogin();
                                            user.sendEmailVerification();
                                            Toast.makeText(LoginActivity.this, "Please check your email to verify your account!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        animateLogin();
                                        Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
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
    public void animateLogin(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        btnLogin.startAnimation(animation);
    }

    private void StoreDataUsingPref(String email, String pass) {
        SharedPreferences.Editor editor = getSharedPreferences(CHECKED, MODE_PRIVATE).edit();
        editor.putString("svEmail", email);
        editor.putString("svPass", pass);
        editor.apply();
    }

    private void initViews() {
        img_back = findViewById(R.id.img_back);
        edit_email = findViewById(R.id.confrimPass);
        edit_pass = findViewById(R.id.edit_pass);
        txtForget = findViewById(R.id.txtForget);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.loginBtn);
        cbRemember = findViewById(R.id.cbRemember);
    }
}