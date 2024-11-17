package com.example.expensetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// data/dao/ExpenseDao.kt
@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: String): Flow<ExpenseEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("UPDATE expenses SET amount = :amount, date = :date, note = :note, categoryId = :categoryId, tags = :tags WHERE id = :id")
    suspend fun updateExpense(
        id: String,
        amount: Double,
        date: java.util.Date,
        note: String,
        categoryId: String,
        tags: List<String>
    )

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpense(id: String)


}

// data/dao/CategoryDao.kt
@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

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