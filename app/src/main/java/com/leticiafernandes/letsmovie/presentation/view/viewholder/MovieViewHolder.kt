package com.leticiafernandes.letsmovie.presentation.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.presentation.util.MovieImageUtils.Companion.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie, funFavouriteClickListener: (Movie) -> Unit,
                  funItemClickListener: (Movie) -> Unit) {
        itemView.textMovieTitle.text = movie.title
        itemView.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        itemView.textGenre.text = movie.genres
        itemView.textVoteAverage.text = movie.voteAverage.toString()
        itemView.fabFavourite.setOnClickListener{ funFavouriteClickListener(movie) }
        itemView.setOnClickListener { funItemClickListener(movie) }
        loadImage(itemView.context, movie.posterPath, itemView.imagePoster!!)
        if (movie.favourite) itemView.fabFavourite.visibility = View.GONE
    }
}