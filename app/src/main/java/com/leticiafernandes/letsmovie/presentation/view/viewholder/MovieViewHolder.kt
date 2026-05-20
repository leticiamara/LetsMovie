package com.leticiafernandes.letsmovie.presentation.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.leticiafernandes.letsmovie.databinding.ItemMovieBinding
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.presentation.util.MovieImageUtils.Companion.loadImage

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindMovie(movie: Movie, funFavouriteClickListener: (Movie) -> Unit,
                  funItemClickListener: (Movie) -> Unit) {
        binding.textMovieTitle.text = movie.title
        binding.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        binding.textGenre.text = movie.genres
        binding.textVoteAverage.text = movie.voteAverage.toString()
        binding.fabFavourite.setOnClickListener { funFavouriteClickListener(movie) }
        binding.root.setOnClickListener { funItemClickListener(movie) }
        loadImage(binding.root.context, movie.posterPath, binding.imagePoster)
        if (movie.favourite) binding.fabFavourite.visibility = View.GONE
    }
}
