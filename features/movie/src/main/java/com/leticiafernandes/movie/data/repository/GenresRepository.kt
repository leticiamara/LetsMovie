package com.leticiafernandes.movies.data.repository

import com.leticiafernandes.movies.domain.model.Genre
import io.reactivex.rxjava3.core.Observable

interface GenresRepository {
    fun listAllGenres(): Observable<List<Genre>>
}