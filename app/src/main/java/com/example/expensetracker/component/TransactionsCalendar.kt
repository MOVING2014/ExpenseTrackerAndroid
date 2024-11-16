package com.example.expensetracker.component
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.expensetracker.models.*
import com.example.expensetracker.screens.toLocalDate
import com.example.expensetracker.ui.theme.Typography
import com.example.expensetracker.ui.theme.textExpenseColor
import com.example.expensetracker.ui.theme.textIncomeColor
import com.example.expensetracker.utils.formatWithCommas
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.*      // 日期类
import java.time.LocalDateTime  // 日期时间类

@Composable
fun TransactionsCalendar(
    transactions: List<Expense>,
    selectedMonth: YearMonth = YearMonth.now(),
    onMonthChange: (YearMonth) -> Unit = {}
) {
    val groupedTransactions = transactions
        .groupBy { it.date.toLocalDate() }
        .mapValues { (_, transactions) ->
            transactions.partition { it.category.name == "income" }
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal =  16.dp
                )
    ) {
        // 月份选择器
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onMonthChange(selectedMonth.minusMonths(1))
            }) {
                Icon(Icons.Default.ChevronLeft, "Previous month")
            }

            Text(
                text = selectedMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(onClick = {
                onMonthChange(selectedMonth.plusMonths(1))
            }) {
                Icon(Icons.Default.ChevronRight, "Next month")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 星期标题行
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("日", "一", "二", "三", "四", "五", "六").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 日历网格
        val firstDayOfMonth = selectedMonth.atDay(1)
        val lastDayOfMonth = selectedMonth.atEndOfMonth()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(0.5.dp),
            verticalArrangement = Arrangement.spacedBy(0.5.dp)
        ) {
            // 填充月初空白天数
            items(firstDayOfWeek) {
                Box(modifier = Modifier.aspectRatio(1f))
            }

            // 填充当月天数
            items(lastDayOfMonth.dayOfMonth) { day ->
                val currentDate = selectedMonth.atDay(day + 1)
                val (income, expense) = groupedTransactions[currentDate]
                    ?: Pair(emptyList(), emptyList())

                CalendarDayCell(
                    day = day + 1,
                    income = income.sumOf { it.amount },
                    expense = expense.sumOf { it.amount },
                    isToday = currentDate == LocalDate.now()
                )
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: Int,
    income: Double,
    expense: Double,
    isToday: Boolean
) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = if (isToday)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.primaryContainer
        ),
        border = if (isToday) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null ,
        shape = RoundedCornerShape(5.dp)  // 在这里设置圆角
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodySmall
            )

            if (income > 0.0) {
                Text(
                    text = income.toInt().formatWithCommas(),
                    style = Typography.labelSmall,
                    color = textIncomeColor
                )
            }

            if (expense > 0.0) {
                Text(
                    text = expense.toInt().formatWithCommas(),
                    style = Typography.labelSmall,
                    color = textExpenseColor
                )
            }
        }
    }
}

