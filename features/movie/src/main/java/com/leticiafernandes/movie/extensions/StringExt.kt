package com.leticiafernandes.movie.extensions

import com.leticiafernandes.movie.BuildConfig.MOVIE_IMAGE_BASE_URL

fun String.toMovieAPIImageURL(): String {
    return "$MOVIE_IMAGE_BASE_URL$this"
}
