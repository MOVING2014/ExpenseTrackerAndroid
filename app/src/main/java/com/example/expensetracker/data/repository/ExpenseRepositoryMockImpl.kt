package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.mock.MockData
import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

// data/repository/MockExpenseRepository.kt
class ExpenseRepositoryMockImpl : ExpenseRepository {
    private val _expenses = MutableStateFlow(MockData.expenses)

    override fun getAllExpenses(): Flow<List<Expense>> = _expenses.asStateFlow()

    override fun getExpenseById(id: String): Flow<Expense?> = _expenses.map { expenses ->
        expenses.find { it.id == id }
    }

    override suspend fun addExpense(expense: Expense) {
        _expenses.update { currentList ->
            currentList + expense
        }
    }

    override suspend fun updateExpense(expense: Expense) {
        _expenses.update { currentList ->
            currentList.map {
                if (it.id == expense.id) expense else it
            }
        }
    }

    override suspend fun deleteExpense(id: String) {
        _expenses.update { currentList ->
            currentList.filter { it.id != id }
        }
    }
}