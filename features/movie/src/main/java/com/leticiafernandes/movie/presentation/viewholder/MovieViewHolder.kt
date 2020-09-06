package com.leticiafernandes.movie.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.leticiafernandes.movie.BuildConfig.MOVIE_IMAGE_BASE_URL
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.extensions.toStringGenres
import com.leticiafernandes.movie.presentation.model.MovieItem
import kotlinx.android.synthetic.main.item_movie2.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: MovieItem, funFavouriteClickListener: (MovieItem) -> Unit,
                  funItemClickListener: (MovieItem) -> Unit) {
        itemView.textMovieTitle.text = movie.title
        itemView.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        itemView.textGenre.text = movie.genres.toStringGenres()
        itemView.textVoteAverage.text = movie.voteAverage.toString()
        itemView.buttonMyList.setOnClickListener { funFavouriteClickListener(movie) }
        itemView.setOnClickListener { funItemClickListener(movie) }
        itemView.imageMoviePoster.load(MOVIE_IMAGE_BASE_URL + movie.posterPath) {
            placeholder(R.drawable.ic_popcorn)
        }
        if (movie.favourite) itemView.buttonMyList.visibility = View.GONE
    }
}
