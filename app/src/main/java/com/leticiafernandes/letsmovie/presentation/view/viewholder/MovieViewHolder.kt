package com.leticiafernandes.letsmovie.presentation.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.leticiafernandes.letsmovie.infrastructure.data.entity.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie) {
        itemView.textMovieTitle.text = movie.title
        itemView.textMovieOverview.text = movie.overview
    }
}