package com.leticiafernandes.letsmovie.presentation.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso


/**
 * Created by leticiafernandes on 26/05/18.
 */
class MovieImageUtils {

    companion object {

        val BASE_URL = "https://image.tmdb.org/t/p/original"

        fun loadImage(context: Context, imageURL: String, imageView: ImageView) {
            Picasso.with(context)
                    .load(BASE_URL + imageURL)
                    .centerCrop()
                    .fit()
                    .into(imageView)
        }
    }
}