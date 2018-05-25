package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieEntity
import com.leticiafernandes.letsmovie.presentation.presenter.FavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.IFavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.view.adapter.FavouriteMoviesAdapter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView
import kotlinx.android.synthetic.main.fragment_favourite_movies.*
import kotlinx.android.synthetic.main.fragment_popular_movies.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class FavouriteMoviesFragment : Fragment(), IFavouriteMvpView {

    var favouriteMoviesPresenter: IFavouriteMoviesPresenter? = null
    var favouriteMoviesAdapter: FavouriteMoviesAdapter? = null

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

    private fun setUpRecyclerView() {
        rvFavouriteMovies.layoutManager = LinearLayoutManager(activity)
        favouriteMoviesAdapter = FavouriteMoviesAdapter()
        rvFavouriteMovies.adapter = favouriteMoviesAdapter
    }

    private fun loadFavouriteList() {
        favouriteMoviesPresenter?.listAllFavouriteMovies()
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(activity, resId, LENGTH_SHORT).show()
    }

    override fun listMovies(movies: List<MovieEntity>?) {
        favouriteMoviesAdapter?.favouriteMovieList = movies
        favouriteMoviesAdapter?.notifyDataSetChanged()
    }

}