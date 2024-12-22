package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// data/entity/ExpenseEntity.kt
@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val categoryId: String,
    val amount: Double,
    val date: java.util.Date,
    val note: String? = null,
    val tags: List<String>
)

