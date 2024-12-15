package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

// data/dao/CategoryDao.kt
@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories")
    fun getAllCategoriesFlow(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert
    suspend fun insertAll(categories: List<CategoryEntity>)  // 批量插入

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: String): Flow<CategoryEntity?>

    @Query("UPDATE categories SET name = :name, icon = :icon, type = :type WHERE id = :id")
    suspend fun updateCategory(
        id: String,
        name: String,
        icon: String,
        type: String
    )

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: String)
}