package com.example.movietimeapp.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.Register;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtUsername, txtEmail;
    private Button btnChange, btnLocation;
    private ImageView img_back, img_profile;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference mStorage;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference("image/" + UUID.randomUUID().toString());

        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("CurrentUser");

        reference.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Register registeredUser = snapshot.getValue(Register.class);


                if (registeredUser != null) {
                    String name = registeredUser.getUsername();
                    String email = registeredUser.getEmail();

                    txtUsername.setText(name);
                    txtEmail.setText(email);

                } else {
                    Toast.makeText(ProfileActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnLocation.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_profile.setOnClickListener(this);

    }

    private void initViews() {
        txtUsername = (TextView) findViewById(R.id.txt_username);
        txtEmail = (TextView) findViewById(R.id.txt_userEmail);
        btnChange = (Button) findViewById(R.id.btnChange);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_profile = (ImageView) findViewById(R.id.img_profile);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChange:
                Toast.makeText(this, "This option will be here soon, Stay tuned!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLocation:
                startActivity(new Intent(ProfileActivity.this, MapActivity.class));
                break;
            case R.id.img_back:
                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
                break;
            case R.id.img_profile:
                changeProfilePic();
                break;
        }
    }

    private void changeProfilePic() {
        ImagePicker.with(this)
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();
        if (imageUri != null) {
            img_profile.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        }

        if (imageUri != null) {
            mStorage.child("image/" + UUID.randomUUID().toString());
            mStorage.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProfileActivity.this, "ProfilePic Uploaded!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
    }
}