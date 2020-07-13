package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.data.repository.GenresRepository
import com.leticiafernandes.movie.data.repository.MoviesRepository
import com.leticiafernandes.movie.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val genresRepository: GenresRepository
) : MoviesUseCase {

    override fun listPopularMovies(page: Int): Observable<MovieResult> {
        return genresRepository.listAllGenres().concatMap {
            moviesRepository.listPopularMovies(page)
        }
    }
}
