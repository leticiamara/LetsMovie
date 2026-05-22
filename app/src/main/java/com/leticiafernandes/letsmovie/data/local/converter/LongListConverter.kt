package com.leticiafernandes.letsmovie.data.local.converter

import androidx.room.TypeConverter

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