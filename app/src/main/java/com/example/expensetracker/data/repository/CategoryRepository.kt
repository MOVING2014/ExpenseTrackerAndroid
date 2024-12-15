package com.example.expensetracker.data.repository

import com.example.expensetracker.data.model.Category
import kotlinx.coroutines.flow.Flow

// domain/repository/CategoryRepository.kt
interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun deleteCategory(id: String)
}