package com.leticiafernandes.movie.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.leticiafernandes.movie.databinding.ActivityMovieDetail2Binding
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.extensions.toMovieAPIImageURL
import com.leticiafernandes.movie.presentation.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_KEY_MOVIE = "EXTRA_KEY_MOVIE"

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetail2Binding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMovieDetail)
        setUpUiState()

        val movie = intent.getParcelableExtra<MovieItem>(EXTRA_KEY_MOVIE)
        movieDetailViewModel.showMovieDetails(movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpUiState() {
        movieDetailViewModel.uiState.observe(this, Observer { uiState ->
            when (uiState) {
                is ShowMovieInfo -> showMovieInfo(uiState.movie)
                ShowMovieEmptyState -> { /* no-op: empty state not handled in this view */ }
            }
        })
    }

    private fun showMovieInfo(movie: MovieItem) {
        binding.toolbarMovieBackdrop.load(movie.backdropPath?.toMovieAPIImageURL())
        binding.imagePoster.load(movie.posterPath?.toMovieAPIImageURL())
        binding.textMovieTitle.text = movie.title
        binding.textMovieVoteAverage.text = movie.voteAverage.toString()
        binding.textGenre.text = movie.genres.toString()
        binding.textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        binding.textOverview.text = movie.overview
    }
}
