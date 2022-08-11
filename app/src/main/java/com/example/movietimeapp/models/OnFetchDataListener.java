package com.example.movietimeapp.models;

import java.util.List;

public interface OnFetchDataListener<NewsResponse> {

    void onFetchData(List<News> list, String message);
    void onError(String message);
}
