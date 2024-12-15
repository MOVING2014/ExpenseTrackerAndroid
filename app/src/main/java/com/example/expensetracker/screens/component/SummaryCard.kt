package com.example.expensetracker.screens.component
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor

@Composable
fun SummaryCard(totalIncome: Double, totalExpense: Double) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        "Income",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        "¥%.2f".format(totalIncome),
                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Green
                        color = textIncomeColor
                    )
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        "Expense",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        "¥%.2f".format(totalExpense),
                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Red
                        color = textExpenseColor
                    )
                }
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        "Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        "¥%.2f".format(totalIncome - totalExpense),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (totalIncome >= totalExpense) textIncomeColor else textExpenseColor
                    )
                }
            }
        }
    }
}