package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.presentation.presenter.IMovieDetailPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.MovieDetailPresenter
import com.leticiafernandes.letsmovie.presentation.util.MovieImageUtils.Companion.loadImage
import com.leticiafernandes.letsmovie.presentation.view.fragment.PopularMoviesFragment
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMovieDetailsMvpView
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), IMovieDetailsMvpView {


    private var detailsPresenter: IMovieDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        detailsPresenter = MovieDetailPresenter(this)

        val movie = intent.getParcelableExtra<Movie>(PopularMoviesFragment.KEY_MOVIE)
        showMovieDetails(movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showMovieDetails(movie: Movie) {
        //drawableFromUrl(this, movie.backdropPath, toolbar)
        detailsPresenter?.loadImageFromURL(movie.backdropPath)
        loadImage(this, movie.posterPath, imagePoster)
        textMovieTitle.text = movie.title
        textMovieVoteAverage.text = movie.voteAverage.toString()
        textGenre.text = movie.genreIds.toString()
        textReleaseDate.text = movie.releaseDate.formatToReleaseDate()
        textOverview.text = movie.overview
    }

    override fun setBackdrop(bitmapDrawable: BitmapDrawable) {
        toolbar.background = bitmapDrawable
    }
}
