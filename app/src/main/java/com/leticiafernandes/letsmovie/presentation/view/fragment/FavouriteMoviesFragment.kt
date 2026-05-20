package com.leticiafernandes.letsmovie.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.leticiafernandes.letsmovie.databinding.FragmentFavouriteMoviesBinding
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.presenter.FavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.IFavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.view.activity.MovieDetailActivity
import com.leticiafernandes.letsmovie.presentation.view.adapter.MovieAdapter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView

class FavouriteMoviesFragment : Fragment(), IFavouriteMvpView {

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding get() = _binding!!

    var favouriteMoviesPresenter: IFavouriteMoviesPresenter? = null
    var movieAdapter: MovieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteMoviesPresenter = FavouriteMoviesPresenter(this.requireContext(), this)
        setUpRecyclerView()
        loadFavouriteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadFavouriteList() {
        favouriteMoviesPresenter?.listAllFavouriteMovies()
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(activity, resId, LENGTH_SHORT).show()
    }

    override fun listMovies(movies: List<Movie>?) {
        if (movies?.isNotEmpty()!!) {
            binding.rvFavouriteMovies.visibility = View.VISIBLE
            binding.textEmptyStateFavorite.visibility = View.GONE
        } else {
            binding.rvFavouriteMovies.visibility = View.GONE
            binding.textEmptyStateFavorite.visibility = View.VISIBLE
        }
        movieAdapter?.movieList = movies as MutableList<Movie>?
        movieAdapter?.notifyDataSetChanged()
    }

    private fun setUpRecyclerView() {
        binding.rvFavouriteMovies.layoutManager = LinearLayoutManager(activity)
        movieAdapter = MovieAdapter({}, showMovieDetails())
        binding.rvFavouriteMovies.adapter = movieAdapter
    }

    private fun showMovieDetails(): (Movie) -> Unit {
        return { movie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(PopularMoviesFragment.KEY_MOVIE, movie)
            startActivity(intent)
        }
    }
}
