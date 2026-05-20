package com.leticiafernandes.movie.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.leticiafernandes.movie.BuildConfig.MOVIE_IMAGE_BASE_URL
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.databinding.ItemMovie2Binding
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.extensions.toStringGenres
import com.leticiafernandes.movie.presentation.model.MovieItem

class MovieViewHolder(private val binding: ItemMovie2Binding) : RecyclerView.ViewHolder(binding.root) {

    fun bindMovie(movie: MovieItem, funFavouriteClickListener: (MovieItem) -> Unit,
                  funItemClickListener: (MovieItem) -> Unit) {
        binding.textMovieTitle.text = movie.title
        binding.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        binding.textGenre.text = movie.genres.toStringGenres()
        binding.textVoteAverage.text = movie.voteAverage.toString()
        binding.buttonMyList.setOnClickListener { funFavouriteClickListener(movie) }
        binding.root.setOnClickListener { funItemClickListener(movie) }
        binding.imageMoviePoster.load(MOVIE_IMAGE_BASE_URL + movie.posterPath) {
            placeholder(R.drawable.ic_popcorn)
        }
        if (movie.favourite) binding.buttonMyList.visibility = View.GONE
    }
}
