package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.GenresRemoteDataSource
import com.leticiafernandes.letsmovie.data.mapper.mapToGenreDomain
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.safeApiCall
import com.leticiafernandes.letsmovie.domain.model.Genre
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
        private val genresRemoteDataSource: GenresRemoteDataSource
) : GenresRepository {

    override suspend fun listAllGenres(): NetworkResult<List<Genre>> =
        safeApiCall { genresRemoteDataSource.listAllGenres().map { it.mapToGenreDomain() } }
}
