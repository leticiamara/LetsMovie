package com.leticiafernandes.movies.data.datasource.remote

import com.leticiafernandes.movies.data.api.MoviesService
import com.leticiafernandes.movies.data.datasource.remote.dto.MovieResultDTO
import io.reactivex.rxjava3.core.Observable

class MoviesRemoteDataSourceImpl(private val moviesService: MoviesService) : MoviesRemoteDataSource {

    override fun listPopularMovies(page: Int): Observable<MovieResultDTO> {
        return moviesService.listPopularMovies(page = page)
    }
}
