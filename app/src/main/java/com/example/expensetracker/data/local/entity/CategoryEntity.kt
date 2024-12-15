package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// data/entity/CategoryEntity.kt
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val icon: String,
    val type: String
)