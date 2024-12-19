package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.mapper.toCategory
import com.example.expensetracker.data.local.mapper.toEntity
import com.example.expensetracker.data.local.mapper.toExpense
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// data/repository/RoomExpenseRepository.kt
class ExpenseRepositoryRoomImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
        .map { entities ->
            entities.map { entity ->
                val category = categoryDao.getCategoryById(entity.categoryId).first()
                entity.toExpense(category?.toCategory())
            }
        }

    override fun getExpenseById(id: String): Flow<Expense?> = expenseDao.getExpenseById(id)
        .map { entity ->
            entity?.let {
                val category = categoryDao.getCategoryById(entity.categoryId).first()
                entity.toExpense(category?.toCategory())
            }
        }

    override suspend fun addExpense(expense: Expense) {
        expense.toEntity()?.let { expenseDao.insertExpense(it) }
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(
            id = expense.id,
            amount = expense.amount,
            date = expense.date,
            note = expense.note ?: "",
            categoryId = expense.category.id,
            tags = expense.tags
        )
    }

    override suspend fun deleteExpense(id: String) {
        expenseDao.deleteExpense(id)
    }
}