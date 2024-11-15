package com.example.expensetracker.component
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.expensetracker.models.Expense


// 补充 TransactionsList 实现
@Composable
fun TransactionsList(
    transactions: List<Expense>,
    onDelete: (Expense) -> Unit
) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction, onDelete)
        }
    }
}