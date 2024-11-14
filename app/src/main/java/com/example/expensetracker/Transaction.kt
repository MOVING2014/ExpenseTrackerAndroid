package com.example.expensetracker

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// Transaction.kt
// 首先添加一个 mock 数据类和示例数据
data class Transaction(
    val id: Int = 0,
    val amount: Double,
    val description: String,
    val category: String,
    val type: TransactionType,
    val date: Long = System.currentTimeMillis()
)

enum class TransactionType {
    INCOME, EXPENSE
}

//// Room Database
//@Database(entities = [Transaction::class], version = 1)
//abstract class TransactionDatabase : RoomDatabase() {
//    abstract fun transactionDao(): TransactionDao
//}

