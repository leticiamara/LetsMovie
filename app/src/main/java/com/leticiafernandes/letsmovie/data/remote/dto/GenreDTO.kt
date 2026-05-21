package com.leticiafernandes.letsmovie.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDTO(
    val id: Long,
    val name: String
)
