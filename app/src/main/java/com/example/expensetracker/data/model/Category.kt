package com.example.expensetracker.data.model

// Category 模型
data class Category(
    val id: String,
    val name: String,
    val icon: String,  // 可选的图标
    val type: String // expense | income
)