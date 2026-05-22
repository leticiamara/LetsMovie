package com.leticiafernandes.letsmovie.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.leticiafernandes.letsmovie.data.mapper.mapToMovie
import com.leticiafernandes.letsmovie.domain.model.Movie

class MoviesPagingSource(
    private val remoteDataSource: MoviesRemoteDataSource
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val dto = remoteDataSource.listPopularMovies(page)
            LoadResult.Page(
                data = dto.results.map { mapToMovie(it) },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= dto.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
