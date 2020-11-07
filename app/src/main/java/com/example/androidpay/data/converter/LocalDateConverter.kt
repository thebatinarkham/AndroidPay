package com.example.androidpay.data.converter

import androidx.room.TypeConverter
import java.util.*

class LocalDateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }


}