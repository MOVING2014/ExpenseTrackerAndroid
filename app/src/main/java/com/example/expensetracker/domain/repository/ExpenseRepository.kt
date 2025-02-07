package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

// domain/repository/ExpenseRepository.kt
interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getExpenseById(id: String): Flow<Expense?>
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(id: String)
}

