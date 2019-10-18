package com.example.searchmoviesapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    static RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ArrayList<Movie> favList;
        setTitle("Favourites!");

        if(getIntent()!=null && getIntent().getExtras() !=null ){
            favList = new ArrayList<>();

            favList = (ArrayList<Movie>) getIntent().getExtras().getSerializable("favList");

//            Log.i("test","fav movie activity" + favList);
            recyclerView1 = (RecyclerView) findViewById(R.id.fav_recycler_view);
            recyclerView1.setHasFixedSize(true);

            layoutManager1 = new LinearLayoutManager(this);
            recyclerView1.setLayoutManager(layoutManager1);
            mAdapter1 = new favAdapter(favList);
            mAdapter1.notifyDataSetChanged();
            recyclerView1.setAdapter(mAdapter1);
            mAdapter1.notifyDataSetChanged();

        }
    }
}
