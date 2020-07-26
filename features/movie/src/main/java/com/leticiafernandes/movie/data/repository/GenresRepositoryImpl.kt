package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.data.datasource.remote.GenresRemoteDataSource
import com.leticiafernandes.movie.data.mapper.mapToGenreDomain
import com.leticiafernandes.movie.domain.model.Genre
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
        private val genresRemoteDataSource: GenresRemoteDataSource
) : GenresRepository {

    override fun listAllGenres(): Single<List<Genre>> {
        return genresRemoteDataSource.listAllGenres().map { genreDTOList ->
            genreDTOList.map { it.mapToGenreDomain() }
        }
    }
}
