package com.example.expensetracker.domain.model

// Models.kt

import java.util.Date

// 日支出汇总模型
data class DailyExpenseSummary(
    val date: Date,
    val expenses: List<Expense>
) {
    val totalAmount: Double = expenses.sumOf { it.amount }
    val totalExpense: Double = expenses
        .filter { it.category.type == "expense" }
        .sumOf { kotlin.math.abs(it.amount) }
    val totalIncome: Double = expenses
        .filter { it.category.type == "income" }
        .sumOf { kotlin.math.abs(it.amount) }
}

