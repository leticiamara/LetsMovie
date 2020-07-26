package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.data.repository.GenresRepository
import com.leticiafernandes.movie.data.repository.MoviesRepository
import com.leticiafernandes.movie.domain.mapper.mapToMovieResultItem
import com.leticiafernandes.movie.presentation.model.MovieResultItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val genresRepository: GenresRepository
) : MoviesUseCase {

    override fun listPopularMovies(page: Int): Single<MovieResultItem> {
        return genresRepository.listAllGenres().concatMap {
            moviesRepository.listPopularMovies(page).map {
                it.mapToMovieResultItem()
            }
        }
    }
}
