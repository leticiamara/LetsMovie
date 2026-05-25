package com.leticiafernandes.letsmovie.data.model

import androidx.room.Entity

@Entity(tableName = "genre", primaryKeys = ["id"])
data class Genre(var id: Long, var name: String)
