package com.example.cs388_project_4_flixsterp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val movieList: MutableList<Movie> = mutableListOf()
    private val otherMovieList: MutableList<OtherMovie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // API REQUEST for RecyclerViewTop
        val recyclerViewTop = findViewById<RecyclerView>(R.id.recyclerviewTop)
        val layoutManagerTop = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTop.layoutManager = layoutManagerTop
        val adapterTop = MovieAdapter(this, movieList)
        recyclerViewTop.adapter = adapterTop
        makeApiRequestForRecyclerViewTop(adapterTop)

        // API REQUEST for RecyclerViewBottom
        val recyclerViewBottom = findViewById<RecyclerView>(R.id.recyclerviewBottom)
        val layoutManagerBottom = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewBottom.layoutManager = layoutManagerBottom
        val adapterBottom = MovieAdapterBottom(this, otherMovieList)
        recyclerViewBottom.adapter = adapterBottom
        makeApiRequestForRecyclerViewBottom(adapterBottom)
    }

    private fun makeApiRequestForRecyclerViewBottom(adapter: MovieAdapterBottom) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val results = json.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val movieData = results.getJSONObject(i)
                        val originalTitle = movieData.getString("original_title")
                        val rating = movieData.getString("vote_average")
                        val description = movieData.getString("overview")
                        val imageUrl = "https://image.tmdb.org/t/p/original" + movieData.getString("poster_path")
                        val movie = OtherMovie(originalTitle, rating, description, imageUrl)
                        otherMovieList.add(movie)
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error: VolleyError ->
                error.printStackTrace()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest )
    }

    private fun makeApiRequestForRecyclerViewTop(adapter: MovieAdapter) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val results = json.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val movieData = results.getJSONObject(i)
                        val originalTitle = movieData.getString("name")
                        val rating = movieData.getString("vote_average")
                        val imageUrl = "https://image.tmdb.org/t/p/original" + movieData.getString("poster_path")
                        val movie = Movie(originalTitle, rating, imageUrl)
                        movieList.add(movie)
                    }
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error: VolleyError ->
                error.printStackTrace()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest )
    }

}
