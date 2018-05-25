package com.leticiafernandes.letsmovie.infrastructure.model

import com.google.gson.annotations.SerializedName

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MovieResponse(var page: Int, @SerializedName("total_results") var totalResults: Int,
                    @SerializedName("total_pages") var totalPages: Int, var results: List<Movie>) {
}