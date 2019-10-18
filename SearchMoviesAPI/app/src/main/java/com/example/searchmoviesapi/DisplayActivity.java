package com.example.searchmoviesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {
    TextView tv_title, tv_reldate, textView3, et_multi;
    ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_reldate = (TextView) findViewById(R.id.tv_reldate);
        textView3 = (TextView) findViewById(R.id.textView3);
        et_multi = (TextView) findViewById(R.id.et_multi);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Movie movie = (Movie) getIntent().getExtras().getSerializable("movieObj");
            tv_title.setText(movie.mtitle);
            tv_reldate.setText("Released Date: " + movie.releaseDate);
            et_multi.setText("Overview: " + movie.moverView);
            textView3.setText("Rating: " + Integer.valueOf(movie.rating).toString());
            String urls = "";
            urls = movie.imageURL + movie.width + movie.poster_path;
            Picasso.get().load(urls).into(imageView2);

        }
    }

}
