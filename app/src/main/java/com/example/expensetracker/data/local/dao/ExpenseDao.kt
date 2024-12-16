package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

// data/dao/ExpenseDao.kt
@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Transaction
    @Query("SELECT * FROM expenses")
    fun getAllExpensesWithCategory(): Flow<List<ExpenseWithCategory>>


    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: String): Flow<ExpenseEntity?>

    @Transaction
    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseWithCategoryById(id: String): Flow<ExpenseWithCategory?>


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

