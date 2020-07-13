package com.leticiafernandes.movie.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.presentation.viewholder.MovieViewHolder

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
