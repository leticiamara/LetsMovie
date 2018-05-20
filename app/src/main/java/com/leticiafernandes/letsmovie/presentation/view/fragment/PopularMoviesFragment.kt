package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.entity.Movie
import com.leticiafernandes.letsmovie.presentation.presenter.IMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.MoviesPresenter
import com.leticiafernandes.letsmovie.presentation.view.adapter.MoviesAdapter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMoviesMvpView
import kotlinx.android.synthetic.main.fragment_popular_movies.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class PopularMoviesFragment : Fragment(), IMoviesMvpView {

    var movieAdapter: MoviesAdapter? = null
    var moviesPresenter: IMoviesPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesPresenter = MoviesPresenter(this)
        setUpRecyclerView()
        loadPopularList()
    }

    override fun showPopularMovieList(movieList: List<Movie>?) {
        movieAdapter?.movieList = movieList
        movieAdapter?.notifyDataSetChanged()
    }

    override fun showMessage(resource: Int) {
        Toast.makeText(activity, activity?.getString(resource), Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        rvPopularMovies.layoutManager = LinearLayoutManager(activity)
        movieAdapter = MoviesAdapter()
        rvPopularMovies.adapter = movieAdapter
    }

    private fun loadPopularList() {
        moviesPresenter?.listPopularMovies()
    }
}