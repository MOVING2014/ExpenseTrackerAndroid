package com.example.expensetracker.utils

import android.icu.text.DecimalFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object NumberFormatUtils {
    private val numberFormat = DecimalFormat("#,###")

    fun formatNumber(number: Number): String {
        return numberFormat.format(number)
    }
}


object DateFormatters {
    val shortDate = DateTimeFormatter.ofPattern("MM月dd日")
    val mediumDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val longDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    val fullDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日 EEEE", Locale.CHINESE)
}

// 扩展函数
fun LocalDate.toShortString() = format(DateFormatters.shortDate)
fun LocalDate.toMediumString() = format(DateFormatters.mediumDate)
fun LocalDate.toLongString() = format(DateFormatters.longDate)
fun LocalDate.toFullString() = format(DateFormatters.fullDate)

fun LocalDate.toDate(): Date {
    return Date.from(
        this.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
    )
}

// 使用示例
//val date = LocalDate.now()
//println(date.toShortString())  // 03-15
//println(date.toMediumString()) // 2024-03-15
//println(date.toLongString())   // 2024年03月15日
//println(date.toFullString())   // 2024年03月15日 星期五