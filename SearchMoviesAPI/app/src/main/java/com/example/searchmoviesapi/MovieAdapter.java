package com.example.searchmoviesapi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> MovieData;


    public MovieAdapter(ArrayList<Movie> MovieData) {
        this.MovieData = MovieData;

    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_movie, parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ViewHolder holder, int position) {
        final Movie nsObject =MovieData.get(position);
        holder.tv_mname.setText(nsObject.mtitle);
        holder.tv_date.setText(nsObject.releaseDate);
        String urls = "";
         urls = nsObject.imageURL +nsObject.width+nsObject.poster_path;
        Picasso.get().load(urls).into(holder.imageView);
        holder.movieDetails = nsObject;

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nsObject.favourite == "true") {
                    nsObject.favourite = "false";
                    holder.imageButton.setImageResource(R.drawable.heart);
                    MainActivity.favList.remove(nsObject);
                } else {
                    nsObject.favourite = "true";
                    holder.imageButton.setImageResource(R.drawable.heart1);
                    MainActivity.favList.add(nsObject);
                    Log.i("test", "added " );

                }
                MainActivity.mAdapter.notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return MovieData.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_mname,tv_date;
        ImageView imageView;
        Movie movieDetails;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mname =itemView.findViewById(R.id.tv_mname);
            tv_date = itemView.findViewById(R.id.tv_date);
            imageView = itemView.findViewById(R.id.imageView);
            imageButton = itemView.findViewById(R.id.imageButton);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context= v.getContext();
                    Intent intent = new Intent(context,DisplayActivity.class);
                    intent.putExtra("movieObj", movieDetails);
                    context.startActivity(intent);

                }
            });

        }
    }
}
