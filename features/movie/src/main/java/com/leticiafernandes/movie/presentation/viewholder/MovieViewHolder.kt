package com.leticiafernandes.movie.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.leticiafernandes.movie.domain.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie, funFavouriteClickListener: (Movie) -> Unit,
                  funItemClickListener: (Movie) -> Unit) {
        itemView.textMovieTitle.text = movie.title
        //TODO itemView.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        itemView.textGenre.text = movie.genres.toString()
        itemView.textVoteAverage.text = movie.voteAverage.toString()
        itemView.fabFavourite.setOnClickListener{ funFavouriteClickListener(movie) }
        itemView.setOnClickListener { funItemClickListener(movie) }
        // TODO loadImage(itemView.context, movie.posterPath, itemView.imagePoster!!)
        if (movie.favourite) itemView.fabFavourite.visibility = View.GONE
    }
}
