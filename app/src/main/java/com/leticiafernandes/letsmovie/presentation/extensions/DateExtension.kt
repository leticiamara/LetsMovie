package com.leticiafernandes.letsmovie.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by leticiafernandes on 22/05/18.
 */
fun Date.formatToReleaseDate(): String {
    val simpleDateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return simpleDateFormat.format(this).capitalize()
}