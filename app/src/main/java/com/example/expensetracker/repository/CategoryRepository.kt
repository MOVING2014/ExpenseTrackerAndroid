package com.example.expensetracker.repository

import com.example.expensetracker.models.Category
import kotlinx.coroutines.flow.Flow

// domain/repository/CategoryRepository.kt
interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun deleteCategory(id: String)
}