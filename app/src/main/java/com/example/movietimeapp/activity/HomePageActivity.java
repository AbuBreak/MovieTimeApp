package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.Movie;
import com.example.movietimeapp.models.MovieAdapter;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.Register;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    RecyclerView cardRecycler;
    List<Movie> movieList = new ArrayList<>();
    MovieAdapter adapter;
    ImageView img_back;
    TextView txtUser;
    MyPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cardRecycler = findViewById(R.id.recyclerView);
        img_back = findViewById(R.id.img_back);
        txtUser = findViewById(R.id.txtUser);
        pref = new MyPreference(this);

        pref.getData("ActiveUser");
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        txtUser.setText("Welcome ".concat(user));

        adapter = new MovieAdapter(movieList);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cardRecycler.setLayoutManager(layoutManager);

        cardRecycler.setItemAnimator(new DefaultItemAnimator());
        cardRecycler.setAdapter(adapter);

        InsertData();


    }

    private void InsertData() {
        Movie movie = new Movie(R.drawable.img, "Marvel", "This is a brief description about marvel");
        movieList.add(movie);

        Movie movie1 = new Movie(R.drawable.img, "Carter", "This is a brief description about movie");
        movieList.add(movie1);

        adapter.notifyDataSetChanged();
    }
}