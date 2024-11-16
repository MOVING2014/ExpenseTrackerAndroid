package com.example.expensetracker.utils

import android.icu.text.DecimalFormat

private val numberFormat = DecimalFormat("#,###")

fun Number.formatWithCommas(): String {
    return numberFormat.format(this)
}