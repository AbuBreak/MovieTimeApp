package com.example.movietimeapp.models;

import java.util.List;

public interface OnFetchDataListener {
    void onFetchData(List<NewsHeadline> list , String message);
    void onError(String message);
}
