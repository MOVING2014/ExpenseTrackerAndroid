package com.example.expensetracker.utils

import android.icu.text.DecimalFormat

object NumberFormatUtils {
    private val numberFormat = DecimalFormat("#,###")

    fun formatNumber(number: Number): String {
        return numberFormat.format(number)
    }
}