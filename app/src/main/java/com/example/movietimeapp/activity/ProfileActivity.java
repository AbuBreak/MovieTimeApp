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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtUsername, txtEmail;
    private Button btnChange,btnLocation;
    private ImageView img_back, img_profile;

    private final int STORAGE_PERMISSION_CODE = 165;
    private final int CAMERA_PERMISSION_CODE = 564;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Uri imageUri;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initViews();
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        storage = FirebaseStorage.getInstance();
        mStorage = storage.getReference();

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


        btnChange.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("Change Profile Pic ")
                    .setMessage("Choose picture from: ")
                    .setPositiveButton("Camera", (dialogInterface, i) -> openCamera())
                    .setNegativeButton("Gallery", (dialogInterface, i) -> openGallery())
                    .create().show();
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == CAMERA_PERMISSION_CODE) {
                openCamera();
                Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                imageUri = getImageUri(this, photo);
                img_profile.setImageURI(imageUri);

               /* ImagePicker.Companion.with(this)
                        .galleryMimeTypes(new String[]{"image/*"})
                        .cameraOnly()
                        .compress(1024)
                        .start(result.getResultCode());*/
            } else if (result.getResultCode() == STORAGE_PERMISSION_CODE) {
                openGallery();
                Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                imageUri = getImageUri(this, photo);
                img_profile.setImageURI(imageUri);

                /*ImagePicker.Companion.with(this)
                        .galleryMimeTypes(new String[]{"image/*"})
                        .galleryOnly()
                        .compress(1024)
                        .start(result.getResultCode());*/
            } else {
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });
        img_back.setOnClickListener(v -> onBackPressed());
        btnLocation.setOnClickListener(v->
        {
            startActivity(new Intent(ProfileActivity.this,MapActivity.class));
        });
    }

    private void openCamera() {
        ImagePicker.Companion.with(this)
                .cameraOnly()
                .compress(1024)
                .start(CAMERA_PERMISSION_CODE);
    }

    private void openGallery() {
        ImagePicker.Companion.with(this)
                .galleryMimeTypes(new String[]{"image/*"})
                .galleryOnly()
                .compress(1024)
                .start(STORAGE_PERMISSION_CODE);
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage() {
        if (!img_profile.toString().isEmpty()) {
            mStorage.child("image/" + UUID.randomUUID().toString());
            mStorage.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {
        txtUsername = (TextView) findViewById(R.id.txt_username);
        txtEmail = (TextView) findViewById(R.id.txt_userEmail);
        btnChange = (Button) findViewById(R.id.btnChange);
        btnLocation = (Button) findViewById(R.id.btnLocation);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_profile = (ImageView) findViewById(R.id.img_profile);

    }


    /*@Override
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
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                Intent openGallery = new Intent(Intent.ACTION_PICK);
                openGallery.setType("image/*");
                activityResultLauncher.launch(openGallery);
            } else {
                Toast.makeText(ProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
    }*/
}