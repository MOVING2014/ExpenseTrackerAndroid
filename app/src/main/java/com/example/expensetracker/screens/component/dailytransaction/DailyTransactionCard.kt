package com.example.expensetracker.screens.component.dailytransaction
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.DailyTransactions
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor
import java.util.Locale

@Composable
fun DailyTransactionCard(
    dailyTransactions: DailyTransactions,
    onDelete: (Expense) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

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
                        text = SimpleDateFormat("MM月dd日 E", Locale.CHINESE)
                            .format(dailyTransactions.date),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "%.0f".format(dailyTransactions.netAmount),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (dailyTransactions.netAmount >= 0)
                            textIncomeColor else textExpenseColor
                    )
                }


            }

            // Divider
            HorizontalDivider(
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
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                    )
                }
            }
        }
    }
}


