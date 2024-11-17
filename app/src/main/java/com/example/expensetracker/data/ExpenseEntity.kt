package com.example.expensetracker.data

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
    val note: String,
    val tags: List<String>
)

// data/entity/CategoryEntity.kt
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val icon: String,
    val type: String
)