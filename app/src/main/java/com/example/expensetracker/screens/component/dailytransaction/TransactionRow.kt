package com.example.expensetracker.screens.component.dailytransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor

@Composable
fun TransactionRow(
    transaction: Expense,
    onDelete: (Expense) -> Unit
) {


    var showDialog2 by remember { mutableStateOf(false) }

    // AlertDialog 用于确认删除
    if (showDialog2) {
        AlertDialog(
            onDismissRequest = { showDialog2 = false },  // 点击外部区域关闭对话框
            title = { Text(text = "更多") },
//            text = { Text(text = "更多") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(transaction)
                        showDialog2 = false  // 点击确认后执行删除并关闭对话框
                    }
                ) {
                    Text("删除")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = { showDialog2 = false }  // 点击取消时关闭对话框
                ) {
                    Text("取消")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            val displayText = if (!transaction.note.isNullOrEmpty()) {
                transaction.note
            } else {
                transaction.category.name
            }


            Text(
                text = transaction.category.icon + " | " + displayText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )


            if (!transaction.note.isNullOrEmpty()) {
//                HorizontalDivider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 2.dp),
//                    thickness = 0.0.dp,
//                    color = Color.White
//                )
                Text(
//                    modifier = Modifier.padding(vertical = 2.dp),
                    text = transaction.category.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }


        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "%.1f".format(transaction.amount),
                style = MaterialTheme.typography.titleSmall,
                color = if (transaction.category.type == "income")
                    textIncomeColor else textExpenseColor
            )

            IconButton(
                onClick = { showDialog2 = true },
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}