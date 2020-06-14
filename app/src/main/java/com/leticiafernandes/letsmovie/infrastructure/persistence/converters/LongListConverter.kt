package com.leticiafernandes.letsmovie.infrastructure.persistence.converters

import androidx.room.TypeConverter

/**
 * Created by leticiafernandes on 25/05/18.
 */
object LongListConverter {

    @TypeConverter
    @JvmStatic
    fun stringToLongList(data: String?): List<Long>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toLong()
                } catch (ex: NumberFormatException) {
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun longListToString(longs: List<Long>?): String? {
        return longs?.joinToString(",")
    }
}