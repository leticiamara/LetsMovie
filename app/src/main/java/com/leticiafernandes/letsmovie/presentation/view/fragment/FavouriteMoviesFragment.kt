package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.presenter.FavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.IFavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.view.activity.MovieDetailActivity
import com.leticiafernandes.letsmovie.presentation.view.adapter.MovieAdapter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView
import kotlinx.android.synthetic.main.fragment_favourite_movies.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class FavouriteMoviesFragment : Fragment(), IFavouriteMvpView {

    var favouriteMoviesPresenter: IFavouriteMoviesPresenter? = null
    var movieAdapter: MovieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteMoviesPresenter = FavouriteMoviesPresenter(this.context!!, this)
        setUpRecyclerView()
        loadFavouriteList()
    }

    fun loadFavouriteList() {
        favouriteMoviesPresenter?.listAllFavouriteMovies()
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(activity, resId, LENGTH_SHORT).show()
    }

    override fun listMovies(movies: List<Movie>?) {
        movieAdapter?.movieList = movies
        movieAdapter?.notifyDataSetChanged()
    }

    private fun setUpRecyclerView() {
        rvFavouriteMovies.layoutManager = LinearLayoutManager(activity)
        movieAdapter = MovieAdapter({}, showMovieDetails())
        rvFavouriteMovies.adapter = movieAdapter
    }

    private fun showMovieDetails(): (Movie) -> Unit {
        return { movie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(PopularMoviesFragment.KEY_MOVIE, movie)
            startActivity(intent)
        }
    }

}