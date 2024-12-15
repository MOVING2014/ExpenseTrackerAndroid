package com.example.expensetracker.data.local.mapper

import androidx.room.TypeConverter
import java.sql.Date

// data/converter/Converters.kt
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}