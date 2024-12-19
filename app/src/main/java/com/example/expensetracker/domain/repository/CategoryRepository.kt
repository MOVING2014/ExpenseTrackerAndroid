package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.model.Category
import kotlinx.coroutines.flow.Flow

// domain/repository/CategoryRepository.kt
interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun deleteCategory(id: String)
    suspend fun updateCategory(category: Category)
}