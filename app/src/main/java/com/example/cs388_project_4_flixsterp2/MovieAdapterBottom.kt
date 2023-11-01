package com.example.cs388_project_4_flixsterp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapterBottom (private val context: Context, private val movieList: List<OtherMovie>) :
    RecyclerView.Adapter<MovieAdapterBottom.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_banner_landscape, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)

        fun bind(movie: OtherMovie) {
            titleTextView.text = movie.originalTitle
            ratingTextView.text = "Rating: ${movie.rating}"

            // Limit the description to 30 characters
            val description = movie.description
            descriptionTextView.text = if (description.length > 30) {
                "${description.substring(0, 30)}..."
            } else {
                description
            }

            Glide.with(context)
                .load(movie.imageUrl)
                .into(posterImageView)
        }
    }
}