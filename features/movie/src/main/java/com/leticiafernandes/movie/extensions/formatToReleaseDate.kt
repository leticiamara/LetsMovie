package com.leticiafernandes.movie.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToReleaseDate(): String {
    val simpleDateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return simpleDateFormat.format(this).capitalize()
}
