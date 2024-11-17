package com.example.expensetracker.data

import com.example.expensetracker.models.Category
import com.example.expensetracker.models.Expense
import com.example.expensetracker.repository.CategoryRepository
import com.example.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// data/repository/RoomExpenseRepository.kt
class RoomExpenseRepository(
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

// data/repository/RoomCategoryRepository.kt
class RoomCategoryRepository(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
        .map { entities ->
            entities.map { it.toCategory() }
        }

    override suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category.toEntity())
    }

    override suspend fun deleteCategory(id: String) {
        categoryDao.deleteCategory(id)
    }
}