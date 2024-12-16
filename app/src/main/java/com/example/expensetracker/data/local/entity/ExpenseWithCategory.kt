package com.example.expensetracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class ExpenseWithCategory(
    @Embedded val expense: ExpenseEntity,   // 将 ExpenseEntity 嵌入
    @Relation(
        parentColumn = "categoryId",         // 这个是 `ExpenseEntity` 的外键
        entityColumn = "id"                 // 这个是 `CategoryEntity` 的主键
    )
    val category: CategoryEntity          // `CategoryEntity` 的关系字段
)

