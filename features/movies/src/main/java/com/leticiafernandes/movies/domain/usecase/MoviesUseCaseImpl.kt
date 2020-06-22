package com.leticiafernandes.movies.domain.usecase

import com.leticiafernandes.movies.data.repository.GenresRepository
import com.leticiafernandes.movies.data.repository.MoviesRepository
import com.leticiafernandes.movies.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable

class MoviesUseCaseImpl(
        private val moviesRepository: MoviesRepository,
        private val genresRepository: GenresRepository
) : MoviesUseCase {

    override fun listPopularMovies(page: Int): Observable<MovieResult> {
        return genresRepository.listAllGenres().concatMap {
            moviesRepository.listPopularMovies(page)
        }
    }
}
