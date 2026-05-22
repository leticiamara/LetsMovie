package com.leticiafernandes.letsmovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    moviesUseCase: MoviesUseCase
) : ViewModel() {

    val movies: Flow<PagingData<MovieItem>> = moviesUseCase.getPopularMovies()
        .cachedIn(viewModelScope)
}
