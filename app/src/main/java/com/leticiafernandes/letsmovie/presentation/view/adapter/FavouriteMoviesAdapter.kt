package com.leticiafernandes.letsmovie.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieEntity
import com.leticiafernandes.letsmovie.presentation.view.viewholder.MovieViewHolder

/**
 * Created by leticiafernandes on 24/05/18.
 */
class FavouriteMoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    var favouriteMovieList: List<MovieEntity>? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(favouriteMovieList?.get(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (favouriteMovieList != null) favouriteMovieList!!.size else 0
    }
}