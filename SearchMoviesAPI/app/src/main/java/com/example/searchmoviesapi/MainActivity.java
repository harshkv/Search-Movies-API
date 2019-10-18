package com.example.searchmoviesapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    TextView tv_title, tv_result;
    EditText et_name;
    Button searchButton;
    static ArrayList<Movie> MoviesList;
    static ArrayList<Movie> favList;

    private RecyclerView recyclerView;
    static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button) findViewById(R.id.searchButton);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result.setVisibility(View.GONE);
        MoviesList = new ArrayList<>();
        favList = new ArrayList<>();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    MoviesList = new ArrayList<>();
                    favList = new ArrayList<>();
                    String movieName = et_name.getText().toString();
                    movieName.trim();
                    movieName = movieName.replaceAll(" ", "+").toLowerCase();
                    new GetDataAsync().execute("https://api.themoviedb.org/3/search/movie?query=" + movieName + "&api_key=2ff880201e2022b36e41b4b8a047340a&page=1");

                } else {
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                }

            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.rating) {
            if (MoviesList.size() > 0) {
                Collections.sort(MoviesList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return o2.rating - o1.rating;
                    }


                });
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No results Found", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.quit) {
            finish();
        }
        if (id == R.id.favourites) {
            if (MoviesList.size() > 0) {
                if (favList.size() > 0) {
                    Log.i("demo","fav count" + favList.size());
                    Intent favIntent = new Intent(MainActivity.this, FavouritesActivity.class);
                    favIntent.putExtra("favList" , favList);
                    startActivity(favIntent);

                }

            }


        }
        if (id == R.id.pop) {
            if (MoviesList.size() > 0) {
                Collections.sort(MoviesList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return o2.popularity - o1.popularity;
                    }


                });
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No results Found", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInformation = connectivityManager.getActiveNetworkInfo();

        if (networkInformation == null || !networkInformation.isConnected() || (networkInformation.getType() != connectivityManager.TYPE_WIFI &&
                networkInformation.getType() != connectivityManager.TYPE_MOBILE)) {
            return false;
        }

        return true;
    }


    public class GetDataAsync extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String result = null;
            HttpURLConnection httpConnection = null;
            try {
                URL url = new URL(strings[0]);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect();
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result = IOUtils.toString(httpConnection.getInputStream(), "UTF8");
                    JSONObject root = new JSONObject(result);
                    JSONArray results = root.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movieJSON = results.getJSONObject(i);
                        Movie movieObj = new Movie();
                        movieObj.mtitle = movieJSON.getString("original_title");
                        movieObj.moverView = movieJSON.getString("overview");
                        movieObj.releaseDate = movieJSON.getString("release_date");
                        movieObj.rating = movieJSON.getInt("vote_average");
                        movieObj.popularity = movieJSON.getInt("popularity");
                        movieObj.setImageURL("http://image.tmdb.org/t/p/");
                        movieObj.poster_path = movieJSON.getString("poster_path");
                        movieObj.setWidth("w154");
                        MoviesList.add(movieObj);
                    }

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();

                }
            }
            return MoviesList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            if (MoviesList.size() > 0) {
                Log.i("demo", "Movies searched for " + movies.get(0).mtitle);
                tv_result.setVisibility(View.VISIBLE);

                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                mAdapter = new MovieAdapter(movies);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

            } else {
                Toast.makeText(MainActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
            }

        }


    }

}
