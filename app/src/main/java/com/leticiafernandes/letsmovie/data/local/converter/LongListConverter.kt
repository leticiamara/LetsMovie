package com.leticiafernandes.letsmovie.data.local.converter

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun stringToLongList(data: String?): List<Long> {
        return data?.split(",")?.mapNotNull {
            it.toLongOrNull()
        } ?: emptyList()
    }

    @TypeConverter
    fun longListToString(longs: List<Long>?): String {
        return longs?.joinToString(",") ?: ""
    }
}
