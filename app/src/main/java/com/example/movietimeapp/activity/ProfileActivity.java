package com.example.movietimeapp.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.movietimeapp.R;
import com.example.movietimeapp.models.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtUsername, txtEmail;
    private Button btnChange;
    private ImageView img_back, img_profile;

    private final int STORAGE_PERMISSION_CODE = 165;
    private final int CAMERA_PERMISSION_CODE = 564;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initViews();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("CurrentUser");

        reference.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Register registeredUser = snapshot.getValue(Register.class);

                if (registeredUser != null) {
                    String userName = registeredUser.getUsername();
                    String userEmail = registeredUser.getEmail();

                    txtUsername.setText(userName);
                    txtEmail.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Change Profile Pic ")
                        .setMessage("Choose picture from: ")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    activityResultLauncher.launch(openCamera);
                                } else {
                                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                                }
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    Intent openGallery = new Intent();
                                    openGallery.setType("image/*");
                                    openGallery.setAction(Intent.ACTION_PICK);
                                    activityResultLauncher.launch(openGallery);
                                } else {
                                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                                }
                            }
                        })
                        .create().show();
            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                img_profile.setImageBitmap(photo);

                img_profile.setImageURI(result.getData().getData());

            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews() {
        txtUsername = (TextView) findViewById(R.id.txt_username);
        txtEmail = (TextView) findViewById(R.id.txt_userEmail);
        btnChange = (Button) findViewById(R.id.btnChange);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_profile = (ImageView) findViewById(R.id.img_profile);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(openCamera);
            } else {
                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Intent openGallery = new Intent(Intent.ACTION_PICK);
                openGallery.setType("image/*");
                activityResultLauncher.launch(openGallery);
            } else {
                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
    }
}