package com.example.movietimeapp.models;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
private Context context;
private List<News> headlines;

    public MovieAdapter(Context context, List<News> headlines) {
        this.context = context;
        this.headlines = headlines;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(v.getContext(), MovieActivity.class);
                    intent.putExtra("name",Title.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
