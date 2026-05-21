package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.data.repository.GenresRepository
import com.leticiafernandes.movie.data.repository.MoviesRepository
import com.leticiafernandes.movie.domain.mapper.mapToMovieItem
import com.leticiafernandes.movie.domain.mapper.mapToMovieResultItem
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.MovieResultItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val genresRepository: GenresRepository
) : MoviesUseCase {

    override suspend fun listPopularMovies(page: Int): MovieResultItem = coroutineScope {
        val genresDeferred = async { genresRepository.listAllGenres() }
        val moviesDeferred = async { moviesRepository.listPopularMovies(page) }
        moviesDeferred.await().mapToMovieResultItem(genresDeferred.await())
    }

    override suspend fun listMovieDetails(movieId: Long): MovieItem {
        return mapToMovieItem(moviesRepository.listMovieDetails(movieId), null)
    }
}
