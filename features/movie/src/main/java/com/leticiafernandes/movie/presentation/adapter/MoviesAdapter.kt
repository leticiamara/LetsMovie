package com.leticiafernandes.movie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leticiafernandes.movie.R
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.PagingItem
import com.leticiafernandes.movie.presentation.model.ProgressItem
import com.leticiafernandes.movie.presentation.viewholder.LoadingViewHolder
import com.leticiafernandes.movie.presentation.viewholder.MovieViewHolder

private const val VIEW_TYPE_MOVIE = 0
private const val VIEW_TYPE_LOADING = 1
private const val ONE_ITEM_COUNT = 1

class MoviesAdapter(
        private val funFavouriteClickListener: (MovieItem) -> Unit,
        private val funItemClickListener: (MovieItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieList: MutableList<PagingItem> = mutableListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            movieList[position].let {
                if (it is MovieItem) {
                    holder.bindMovie(it, funFavouriteClickListener, funItemClickListener)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MOVIE) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movie2, parent, false)
            MovieViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieList[position] is ProgressItem) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    fun addAll(movies: List<PagingItem>) {
        movieList.addAll(movies)
        notifyItemRangeInserted(itemCount, itemCount.plus(movies.size).minus(ONE_ITEM_COUNT))
    }

    fun add(item: PagingItem) {
        movieList.add(item)
        notifyItemInserted(itemCount - ONE_ITEM_COUNT)
    }

    fun removeLastItem() {
        if (movieList.isNotEmpty()) {
            movieList.removeAt(itemCount - ONE_ITEM_COUNT)
            notifyItemRemoved(itemCount)
        }
    }
}
