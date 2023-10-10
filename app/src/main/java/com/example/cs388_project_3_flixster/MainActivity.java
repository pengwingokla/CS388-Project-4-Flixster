package com.example.cs388_project_3_flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();

        MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieList);
        recyclerView.setAdapter(adapter);

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        fetchMovies();

    }

    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONObject(response).getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject jsonObject = array.getJSONObject(i);
                                String title = jsonObject.getString("original_title");
                                String overview = jsonObject.getString("overview");
                                String posterPath = jsonObject.getString("poster_path");
                                Double rating = jsonObject.getDouble("vote_average");

                                Movie movie = new Movie(title, posterPath, overview, rating);
                                movieList.add(movie);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieList);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the error for debugging
                Log.e("VolleyError", "Error: " + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }
}