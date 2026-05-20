package com.leticiafernandes.letsmovie.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leticiafernandes.letsmovie.databinding.ItemMovieBinding
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.view.viewholder.MovieViewHolder

class MovieAdapter(private val funFavouriteClickListener: (Movie) -> Unit,
                   private val funItemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList: MutableList<Movie>? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movieList?.get(position)!!, funFavouriteClickListener, funItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (movieList != null) movieList!!.size else 0
    }

    fun addAll(movies: List<Movie>) {
        movieList?.addAll(movies)
    }
}
