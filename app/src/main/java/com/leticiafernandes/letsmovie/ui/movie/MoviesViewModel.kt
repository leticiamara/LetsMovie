package com.leticiafernandes.letsmovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leticiafernandes.letsmovie.domain.model.MovieCategory
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FLOW_STOP_TIMEOUT = 5000L

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    observeFavoritesUseCase: ObserveFavoritesUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(MovieCategory.Popular)
    val selectedCategory: StateFlow<MovieCategory> = _selectedCategory.asStateFlow()

    val movies: Flow<PagingData<MovieItem>> = _selectedCategory
        .flatMapLatest { category -> moviesUseCase.getMovies(category) }
        .cachedIn(viewModelScope)

    val bookmarkedIds: StateFlow<Set<Long>> = observeFavoritesUseCase()
        .map { favorites -> favorites.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(FLOW_STOP_TIMEOUT),
            initialValue = emptySet()
        )

    fun setCategory(category: MovieCategory) {
        _selectedCategory.value = category
    }

    fun toggleBookmark(movieId: Long) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
        }
    }
}
