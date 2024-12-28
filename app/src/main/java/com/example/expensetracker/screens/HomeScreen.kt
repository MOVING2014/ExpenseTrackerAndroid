import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.screens.component.DailyTransactionCard
import com.example.expensetracker.screens.component.TransactionsCalendar
import com.example.expensetracker.screens.HomeViewModel


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
        containerColor = Color.White,

        contentWindowInsets = WindowInsets(0),
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // 处理导航栏padding
//            .systemBarsPadding()    // 处理系统栏padding
//            .statusBarsPadding()
            ,



        topBar = {
            TopAppBar(
//                modifier = Modifier.height(65.dp),
                modifier = Modifier
                    .height(70.dp),

                title = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "快记",
                            style = MaterialTheme.typography.titleMedium
                        )

                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    // 设置图标按钮
                    IconButton(onClick = { /* 处理点击事件 */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    // 如果需要多个按钮，可以继续添加
                    IconButton(onClick = { /* 处理点击事件 */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // 日历作为固定项
                item {
                    TransactionsCalendar(
                        transactions = monthlyExpenses,
                        selectedMonth = selectedMonth,
                        onMonthChange = { viewModel.updateSelectedMonth(it) }
                    )
                }

                // 交易列表
                items(groupedMonthlyExpenses) { dailyTransactions ->
                    DailyTransactionCard(
                        dailyTransactions = dailyTransactions,
                        onDelete = { viewModel.deleteExpense(it) }
                    )
                }
            }
        }

    }
}

