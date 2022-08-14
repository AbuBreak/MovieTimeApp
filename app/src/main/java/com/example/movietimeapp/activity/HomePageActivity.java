package com.example.movietimeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movietimeapp.R;
import com.example.movietimeapp.models.MovieAdapter;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.News;
import com.example.movietimeapp.models.NewsResponse;
import com.example.movietimeapp.models.OnFetchDataListener;
import com.example.movietimeapp.models.RequestManager;
import com.example.movietimeapp.models.SelectListener;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements SelectListener {
    RecyclerView cardRecycler;
    MovieAdapter adapter;
    ImageView img_back;
    TextView txtUser;
    MyPreference pref;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);

        cardRecycler = findViewById(R.id.recyclerView);
        img_back = findViewById(R.id.img_back);
        txtUser = findViewById(R.id.txtUser);
        pref = new MyPreference(this);

        pref.getData("ActiveUser");
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        txtUser.setText("Welcome ".concat(user));

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles..");
        dialog.show();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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