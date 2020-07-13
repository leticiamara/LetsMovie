package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.Genre
import io.reactivex.rxjava3.core.Observable

interface GenresRepository {
    fun listAllGenres(): Observable<List<Genre>>
}