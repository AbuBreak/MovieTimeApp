package com.example.movietimeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MovieAdapter;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.News;
import com.example.movietimeapp.models.NewsResponse;
import com.example.movietimeapp.models.OnFetchDataListener;
import com.example.movietimeapp.models.Register;
import com.example.movietimeapp.models.RequestManager;
import com.example.movietimeapp.models.SelectListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements SelectListener {
    RecyclerView cardRecycler;
    MovieAdapter adapter;
    ImageView img_back;
    TextView txtUser;
    ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);

        cardRecycler = findViewById(R.id.recyclerView);
        img_back = findViewById(R.id.img_back);
        txtUser = findViewById(R.id.txtUser);

        auth=FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID =user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Register registerUser = snapshot.getValue(Register.class);
                if (registerUser!=null){
                    String name = registerUser.getUsername();

                    txtUser.setText("Welcome ".concat(name));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePageActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });



        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles..");
        dialog.show();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
                onBackPressed();
            }
        });

    }

    private final OnFetchDataListener<NewsResponse> listener = new OnFetchDataListener<NewsResponse>() {
        @Override
        public void onFetchData(List<News> list, String message) {
            showNews(list);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
        }
    };

    private void showNews(List<News> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cardRecycler.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(this, list, this);
        cardRecycler.setAdapter(adapter);
        cardRecycler.setHasFixedSize(true);
    }


    @Override
    public void ShowNews(News headlines) {
        startActivity(new Intent(HomePageActivity.this, MovieActivity.class)
                .putExtra("news", headlines));
    }
}