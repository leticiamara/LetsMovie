package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.datasource.remote.dto.MovieResultDTO
import io.reactivex.rxjava3.core.Observable

interface MoviesRemoteDataSource {
    fun listPopularMovies(page: Int): Observable<MovieResultDTO>
}