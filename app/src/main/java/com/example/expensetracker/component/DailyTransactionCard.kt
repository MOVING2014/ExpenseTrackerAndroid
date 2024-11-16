package com.example.expensetracker.component
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.expensetracker.models.*
import com.example.expensetracker.screens.HomeViewModel
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor
import java.util.Locale

@Composable
fun DailyTransactionCard(
    dailyTransactions: HomeViewModel.DailyTransactions,
    onDelete: (Expense) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 1.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Date header with income and expense summary
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SimpleDateFormat("yyyy年MM月dd日 E", Locale.CHINESE)
                            .format(dailyTransactions.date),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "¥%.1f".format(dailyTransactions.netAmount),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (dailyTransactions.netAmount >= 0)
                            textIncomeColor else textExpenseColor
                    )
                }

                // 收支详情行
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (dailyTransactions.totalIncome > 0) {
                        Text(
                            text = "收入: ¥%.1f".format(dailyTransactions.totalIncome),
                            style = MaterialTheme.typography.bodySmall,
                            color = textIncomeColor
                        )
                    }
                    if (dailyTransactions.totalExpense > 0) {
                        Text(
                            text = "支出: ¥%.1f".format(dailyTransactions.totalExpense),
                            style = MaterialTheme.typography.bodySmall,
                            color = textExpenseColor
                        )
                    }
                }
            }

            // Divider
            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )

            // Transactions list
            dailyTransactions.transactions.forEach { transaction ->
                TransactionRow(
                    transaction = transaction,
                    onDelete = onDelete
                )
                if (transaction != dailyTransactions.transactions.last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                    )
                }
            }
        }
    }
}


@Composable
private fun TransactionRow(
    transaction: Expense,
    onDelete: (Expense) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            transaction.note?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = transaction.category.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "¥%.1f".format(transaction.amount),
                style = MaterialTheme.typography.bodyMedium,
                color = if (transaction.category.type == "income")
                    textIncomeColor else textExpenseColor
            )

            IconButton(
                onClick = { onDelete(transaction) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}