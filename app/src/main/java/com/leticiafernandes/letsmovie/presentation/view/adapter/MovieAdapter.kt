package com.leticiafernandes.letsmovie.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.view.viewholder.MovieViewHolder

/**
 * Created by leticiafernandes on 25/05/18.
 */
class MovieAdapter(private val funFavouriteClickListener: (Movie) -> Unit,
                   private val funItemClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList: MutableList<Movie>? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movieList?.get(position)!!, funFavouriteClickListener, funItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (movieList != null) movieList!!.size else 0
    }

    fun addAll(movies: List<Movie>) {
        movieList?.addAll(movies)
    }
}