package com.leticiafernandes.movies.data.repository

import com.leticiafernandes.movies.data.datasource.remote.GenresRemoteDataSource
import com.leticiafernandes.movies.data.mapper.GenreMapper
import com.leticiafernandes.movies.domain.model.Genre
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
        private val genresRemoteDataSource: GenresRemoteDataSource,
        private val genreMapper: GenreMapper
) : GenresRepository {

    override fun listAllGenres(): Observable<List<Genre>> {
        return genresRemoteDataSource.listAllGenres().map {
            genreMapper.mapTo(it)
        }
    }
}
