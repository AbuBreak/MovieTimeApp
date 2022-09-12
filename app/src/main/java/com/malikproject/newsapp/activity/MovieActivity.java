package com.malikproject.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malikproject.newsapp.R;
import com.malikproject.newsapp.models.News;

public class MovieActivity extends AppCompatActivity {
ImageView img_back,img_news;
TextView txtName,txtContent;
News headlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        txtName=findViewById(R.id.txtName);
        txtContent=findViewById(R.id.txtContent);
        img_news=findViewById(R.id.img_news);

        headlines = (News) getIntent().getSerializableExtra("news");

        txtName.setText(headlines.getTitle());
        txtContent.setText(headlines.getContent());
        Glide.with(this).asBitmap().load(headlines.getUrlToImage()).into(img_news);

    }
}