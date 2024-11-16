import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.component.DailyTransactionCard
import com.example.expensetracker.component.SummaryCard
import com.example.expensetracker.component.TransactionsCalendar
import com.example.expensetracker.component.TransactionsList
import com.example.expensetracker.screens.HomeViewModel
import java.time.YearMonth


// 修改 MainScreen，使用 mock 数据
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: HomeViewModel = hiltViewModel(), onAddClick: () -> Unit) {

    // Mock 数据
    val transactions by viewModel.expenses.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val monthlyExpenses by viewModel.monthlyExpenses.collectAsState()
    val groupedMonthlyExpenses by viewModel.groupedMonthlyExpenses.collectAsState()




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
                onClick = onAddClick  // 使用传入的 onAddClick
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


            TransactionsCalendar(
                transactions = monthlyExpenses,
                selectedMonth = selectedMonth,
                onMonthChange = { viewModel.updateSelectedMonth(it) }

            )

            LazyColumn {
                items(groupedMonthlyExpenses) { dailyTransactions ->
                    DailyTransactionCard(
                        dailyTransactions = dailyTransactions,
                        onDelete = { viewModel.deleteExpense(it) }
                    )
                }
            }
//            SummaryCard(
//                totalIncome = totalIncome,
//                totalExpense = totalExpense
//            )

//            TransactionsList(
//                transactions = transactions,
//                onDelete = { /* Mock delete action */
//                expense ->
//                    viewModel.deleteExpense(expense)
//                }
//            )
        }

    }
}

