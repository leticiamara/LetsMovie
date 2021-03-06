package com.leticiafernandes.movie.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.presentation.adapter.MoviesAdapter
import com.leticiafernandes.movie.presentation.detail.EXTRA_KEY_MOVIE
import com.leticiafernandes.movie.presentation.detail.MovieDetailActivity
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.PagingItem
import com.leticiafernandes.movie.presentation.model.ProgressItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val movieAdapter: MoviesAdapter by lazy {
        MoviesAdapter(favouriteClickListener(), showMovieDetails())
    }

    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        loadPopularList()
        handleMovies()
    }

    private fun handleMovies() {
        moviesViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is Success -> showMovies(uiState.moviesList)
                is ShowMovieListProgress -> showMovieListProgress(uiState.progressItem)
                is HideMovieListProgress -> hideMovieListProgress()
                is Loading -> handleLoading(uiState.loading)
                is Error -> showAlertError(uiState.errorMessage)
            }
        })
    }

    private fun showMovies(movies: List<PagingItem>) {
        movieAdapter.addAll(movies)
        recyclerViewMovies.isVisible = true
    }

    private fun showMovieListProgress(progressItem: ProgressItem) {
        movieAdapter.add(progressItem)
    }

    private fun hideMovieListProgress() {
        movieAdapter.removeLastItem()
    }

    private fun handleLoading(loading: Boolean) {
        progressBarMovies.isVisible = loading
    }

    private fun showAlertError(errorMessage: String?) {
        context?.run {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle(R.string.generic_error_title)
                setMessage(errorMessage)
                setPositiveButton(R.string.error_action_ok) { dialog, _ -> dialog.dismiss() }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerViewMovies.layoutManager = linearLayoutManager
        recyclerViewMovies.adapter = movieAdapter
        recyclerViewMovies.addOnScrollListener(object : EndlessScrollEventListener(linearLayoutManager) {
            override fun onLoadMore(pageNumber: Int, recyclerView: RecyclerView) {
                loadMoreMovies()
            }

            override fun isLoadingData(): Boolean {
                return moviesViewModel.isLoading
            }
        })
    }

    private fun favouriteClickListener(): (MovieItem) -> Unit {
        return { movie ->
            //TODO moviesViewModel.addMovieToFavoriteList(movie)
        }
    }

    private fun showMovieDetails(): (MovieItem) -> Unit {
        return { movie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_KEY_MOVIE, movie)
            startActivity(intent)
        }
    }

    private fun loadPopularList() {
        moviesViewModel.listPopularMovies()
    }

    private fun loadMoreMovies() {
        moviesViewModel.listNextPage()
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }
}
