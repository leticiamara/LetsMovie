package com.leticiafernandes.letsmovie.presentation.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.extensions.formatToReleaseDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie, clickListener: (Movie) -> Unit) {
        itemView.textMovieTitle.text = movie.title
        itemView.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        itemView.textGenre.text = movie.genreIds.toList().toString()
        itemView.textVoteAverage.text = movie.voteAverage.toString()
        itemView.fabFavourite.setOnClickListener{clickListener(movie)}
        loadImage(movie.posterPath)
        if (movie.favourite) itemView.fabFavourite.visibility = View.GONE
    }

    private fun loadImage(imageURL: String) {
        val baseUrl = "https://image.tmdb.org/t/p/original"
        Picasso.with(itemView?.context)
                .load(baseUrl + imageURL)
                .centerCrop()
                .fit()
                .into(itemView?.imagePoster)
    }
}