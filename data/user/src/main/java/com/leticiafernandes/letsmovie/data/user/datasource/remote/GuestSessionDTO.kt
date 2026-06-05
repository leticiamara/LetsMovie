package com.leticiafernandes.letsmovie.data.user.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionDTO(
    val success: Boolean = false,
    @SerialName("guest_session_id") val guestSessionId: String = "",
    @SerialName("expires_at") val expiresAt: String = ""
)
