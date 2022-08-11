package com.example.movietimeapp.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines (
            @Query("apiKey") String api_Key,
            @Query("country") String country,
            @Query("category") String category,
            @Query("q") String query
    );
}
