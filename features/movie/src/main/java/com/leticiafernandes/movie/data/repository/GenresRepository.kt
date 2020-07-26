package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.Genre
import io.reactivex.rxjava3.core.Single

interface GenresRepository {
    fun listAllGenres(): Single<List<Genre>>
}