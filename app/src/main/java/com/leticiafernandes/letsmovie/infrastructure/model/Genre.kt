package com.leticiafernandes.letsmovie.infrastructure.model

import android.arch.persistence.room.Entity

/**
 * Created by leticiafernandes on 22/05/18.
 */
@Entity(tableName = "genre", primaryKeys = arrayOf("id"))
class Genre(var id: Long, var name: String) {
}