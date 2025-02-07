package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.mock.MockData
import com.example.expensetracker.domain.model.Category
import com.example.expensetracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// data/repository/MockCategoryRepository.kt
class CategoryRepositoryMockImpl : CategoryRepository {
    private val _categories = MutableStateFlow(MockData.categories)

    override fun getAllCategories(): Flow<List<Category>> = _categories.asStateFlow()

    override suspend fun addCategory(category: Category) {
        _categories.update { currentList ->
            currentList + category
        }
    }

    override suspend fun deleteCategory(id: String) {
        _categories.update { currentList ->
            currentList.filter { it.id != id }
        }
    }

    override suspend fun updateCategory(category: Category) {
        _categories.update { currentList ->
            currentList.map {
                if (it.id == category.id) category else it
            }
        }
    }
}