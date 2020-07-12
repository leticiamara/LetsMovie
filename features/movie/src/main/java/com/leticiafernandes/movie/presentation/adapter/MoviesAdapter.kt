package com.leticiafernandes.movies.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leticiafernandes.movies.R
import com.leticiafernandes.movies.domain.model.Movie
import com.leticiafernandes.movies.presentation.viewholder.MovieViewHolder

class MoviesAdapter(private val funFavouriteClickListener: (Movie) -> Unit,
                    private val funItemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movieList: MutableList<Movie> = mutableListOf()

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movieList[position], funFavouriteClickListener, funItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addAll(movies: List<Movie>) {
        movieList.addAll(movies)
    }
}
