package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.api.MoviesService
import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO
import com.leticiafernandes.letsmovie.domain.model.MovieCategory
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
        private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun listMovies(category: MovieCategory, page: Int): MovieResultDTO =
        when (category) {
            MovieCategory.Popular -> moviesService.listPopularMovies(page = page)
            MovieCategory.NowPlaying -> moviesService.listNowPlayingMovies(page = page)
            MovieCategory.Upcoming -> moviesService.listUpcomingMovies(page = page)
        }

    override suspend fun listMovieDetails(movieId: Long): MovieDTO =
        moviesService.listMovieDetails(movieId)
}
