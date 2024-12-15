package com.example.expensetracker.data.model

import java.util.Date

data class DailyTransactions(
    val date: Date,
    val transactions: List<Expense>,
    val totalIncome: Double,
    val totalExpense: Double
) {
    // 计算净额（收入 - 支出）
    val netAmount: Double get() = totalIncome - totalExpense
}