package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;

public class CreatePassActivity extends AppCompatActivity {
    private ImageView img_back;
    private EditText new_pass, confirm_pass;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        initViews();
        MyPreference pref = new MyPreference(this);

        Intent intent = getIntent();
        Register register = intent.getParcelableExtra("user");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new_pass.getText().toString().equals(pref.getData(register.getEmail()).getPassword())) {
                    Toast.makeText(CreatePassActivity.this, "You cannot enter an old password!", Toast.LENGTH_SHORT).show();

                } else if (!new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                    Toast.makeText(CreatePassActivity.this, "Password dose not match, Please check!", Toast.LENGTH_SHORT).show();

                } else if (new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                    pref.saveData(register.getEmail(), new Register(register.getUsername(), register.getEmail(), new_pass.getText().toString()));
                    Intent intent = new Intent(CreatePassActivity.this, ConfirmResetActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {

        img_back = findViewById(R.id.img_back);
        new_pass = findViewById(R.id.new_pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        btnReset = findViewById(R.id.btnBack);
    }
}