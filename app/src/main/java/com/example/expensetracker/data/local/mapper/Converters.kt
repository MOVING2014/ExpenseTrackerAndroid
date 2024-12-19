package com.example.expensetracker.data.local.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.TypeConverter
import java.sql.Date
import kotlin.reflect.KClass

// data/converter/Converters.kt
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? {
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
//    // 将字符串转换为对应的 ImageVector
//    @TypeConverter
//    fun fromStringToImageVector(iconName: String): ImageVector? {
//        return stringToImageVector(iconName)  // 使用反射转换函数
//    }
//
//    // 将 ImageVector 转换为字符串
//    @TypeConverter
//    fun fromImageVectorToString(icon: ImageVector): String {
//        return iconToString(icon)  // 将 ImageVector 转换为字符串
//    }
//
//
//    // 将字符串转化为对应的 ImageVector
//    private fun stringToImageVector(iconName: String): ImageVector? {
//        // 解析字符串
//    }

}