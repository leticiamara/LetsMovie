package com.leticiafernandes.letsmovie.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.entity.Movie
import com.leticiafernandes.letsmovie.presentation.view.viewholder.MovieViewHolder

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList: List<Movie>? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movieList?.get(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (movieList != null) movieList!!.size else 0
    }
}