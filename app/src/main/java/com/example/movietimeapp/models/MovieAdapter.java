package com.example.movietimeapp.models;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movietimeapp.R;
import com.example.movietimeapp.activity.MovieActivity;

import java.io.Serializable;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
private Context context;
private List<News> headlines;
private SelectListener listener;

    public MovieAdapter(Context context, List<News> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page_recycler, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
            holder.Title.setText(headlines.get(position).getTitle());
            holder.BriefDes.setText(headlines.get(position).getSource().getName());

            if(headlines.get(position).getUrlToImage()!=null){
                Glide.with(context)
                        .asBitmap()
                        .load(headlines.get(position).getUrlToImage())
                        .into(holder.MovieImg);
            }
            holder.BriefDes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ShowNews(headlines.get(position));
                }
            });

    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView MovieImg;
        public TextView Title, BriefDes ,txtUser;
        public CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            MovieImg = itemView.findViewById(R.id.MovieImg);
            Title = itemView.findViewById(R.id.txtMovie);
            BriefDes = itemView.findViewById(R.id.txtBrief);
            txtUser = itemView.findViewById(R.id.txtName);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }

}
