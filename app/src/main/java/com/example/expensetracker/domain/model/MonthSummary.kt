package com.example.expensetracker.domain.model

// 月度汇总接口
data class MonthSummary(
    val month: String,
    val expense: Double,
    val income: Double
)