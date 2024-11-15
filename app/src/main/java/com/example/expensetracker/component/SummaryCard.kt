package com.example.expensetracker.component
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.models.Expense
import com.example.expensetracker.screens.HomeViewModel
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor
import java.text.SimpleDateFormat

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