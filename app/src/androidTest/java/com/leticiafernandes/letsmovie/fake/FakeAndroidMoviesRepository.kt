package com.leticiafernandes.letsmovie.fake

import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.MoviesRepository
import com.leticiafernandes.letsmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class FakeAndroidMoviesRepository : MoviesRepository {

    var movieDetailsResult: NetworkResult<Movie> = NetworkResult.HttpError(404, "Not Found")

    override fun getPopularMovies(): Flow<PagingData<Movie>> = flowOf(PagingData.empty())

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie> = movieDetailsResult
}

fun buildAndroidFakeMovieDomain(
    id: Long = 1L,
    title: String = "Movie $id",
    voteAverage: Double = 7.5,
    overview: String = "Overview $id"
) = Movie(
    id = id,
    voteCount = 100,
    title = title,
    voteAverage = voteAverage,
    popularity = 100.0,
    posterPath = null,
    originalLanguage = "en",
    originalTitle = title,
    genreIds = listOf(28L),
    backdropPath = null,
    adult = false,
    overview = overview,
    releaseDate = Date(0)
)
