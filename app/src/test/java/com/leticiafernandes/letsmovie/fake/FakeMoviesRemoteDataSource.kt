package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.MoviesRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO

class FakeMoviesRemoteDataSource : MoviesRemoteDataSource {

    var movieDetailsResult: MovieDTO = buildFakeMovieDTO()
    var exception: Exception? = null

    override suspend fun listPopularMovies(page: Int): MovieResultDTO =
        MovieResultDTO(page = page, results = emptyList(), totalResults = 0, totalPages = 1)

    override suspend fun listMovieDetails(movieId: Long): MovieDTO {
        exception?.let { throw it }
        return movieDetailsResult
    }
}

fun buildFakeMovieDTO(
    id: Long = 1L,
    title: String = "Movie $id",
    voteAverage: Double = 7.5,
    releaseDate: String = "2010-07-16"
) = MovieDTO(
    id = id,
    title = title,
    voteCount = 100,
    voteAverage = voteAverage,
    popularity = 100.0,
    posterPath = null,
    originalLanguage = "en",
    originalTitle = title,
    genreIds = listOf(28L),
    backdropPath = null,
    adult = false,
    overview = "Overview $id",
    releaseDate = releaseDate
)
