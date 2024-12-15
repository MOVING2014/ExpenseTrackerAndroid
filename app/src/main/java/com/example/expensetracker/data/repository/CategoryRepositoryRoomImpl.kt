package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.mapper.toCategory
import com.example.expensetracker.data.local.mapper.toEntity
import com.example.expensetracker.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// data/repository/RoomCategoryRepository.kt
class CategoryRepositoryRoomImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategoriesFlow()
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