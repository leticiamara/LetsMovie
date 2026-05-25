package com.leticiafernandes.letsmovie.fake

import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.MoviesRepository
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class FakeMoviesRepository : MoviesRepository {

    var movies: List<Movie> = emptyList()
    var movieDetailsResult: NetworkResult<Movie> = NetworkResult.HttpError(404, "Not Found")

    override fun getMovies(category: MovieCategory): Flow<PagingData<Movie>> = flowOf(PagingData.from(movies))

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie> = movieDetailsResult
}

fun buildFakeMovieDomain(id: Long = 1L) = Movie(
    id = id,
    voteCount = 100,
    title = "Movie $id",
    voteAverage = 7.5,
    popularity = 100.0,
    posterPath = "/poster$id.jpg",
    originalLanguage = "en",
    originalTitle = "Movie $id",
    genreIds = listOf(28L),
    backdropPath = "/backdrop$id.jpg",
    adult = false,
    overview = "Overview $id",
    releaseDate = Date(0)
)
