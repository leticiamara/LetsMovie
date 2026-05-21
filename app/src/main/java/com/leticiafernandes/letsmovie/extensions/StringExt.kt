package com.leticiafernandes.letsmovie.extensions

import com.leticiafernandes.letsmovie.BuildConfig.MOVIE_IMAGE_BASE_URL

fun String.toMovieAPIImageURL(): String {
    return "$MOVIE_IMAGE_BASE_URL$this"
}
