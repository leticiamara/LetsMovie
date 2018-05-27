package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.presenter.IMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.MoviesPresenter
import com.leticiafernandes.letsmovie.presentation.util.InfiniteScrollListener
import com.leticiafernandes.letsmovie.presentation.view.activity.MovieDetailActivity
import com.leticiafernandes.letsmovie.presentation.view.adapter.MovieAdapter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMoviesMvpView
import kotlinx.android.synthetic.main.fragment_popular_movies.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class PopularMoviesFragment : Fragment(), IMoviesMvpView {


    private var movieAdapter: MovieAdapter? = null
    private var moviesPresenter: IMoviesPresenter? = null

    companion object {
        val KEY_MOVIE = "movie"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesPresenter = MoviesPresenter(this.context!!, this)
        setUpRecyclerView()
        loadPopularList()
    }

    override fun showPopularMovieList(movieList: List<Movie>?) {
        movieAdapter?.movieList = movieList as MutableList<Movie>?
        movieAdapter?.notifyDataSetChanged()
    }

    override fun showNextPage(results: List<Movie>?) {
        val adapterSize = movieAdapter?.itemCount
        movieAdapter?.addAll(results!!)
        movieAdapter?.notifyItemRangeInserted(adapterSize!!, adapterSize.plus(results?.size!!).minus(1))
    }

    override fun showMessage(resource: Int) {
        Toast.makeText(activity, activity?.getString(resource), Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        rvPopularMovies.layoutManager = linearLayoutManager
        movieAdapter = MovieAdapter(addMovieToFavouriteList(), showMovieDetails())
        rvPopularMovies.adapter = movieAdapter
        rvPopularMovies.addOnScrollListener(InfiniteScrollListener({loadMoreMovies()}, linearLayoutManager))
    }

    private fun addMovieToFavouriteList(): (Movie) -> Unit {
        return { movie ->
            moviesPresenter?.addMovieToFavouriteList(movie)
        }
    }

    private fun showMovieDetails(): (Movie) -> Unit {
        return { movie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(KEY_MOVIE, movie)
            startActivity(intent)
        }
    }

    private fun loadPopularList() {
        moviesPresenter?.listPopularMovies()
    }

    private fun loadMoreMovies() {
        moviesPresenter?.listNextPage()
    }
}