package com.example.expensetracker.data.local

import com.example.expensetracker.data.model.Category
import com.example.expensetracker.data.model.Expense

// data/mapper/EntityMappers.kt
fun ExpenseEntity.toExpense(category: Category?) = Expense(
    id = id,
    category = category ?: throw IllegalStateException("Category not found"),
    amount = amount,
    date = date,
    note = note,
    tags = tags
)

fun Expense.toEntity() = note?.let {
    ExpenseEntity(
        id = id,
        categoryId = category.id,
        amount = amount,
        date = date,
        note = it,
        tags = tags
    )
}

fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name,
    icon = icon,
    type = type
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    icon = icon,
    type = type
)