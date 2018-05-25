package com.leticiafernandes.letsmovie.infrastructure.persistence.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by leticiafernandes on 25/05/18.
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}