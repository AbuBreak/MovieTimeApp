package com.malikproject.newsapp.models;

import java.util.List;

public interface OnFetchDataListener<NewsResponse> {

    void onFetchData(List<News> list, String message);
    void onError(String message);
}
