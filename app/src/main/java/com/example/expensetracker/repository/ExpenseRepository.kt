package com.example.expensetracker.repository

import com.example.expensetracker.models.*
import kotlinx.coroutines.flow.Flow

// domain/repository/ExpenseRepository.kt
interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getExpenseById(id: String): Flow<Expense?>
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(id: String)
}

