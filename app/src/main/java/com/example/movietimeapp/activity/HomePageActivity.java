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
import com.example.movietimeapp.models.ApiInterface;
import com.example.movietimeapp.models.Movie;
import com.example.movietimeapp.models.MovieAdapter;
import com.example.movietimeapp.models.MyPreference;
import com.example.movietimeapp.models.NewsApiResponse;
import com.example.movietimeapp.models.OnFetchDataListener;
import com.example.movietimeapp.models.Register;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageActivity extends AppCompatActivity  {
    RecyclerView cardRecycler;
    MovieAdapter adapter;
    ImageView img_back;
    TextView txtUser;
    MyPreference pref;

    Retrofit retrofit =new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

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

        adapter = new MovieAdapter();

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

        getNewsHeadlines();


    }
    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query){
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<NewsApiResponse> call = apiInterface.callHeadlines(String.valueOf(R.string.api_key),"us",category,query);
        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(HomePageActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(),response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}