package com.example.movietimeapp.models;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movietimeapp.R;
import com.example.movietimeapp.activity.MovieActivity;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    List<Movie> movieList = new ArrayList<>();

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page_recycler, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.MovieImg.setImageResource(R.drawable.img);
        holder.Title.setText(movie.getTitle());
        holder.BriefDes.setText(movie.getBriefDes());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView MovieImg;
        public TextView Title, BriefDes ,txtUser;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            MovieImg = itemView.findViewById(R.id.MovieImg);
            Title = itemView.findViewById(R.id.txtMovie);
            BriefDes = itemView.findViewById(R.id.txtBrief);
            txtUser = itemView.findViewById(R.id.txtName);

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
