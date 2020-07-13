package com.leticiafernandes.movie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.presentation.adapter.MoviesAdapter
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
        moviesViewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
            showMovies(movies)
        })
    }

    private fun showMovies(movies: List<Movie>) {
        movieAdapter.addAll(movies)
        movieAdapter.notifyDataSetChanged()
    }

    private fun showNextPage(results: List<Movie>) {
        val adapterSize = movieAdapter.itemCount
        movieAdapter.addAll(results)
        movieAdapter.notifyItemRangeInserted(adapterSize, adapterSize.plus(results.size).minus(1))
    }

    private fun showMessage(resource: Int) {
        Toast.makeText(activity, activity?.getString(resource), Toast.LENGTH_SHORT).show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerViewMovies.layoutManager = linearLayoutManager
        recyclerViewMovies.adapter = movieAdapter
        recyclerViewMovies.addOnScrollListener(InfiniteScrollListener({ loadMoreMovies() }, linearLayoutManager))
    }

    private fun favouriteClickListener(): (Movie) -> Unit {
        return { movie ->
            //TODO moviesViewModel.addMovieToFavoriteList(movie)
        }
    }

    private fun showMovieDetails(): (Movie) -> Unit {
        return { movie ->
//            val intent = Intent(activity, MovieDetailActivity::class.java)
//            intent.putExtra(KEY_MOVIE, movie)
//            startActivity(intent)
        }
    }

    private fun loadPopularList() {
        moviesViewModel.listPopularMovies()
    }

    private fun loadMoreMovies() {
        //moviesViewModel.listNextPage()
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }
}
