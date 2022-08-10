package com.example.movietimeapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
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


public class ForgetActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText forget_email;
    private TextView txtLogin;
    private Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initViews();
        MyPreference MyPrf = new MyPreference(this);

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
                    Toast.makeText(ForgetActivity.this, "Please fill in the required field", Toast.LENGTH_SHORT).show();

                } else if (!forget_email.getText().toString().equals(MyPrf.getData(forget_email.getText().toString()).getEmail())) {
                    Toast.makeText(ForgetActivity.this, "Provided email NOT found, Please check!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ForgetActivity.this, OTPActivity.class);
                    intent.putExtra("email", new Register(MyPrf.getData(forget_email.getText().toString()).getUsername()
                            ,forget_email.getText().toString(), MyPrf.getData(forget_email.getText().toString()).getPassword()));
                    startActivity(intent);
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