package com.leticiafernandes.movie.presentation.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.ImageLoader
import coil.api.load
import coil.request.GetRequest
import coil.request.LoadRequest
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.extensions.toMovieAPIImageURL
import com.leticiafernandes.movie.presentation.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_detail2.*
import javax.inject.Inject

const val EXTRA_KEY_MOVIE = "EXTRA_KEY_MOVIE"

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail2)
        setSupportActionBar(toolbarMovieDetail)
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
            }
        })
    }

    private fun showMovieInfo(movie: MovieItem) {
        toolbarMovieBackdrop.load(movie.backdropPath?.toMovieAPIImageURL())
        imagePoster.load(movie.posterPath?.toMovieAPIImageURL())
        textMovieTitle.text = movie.title
        textMovieVoteAverage.text = movie.voteAverage.toString()
        textGenre.text = movie.genres.toString()
        textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        textOverview.text = movie.overview
    }
}
