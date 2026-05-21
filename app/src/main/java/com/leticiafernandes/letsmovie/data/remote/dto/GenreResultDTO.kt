package com.leticiafernandes.letsmovie.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreResultDTO(val genres: List<GenreDTO>)
