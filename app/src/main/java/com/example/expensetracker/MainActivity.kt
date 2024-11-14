package com.example.expensetracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable

import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                // 直接调用 MainScreen，不再传入 ViewModel
                MainScreen()
            }
        }
    }
}


// Mock 数据
val mockTransactions = listOf(
    Transaction(1, 5000.0, "Monthly Salary", "Income", TransactionType.INCOME),
    Transaction(2, 25.0, "Lunch", "Food", TransactionType.EXPENSE),
    Transaction(3, 35.0, "Taxi", "Transport", TransactionType.EXPENSE),
    Transaction(4, 500.0, "Freelance Project", "Income", TransactionType.INCOME),
    Transaction(5, 15.0, "Coffee", "Food", TransactionType.EXPENSE),
    Transaction(6, 1200.0, "Rent", "Housing", TransactionType.EXPENSE),
    Transaction(7, 200.0, "Utilities", "Bills", TransactionType.EXPENSE),
    Transaction(8, 300.0, "Part-time Work", "Income", TransactionType.INCOME),
    Transaction(9, 80.0, "Groceries", "Food", TransactionType.EXPENSE),
    Transaction(10, 45.0, "Movie Tickets", "Entertainment", TransactionType.EXPENSE),
    Transaction(11, 1000.0, "Bonus", "Income", TransactionType.INCOME),
    Transaction(12, 60.0, "Internet Bill", "Bills", TransactionType.EXPENSE),
    Transaction(13, 30.0, "Bus Pass", "Transport", TransactionType.EXPENSE),
    Transaction(14, 150.0, "Online Course", "Education", TransactionType.EXPENSE),
    Transaction(15, 800.0, "Side Project", "Income", TransactionType.INCOME),
    Transaction(16, 40.0, "Restaurant", "Food", TransactionType.EXPENSE),
    Transaction(17, 100.0, "Phone Bill", "Bills", TransactionType.EXPENSE),
    Transaction(18, 250.0, "Tutoring", "Income", TransactionType.INCOME),
    Transaction(19, 70.0, "Hair Cut", "Personal Care", TransactionType.EXPENSE),
    Transaction(20, 90.0, "Gym Membership", "Health", TransactionType.EXPENSE),
    Transaction(21, 2000.0, "Contract Work", "Income", TransactionType.INCOME),
    Transaction(22, 120.0, "Shopping", "Clothing", TransactionType.EXPENSE),
    Transaction(23, 55.0, "Medicine", "Health", TransactionType.EXPENSE),
    Transaction(24, 400.0, "Gift", "Income", TransactionType.INCOME),
    Transaction(25, 65.0, "Books", "Education", TransactionType.EXPENSE),
    Transaction(26, 85.0, "Concert Tickets", "Entertainment", TransactionType.EXPENSE),
    Transaction(27, 1500.0, "Consulting", "Income", TransactionType.INCOME),
    Transaction(28, 95.0, "Home Supplies", "Housing", TransactionType.EXPENSE),
    Transaction(29, 75.0, "Gas", "Transport", TransactionType.EXPENSE),
    Transaction(30, 300.0, "Overtime Pay", "Income", TransactionType.INCOME)
)

// 补充 TransactionsList 实现
@Composable
fun TransactionsList(
    transactions: List<Transaction>,
    onDelete: (Transaction) -> Unit
) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction, onDelete)
        }
    }
}

// 修改 MainScreen，使用 mock 数据
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Mock 数据
    val transactions = remember { mockTransactions }
    val totalIncome = remember { transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount } }
    val totalExpense = remember { transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount } }

    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, "Add Transaction")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SummaryCard(
                totalIncome = totalIncome,
                totalExpense = totalExpense
            )

            TransactionsList(
                transactions = transactions,
                onDelete = { /* Mock delete action */ }
            )
        }

        if (showAddDialog) {
            AddTransactionDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { /* Mock add action */
                    showAddDialog = false
                }
            )
        }
    }
}

// 添加预览函数
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun TransactionItemPreview() {
//    MaterialTheme {
//        TransactionItem(
//            transaction = Transaction(
//                amount = 100.0,
//                description = "Salary",
//                category = "Income",
//                type = TransactionType.INCOME
//            ),
//            onDelete = {}
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SummaryCardPreview() {
//    MaterialTheme {
//        SummaryCard(totalIncome = 1000.0, totalExpense = 650.0)
//    }
//}



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

@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionItem(
    transaction: Transaction,
    onDelete: (Transaction) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = SimpleDateFormat("yyyy-MM-dd")
                        .format(Date(transaction.date)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "¥%.2f".format(transaction.amount),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (transaction.type == TransactionType.INCOME)
                        textExpenseColor else textIncomeColor
                )

                IconButton(
                    onClick = { onDelete(transaction) }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (Transaction) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Add Transaction",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.selectable(
                            selected = type == TransactionType.EXPENSE,
                            onClick = { type = TransactionType.EXPENSE }
                        )
                    ) {
                        RadioButton(
                            selected = type == TransactionType.EXPENSE,
                            onClick = { type = TransactionType.EXPENSE }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Expense")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.selectable(
                            selected = type == TransactionType.INCOME,
                            onClick = { type = TransactionType.INCOME }
                        )
                    ) {
                        RadioButton(
                            selected = type == TransactionType.INCOME,
                            onClick = { type = TransactionType.INCOME }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Income")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            amount.toDoubleOrNull()?.let { amountValue ->
                                if (description.isNotBlank() && category.isNotBlank()) {
                                    onConfirm(
                                        Transaction(
                                            amount = amountValue,
                                            description = description,
                                            category = category,
                                            type = type,
                                            date = System.currentTimeMillis()
                                        )
                                    )
                                }
                            }
                        },
                        enabled = amount.isNotBlank() && description.isNotBlank() && category.isNotBlank()
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

// Preview functions
@Preview(showBackground = true)
@Composable
fun SummaryCardPreview() {
    MaterialTheme {
        SummaryCard(totalIncome = 1000.0, totalExpense = 650.0)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    MaterialTheme {
        TransactionItem(
            transaction = Transaction(
                amount = 100.0,
                description = "Salary",
                category = "Income",
                type = TransactionType.INCOME,
                date = System.currentTimeMillis()
            ),
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionDialogPreview() {
    MaterialTheme {
        AddTransactionDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}