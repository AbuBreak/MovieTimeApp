package com.example.movietimeapp.models;

import android.widget.ImageView;

public class Movie {
    private int movieImg;
    private String title;
    private String briefDes;


    public Movie(int movieImg, String title, String briefDes) {
        this.movieImg = movieImg;
        this.title = title;
        this.briefDes = briefDes;

    }

    public int getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(int movieImg) {
        this.movieImg = movieImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefDes() {
        return briefDes;
    }

    public void setBriefDes(String briefDes) {
        this.briefDes = briefDes;
    }

}
